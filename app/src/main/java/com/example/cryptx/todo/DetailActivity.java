package com.example.cryptx.todo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import static com.example.cryptx.todo.Constant.KEY_POSITION;
import static com.example.cryptx.todo.Constant.KEY_TODO;
import static com.example.cryptx.todo.Constant.KEY_TITLE;
/**
 * Created by CryptX on 10-09-2017.
 */

public class DetailActivity extends AppCompatActivity {

    CustomAdapter adapter;
    EditText title;
    int position;

    public static final int RESULT_SAVE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent i = getIntent();
        TODO todo = (TODO) i.getSerializableExtra(KEY_TODO);
        position = i.getIntExtra(KEY_POSITION, 0);
        title = (EditText) findViewById(R.id.titleEdit);
        title.setText(todo.getTitle());



    }

    public void buttonClicked(View view) {

        String updatedTitle = title.getEditableText().toString();
        final TodoHelper openHelper = TodoHelper.getInstance(getApplicationContext());
        SQLiteDatabase db = openHelper.getReadableDatabase();

        //db.update()

        Intent i = new Intent();
        i.putExtra(KEY_TITLE, updatedTitle);
        i.putExtra(KEY_POSITION, position);
        setResult(RESULT_SAVE, i);
        finish();


    }
}