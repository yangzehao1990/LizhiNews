package com.example.lizhinews.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lizhinews.adapter.TopAdapter.SplashViewPagerAdapter;

import java.util.ArrayList;


public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener
{
    private ArrayList<View> mViews;
    private ImageView mGuideImage1, mGuideImage2, mGuideImage3;
    private SplashViewPagerAdapter adapter;
    private View mGuideView1, mGuideView2, mGuideView3;
    private Button mStartBtn;
    private ViewPager mGuidePager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        mGuidePager = (ViewPager)findViewById(R.id.guide_viewpager);
        mGuideImage1 = (ImageView) findViewById(R.id.guide_image1);
        mGuideImage2 = (ImageView) findViewById(R.id.guide_image2);
        mGuideImage3 = (ImageView) findViewById(R.id.guide_image3);

        mGuideView1 = LayoutInflater.from(this).inflate(R.layout.guide_one, null);
        mGuideView2 = LayoutInflater.from(this).inflate(R.layout.guide_two, null);
        mGuideView3 = LayoutInflater.from(this).inflate(R.layout.guide_three, null);


        mStartBtn = (Button)mGuideView3.findViewById(R.id.guide_start_id);

        mStartBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        mViews = new ArrayList<View>();
        mViews.add(mGuideView1);
        mViews.add(mGuideView2);
        mViews.add(mGuideView3);

        adapter = new SplashViewPagerAdapter(GuideActivity.this, mViews);

        mGuidePager.setAdapter(adapter);
        mGuidePager.addOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {
        switch (position)
        {
            case 0:
                mGuideImage1.setImageResource(R.drawable.page_indicator_focused);
                mGuideImage2.setImageResource(R.drawable.page_indicator_unfocused);
                mGuideImage3.setImageResource(R.drawable.page_indicator_unfocused);
                break;

            case 1:
                mGuideImage1.setImageResource(R.drawable.page_indicator_unfocused);
                mGuideImage2.setImageResource(R.drawable.page_indicator_focused);
                mGuideImage3.setImageResource(R.drawable.page_indicator_unfocused);
                break;

            case 2:
                mGuideImage1.setImageResource(R.drawable.page_indicator_unfocused);
                mGuideImage2.setImageResource(R.drawable.page_indicator_unfocused);
                mGuideImage3.setImageResource(R.drawable.page_indicator_focused);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }
}
