package com.example.rahul.alarm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "TEMPORARY", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table log(id integer primary key autoincrement , name text, time datetime default current_timestamp )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void pushDate()
    {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("insert into log(name) values('alert triggered')");
    }

    public Object [] displayData()
    {
        SQLiteDatabase db=getWritableDatabase();
        Cursor cur=db.rawQuery("select * from log;",null);
        ArrayList<String> time=new ArrayList<>();
        if(cur.moveToFirst())
        {
            do{
                time.add(cur.getString(cur.getColumnIndex("time")));
            }while(cur.moveToNext());
        }

        Object arr[]=time.toArray();

        return arr;
    }

}
