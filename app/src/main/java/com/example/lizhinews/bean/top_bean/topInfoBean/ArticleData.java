package com.example.lizhinews.bean.top_bean.topInfoBean;

import java.util.ArrayList;

/**
 * Created by freemypay on 2015/11/6.
 */
public class ArticleData
{
    private String id;
    private String category;
    private String subject;
    private String subtitle;
    private String summary;
    private ArrayList<InfoContents> datas;
    private String origin;
    private String origined;
    private String changed;
    private String created;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getSubtitle()
    {
        return subtitle;
    }

    public void setSubtitle(String subtitle)
    {
        this.subtitle = subtitle;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public ArrayList<InfoContents> getDatas()
    {
        return datas;
    }

    public void setDatas(ArrayList<InfoContents> datas)
    {
        this.datas = datas;
    }

    public String getOrigin()
    {
        return origin;
    }

    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public String getOrigined()
    {
        return origined;
    }

    public void setOrigined(String origined)
    {
        this.origined = origined;
    }

    public String getChanged()
    {
        return changed;
    }

    public void setChanged(String changed)
    {
        this.changed = changed;
    }

    public String getCreated()
    {
        return created;
    }

    public void setCreated(String created)
    {
        this.created = created;
    }


    @Override
    public String toString()
    {
        return "ArticleData{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", subject='" + subject + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", summary='" + summary + '\'' +
                ", datas=" + datas +
                ", origin='" + origin + '\'' +
                ", origined='" + origined + '\'' +
                ", changed='" + changed + '\'' +
                ", created='" + created + '\'' +
                '}';
    }
}
