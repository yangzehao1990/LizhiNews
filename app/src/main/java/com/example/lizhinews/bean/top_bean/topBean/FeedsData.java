package com.example.lizhinews.bean.top_bean.topBean;

/**
 * Created by freemypay on 2015/10/13.
 */
public class FeedsData
{
    private String id;
    private String oid;
    private String category;
    private NewsData data;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getOid()
    {
        return oid;
    }

    public void setOid(String oid)
    {
        this.oid = oid;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public NewsData getData()
    {
        return data;
    }

    public void setData(NewsData data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "FeedsData{" +
                "id='" + id + '\'' +
                ", oid='" + oid + '\'' +
                ", category='" + category + '\'' +
                ", data=" + data +
                '}';
    }
}
