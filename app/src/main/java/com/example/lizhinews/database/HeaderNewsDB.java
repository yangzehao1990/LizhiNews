package com.example.lizhinews.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class HeaderNewsDB
{
    private Context mContext;
    private HeaderNewsSQLiteHelper mHeaderNewsSQLiteHelper;

    public HeaderNewsDB(Context context)
    {
        this.mContext = context;
        this.mHeaderNewsSQLiteHelper = new HeaderNewsSQLiteHelper(mContext);
    }

    /**
     * 插入json,先删除之前的
     * @param json
     * @param type
     */
    public void insert(String json,int type)
    {
        SQLiteDatabase database = mHeaderNewsSQLiteHelper.getWritableDatabase();
        database.delete("HeaderJsonData", "type = ?", new String[]{type + ""});
        ContentValues values = new ContentValues();
        values.put("type",type);
        values.put("data",json);
        database.insert("HeaderJsonData", null, values);
        database.close();
    }


    /**
     * 拿到json
     * @param type
     * @return
     */
    public String getJson(int type)
    {
        SQLiteDatabase database = mHeaderNewsSQLiteHelper.getReadableDatabase();
        Cursor cursor = database.query("HeaderJsonData",null,"type = ?",new String[]{type + ""},null,null,null);
        if(cursor.getCount() > 0)
        {
            cursor.moveToNext();
            String data = cursor.getString(cursor.getColumnIndex("data"));
            return data;
        }
        return "";
    }
}
