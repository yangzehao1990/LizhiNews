package com.example.lizhinews.bean.top_bean.topInfoBean;

/**
 * Created by freemypay on 2015/11/6.
 */
public class InfoContents
{
    String category;
    String link;
    String summary;
    String text;

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return "InfoContents{" +
                "category='" + category + '\'' +
                ", link='" + link + '\'' +
                ", summary='" + summary + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
