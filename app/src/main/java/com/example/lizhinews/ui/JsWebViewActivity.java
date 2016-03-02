package com.example.lizhinews.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


public class JsWebViewActivity extends AppCompatActivity
{
    private WebView mWebView;
    private WebSettings mWebSettings;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_webview);
        mWebView = (WebView)findViewById(R.id.js_webview_id);
        mProgressBar = (ProgressBar)findViewById(R.id.js_webview_pro);
        initToolbar();
        Intent intent = getIntent();
        String adlink = intent.getStringExtra("adlink");
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);        //允许使用javaScript代码
        mWebSettings.setUseWideViewPort(true);          //将图片调整到适合webview的大小
        mWebSettings.setLoadWithOverviewMode(true);     //缩放至屏幕的大小
        mWebView.setWebViewClient(new WebViewClient()); //默认网页在本应用中打开
        //设置webView进度条
        mWebView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                super.onProgressChanged(view, newProgress);
                if(newProgress == 100)
                {
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
                else
                {
                    if(View.INVISIBLE == mProgressBar.getVisibility())
                    {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
            }
        });
        mWebView.loadUrl(adlink);
    }


    private void initToolbar()
    {
        mToolbar = (Toolbar)findViewById(R.id.toolbar_id);
        mToolbar.setBackgroundColor(Color.WHITE);
        mToolbar.setTitleTextColor(Color.BLACK);
        mToolbar.setLogo(R.mipmap.newiconnoti);
        mToolbar.setTitle(R.string.info);
        mToolbar.setNavigationIcon(R.mipmap.ic_title_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                JsWebViewActivity.this.finish();
            }
        });
    }

    /**
     * 按返回键返回到上一界面
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack())
        {
            // 返回键退回
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
