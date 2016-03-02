package com.example.lizhinews.adapter.TopAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class MainFragmentAdapter extends FragmentPagerAdapter
{
    private ArrayList<Fragment> mFragments;
    private Context mContext;
    public MainFragmentAdapter(FragmentManager fm,Context context,ArrayList<Fragment> fragments)
    {
        super(fm);
        this.mContext = context;
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position)
    {
        return mFragments.get(position);
    }

    @Override
    public int getCount()
    {
        return mFragments.size();
    }
}
