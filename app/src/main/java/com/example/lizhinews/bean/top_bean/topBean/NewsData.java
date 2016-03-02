package com.example.lizhinews.bean.top_bean.topBean;

import java.util.ArrayList;

/**
 * Created by freemypay on 2015/10/13.
 */
public class NewsData
{
    private String subject;
    private String summary;
    private String cover;
    private String format;
    private String changed;
    private ArrayList<PicData> pics;

    public ArrayList<PicData> getPics()
    {
        return pics;
    }

    public void setPics(ArrayList<PicData> pics)
    {
        this.pics = pics;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getCover()
    {
        return cover;
    }

    public void setCover(String cover)
    {
        this.cover = cover;
    }

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public String getChanged()
    {
        return changed;
    }

    public void setChanged(String changed)
    {
        this.changed = changed;
    }

    @Override
    public String toString()
    {
        return "NewsData{" +
                "subject='" + subject + '\'' +
                ", summary='" + summary + '\'' +
                ", cover='" + cover + '\'' +
                ", format='" + format + '\'' +
                ", changed='" + changed + '\'' +
                ", pics=" + pics +
                '}';
    }
}
