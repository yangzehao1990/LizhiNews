package com.example.lizhinews.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class HeaderNewsSQLiteHelper extends SQLiteOpenHelper
{

    public HeaderNewsSQLiteHelper(Context context)
    {
        super(context, "HeaderNewsDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql="create table HeaderJsonData(_id integer primary key autoincrement,"
                + "type integer,data text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
