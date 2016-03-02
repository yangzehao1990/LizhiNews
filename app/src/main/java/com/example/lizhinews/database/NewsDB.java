package com.example.lizhinews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 实现Json数据的插入删除
 */
public class NewsDB
{
    private Context mContext;
    private NewsSQLiteHelper mHelper;

    public NewsDB(Context context)
    {
        this.mContext = context;
        this.mHelper = new NewsSQLiteHelper(mContext);
    }

    /**
     * 插入json,先删除之前的
     * @param json
     * @param type
     */
    public void insert(String json,int type)
    {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        database.delete("JsonData", "type = ?", new String[]{type + ""});
        ContentValues values = new ContentValues();
        values.put("type",type);
        values.put("data",json);
        database.insert("JsonData", null, values);
        database.close();
    }

    /**
     * 拿到json
     * @param type
     * @return
     */
    public String getJson(int type)
    {
        SQLiteDatabase database = mHelper.getReadableDatabase();
        Cursor cursor = database.query("JsonData",null,"type = ?",new String[]{type + ""},null,null,null);
        if(cursor.getCount() > 0)
        {
            cursor.moveToNext();
            String data = cursor.getString(cursor.getColumnIndex("data"));
            return data;
        }
        return "";
    }
}
