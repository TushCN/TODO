package com.example.cryptx.todo;

import android.content.SharedPreferences;
import android.support.v4.app.BundleCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by CryptX on 17-09-2017.
 */

public class AddTodoActivity extends AppCompatActivity{

    public static final int ADD_SUCCESS=1;
    EditText title;

    @Override
    protected void onCreate(Bundle savedInstancesState)
    {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_add_todo);
        title=(EditText)findViewById(R.id.titleEdit);
    }

    public void add(View view)
    {
        String titleText= title.getText().toString();

        TodoHelper openHelper=TodoHelper.getInstance(getApplicationContext());
        SQLiteDatabase db=openHelper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(Contract.TODO_TITLE,titleText);

        long id=db.insert(Contract.TODO_TABLE_NAME,null,contentValues);

        Intent result=new Intent();
        result.putExtra(Constant.KEY_TODO_ID,id);
        setResult(ADD_SUCCESS,result);
        finish();

    }


}
