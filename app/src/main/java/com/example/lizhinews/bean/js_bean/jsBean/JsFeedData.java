package com.example.lizhinews.bean.js_bean.jsBean;


public class JsFeedData
{
    private String id;
    private String oid;
    private String category;
    private JsNewsData data;

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

    public JsNewsData getData()
    {
        return data;
    }

    public void setData(JsNewsData data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "JsFeedData{" +
                "id='" + id + '\'' +
                ", oid='" + oid + '\'' +
                ", category='" + category + '\'' +
                ", data=" + data +
                '}';
    }
}
