package com.example.cryptx.todo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.example.cryptx.todo.Constant.KEY_POSITION;
import static com.example.cryptx.todo.Constant.KEY_TITLE;
import static com.example.cryptx.todo.Constant.KEY_TODO;
import static com.example.cryptx.todo.Constant.KEY_TODO_ID;

public class MainActivity extends AppCompatActivity {
    ListView listview;
    ArrayList<TODO> todos;
    CustomAdapter adapter;

    public final static int REQUEST_DETAIL = 1;
    public final static int REQUEST_ADD = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.list);
        todos = new ArrayList<>();


        final TodoHelper openHelper = TodoHelper.getInstance(getApplicationContext());
        SQLiteDatabase db = openHelper.getReadableDatabase();

        Cursor cursor = db.query(Contract.TODO_TABLE_NAME, null, null, null, null, null, null);
        db.delete(Contract.TODO_TABLE_NAME, Contract.TODO_ID + " = ?", new String[]{"1"});


        while (cursor.moveToNext()) {

            String title = cursor.getString(cursor.getColumnIndex(Contract.TODO_TITLE));
            int id = cursor.getInt(cursor.getColumnIndex(Contract.TODO_ID));
            TODO todo = new TODO(title, id);
            todos.add(todo);
        }
        cursor.close();


        adapter = new CustomAdapter(this, todos, new CustomAdapter.DeleteButtonClickListener() {
            @Override
            public void onDeleteClicked(int position, View view) {
                todos.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TODO todo = todos.get(i);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                intent.putExtra(KEY_TODO, todo);
                intent.putExtra(KEY_POSITION, i);

                startActivityForResult(intent, REQUEST_DETAIL);
            }
        });


        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete TODO ?");
                builder.setMessage("Are you sure?");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db = openHelper.getWritableDatabase();
                        long id=todos.get(position).getId();
                        db.delete(Contract.TODO_TABLE_NAME, Contract.TODO_ID + " = ?", new String[]{id+""});
                        todos.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("No", null);

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }}
        );

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {

            Intent add = new Intent(this, AddTodoActivity.class);
            startActivityForResult(add, REQUEST_ADD);
        }

        else if( id == R.id.about){

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            String url = "http://codingninjas.in";
            intent.setData(Uri.parse(url));
            startActivity(intent);

        }else if(id == R.id.feedback){

            Intent feedback = new Intent();
            feedback.setAction(Intent.ACTION_SENDTO);
            feedback.setData(Uri.parse("mailto:tusharyadav14111997@gmail.com"));
            feedback.putExtra(Intent.EXTRA_TEXT,"FEEDBACK");
            if(feedback.resolveActivity(getPackageManager()) != null){
                startActivity(feedback);
            }
            else {

            }
        }
        else if(id == R.id.contact){

            int permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE);

            if(permission == PERMISSION_GRANTED){
                Intent call = new Intent();
                call.setAction(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:9810543868"));
                startActivity(call);
            }
            else {
                //  ActivityCompat.requestPermissions();
                Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_LONG).show();
            }


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_DETAIL) {

            if (resultCode == DetailActivity.RESULT_SAVE) {


                int position = data.getIntExtra(KEY_POSITION, 0);
                String title = data.getStringExtra(KEY_TITLE);

                TODO todo = todos.get(position);
                todo.setTitle(title);
                adapter.notifyDataSetChanged();
            }
        } else if (requestCode == REQUEST_ADD) {
            if (resultCode == AddTodoActivity.ADD_SUCCESS) {
                long id = data.getLongExtra(KEY_TODO_ID, -1L);
                if (id > -1) {
                    TodoHelper openHelper = TodoHelper.getInstance(getApplicationContext());
                    SQLiteDatabase db = openHelper.getReadableDatabase();

                    Cursor cursor = db.query(Contract.TODO_TABLE_NAME, null,
                            Contract.TODO_ID + " = ?", new String[]{id + ""}
                            , null, null, null);

                    if (cursor.moveToFirst()) {

                        int titleColumnIndex = cursor.getColumnIndex(Contract.TODO_TITLE);
                        String title = cursor.getString(titleColumnIndex);
                        TODO todo = new TODO(title, (int) id);
                        todos.add(todo);
                        adapter.notifyDataSetChanged();
                    }

                    //SELECT * FROM expenses WHERE _id = ? AND amount > ?,id,amount

                }

            }
        }
    }
}