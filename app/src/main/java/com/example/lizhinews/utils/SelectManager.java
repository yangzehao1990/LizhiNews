package com.example.lizhinews.utils;

import com.example.lizhinews.bean.select_bean.Checked_bean;
import com.example.lizhinews.bean.select_bean.UnChecked_bean;

import java.util.ArrayList;


public class SelectManager
{
    public static ArrayList<Checked_bean> mCheckDatas;
    public static ArrayList<UnChecked_bean> mUnCheckDatas;


    public static void addUncheckDatas()
    {
        mUnCheckDatas.add(new UnChecked_bean("原创"));
        mUnCheckDatas.add(new UnChecked_bean("娱乐"));
        mUnCheckDatas.add(new UnChecked_bean("豆瓣速递"));
        mUnCheckDatas.add(new UnChecked_bean("独家一番"));
        mUnCheckDatas.add(new UnChecked_bean("专题"));
        mUnCheckDatas.add(new UnChecked_bean("荔枝派"));
        mUnCheckDatas.add(new UnChecked_bean("国内"));
        mUnCheckDatas.add(new UnChecked_bean("国际"));

    }

    public static void addCheckDatas()
    {
        mCheckDatas.add(new Checked_bean("头条"));
        mCheckDatas.add(new Checked_bean("江苏"));
        mCheckDatas.add(new Checked_bean("在现场"));
        mCheckDatas.add(new Checked_bean("江苏卫视"));
        mCheckDatas.add(new Checked_bean("荔枝锐评"));
    }

}
