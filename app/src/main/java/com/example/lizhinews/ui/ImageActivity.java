package com.example.lizhinews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.lizhinews.adapter.TopAdapter.InfoImageAdapter;

import java.util.ArrayList;


public class ImageActivity extends AppCompatActivity
{
    private ViewPager mViewPager;

    /**
     * 图片的网址
     */
    private ArrayList<String> mDatas;

    /**
     * 所点击ViewPager的索引
     */
    private int mCount;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        mViewPager = (ViewPager) findViewById(R.id.image_viewpager_id);
        Intent intent = getIntent();
        mDatas = new ArrayList<String>();
        mDatas = intent.getStringArrayListExtra("images");
        mCount = intent.getIntExtra("position", -1);
        InfoImageAdapter adapter = new InfoImageAdapter(ImageActivity.this,mDatas);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(mCount-1);
    }
}
