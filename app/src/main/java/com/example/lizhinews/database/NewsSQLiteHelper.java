package com.example.lizhinews.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Json数据库缓存
 */
public class NewsSQLiteHelper extends SQLiteOpenHelper
{
    public NewsSQLiteHelper(Context context)
    {
        super(context, "NewsDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql="create table JsonData(_id integer primary key autoincrement,"
                + "type integer,data text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
