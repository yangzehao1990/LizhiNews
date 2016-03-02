package com.example.lizhinews.bean.js_bean.jsBean;


public class JsNewsData
{
    private String subject;
    private String summary;
    private String cover;
    private String adlink;
    private String format;
    private String changed;

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

    public String getAdlink()
    {
        return adlink;
    }

    public void setAdlink(String adlink)
    {
        this.adlink = adlink;
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
        return "JsNewsData{" +
                "subject='" + subject + '\'' +
                ", summary='" + summary + '\'' +
                ", cover='" + cover + '\'' +
                ", adlink='" + adlink + '\'' +
                ", format='" + format + '\'' +
                ", changed='" + changed + '\'' +
                '}';
    }
}
