package com.example.lizhinews.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.example.lizhinews.ui.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 二次封装ImageLoader,实现下载加显示
 */
public class DisplayImageUtils
{
    public static void DisplayImage(final ImageView imageView,String path,DisplayImageOptions options)
    {
        ImageLoader.getInstance().loadImage(path, options, new ImageLoadingListener()
        {
            @Override
            public void onLoadingStarted(String imageUri, View view)
            {
                imageView.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason)
            {
                imageView.setImageResource(R.mipmap.ic_launcher);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
            {
                imageView.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view)
            {

            }
        });
    }
}
