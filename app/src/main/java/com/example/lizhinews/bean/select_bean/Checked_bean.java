package com.example.lizhinews.bean.select_bean;


public class Checked_bean
{
    private String name;


    public Checked_bean(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    @Override
    public String toString()
    {
        return "Checked_bean{" +
                "name='" + name + '\'' +
                '}';
    }
}
