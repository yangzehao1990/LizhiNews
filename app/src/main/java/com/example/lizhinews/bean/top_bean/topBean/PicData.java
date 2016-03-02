package com.example.lizhinews.bean.top_bean.topBean;

/**
 * Created by freemypay on 2015/10/13.
 */
public class PicData
{
    private String id;
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
        return "PicData{" +
                "id='" + id + '\'' +
                ", photo='" + photo + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
