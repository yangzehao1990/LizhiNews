package com.example.lizhinews.bean.top_bean.topHeaderBean;

/**
 * Created by freemypay on 2015/10/14.
 */
public class TopsHeaderData
{
    private String id;
    private String oid;
    private String category;
    private String photo;
    private String subject;

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

    public String getPhoto()
    {
        return photo;
    }

    public void setPhoto(String photo)
    {
        this.photo = photo;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    @Override
    public String toString()
    {
        return "TopsHeaderData{" +
                "id='" + id + '\'' +
                ", oid='" + oid + '\'' +
                ", category='" + category + '\'' +
                ", photo='" + photo + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
