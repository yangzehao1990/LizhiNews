package com.example.lizhinews.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class SplashActivity extends AppCompatActivity
{
    private Handler mHandler = new Handler();
    private boolean mFlag = true;   //判断是否为第一次
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.splash_layout);
        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        startAnimation();

        mHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                boolean isFirst = mSharedPreferences.getBoolean("isFirst", mFlag);
                //第一次
                if (isFirst)
                {
                    Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                    mEditor.putBoolean("isFirst", false);
                    mEditor.commit();
                    startActivity(intent);
                    finish();
                }
                //不是第一次
                else if (!isFirst)
                {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        return false;
    }

    /**
     * 动画效果
     */
    public void startAnimation()
    {
        final AnimationSet animationSet = new AnimationSet(false);
        //AlphaAnimation alphaAnimation = new AlphaAnimation(0.6f, 1.6f);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1.07f, 1f, 1.07f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        //animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setDuration(3000);
        animationSet.setFillAfter(true);
        mRelativeLayout.startAnimation(animationSet);
    }
}
