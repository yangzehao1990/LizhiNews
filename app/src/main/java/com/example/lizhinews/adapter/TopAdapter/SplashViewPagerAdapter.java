package com.example.lizhinews.adapter.TopAdapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class SplashViewPagerAdapter extends PagerAdapter
{
    private Context mContext;
    private ArrayList<View> mViews;


    public SplashViewPagerAdapter(Context context, ArrayList<View> views)
    {
        this.mContext = context;
        this.mViews = views;
    }
    @Override
    public int getCount()
    {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView(mViews.get(position));
    }
}
