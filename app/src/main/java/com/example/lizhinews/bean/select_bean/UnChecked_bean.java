package com.example.lizhinews.bean.select_bean;


public class UnChecked_bean
{
    private String name;


    public UnChecked_bean(String name)
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
        return "UnChecked_bean{" +
                "name='" + name + '\'' +
                '}';
    }
}
