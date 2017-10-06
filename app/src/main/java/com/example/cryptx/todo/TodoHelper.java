package com.example.cryptx.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by CryptX on 17-09-2017.
 */

public class TodoHelper extends SQLiteOpenHelper{

private static TodoHelper instance;

    public static TodoHelper getInstance(Context context)
    {
        if(instance==null)
        {
            instance=new TodoHelper(context);
            return instance;
        }
        return instance;
    }

    private TodoHelper(Context context) {
        super(context, "todo_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String query=" CREATE TABLE "+Contract.TODO_TABLE_NAME+" ( "+
                Contract.TODO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                Contract.TODO_TITLE+" TEXT) ";
        sqLiteDatabase.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
