package com.example.lizhinews.adapter.TopAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.lizhinews.ui.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;


public class InfoImageAdapter extends PagerAdapter
{
    private Context mContext;
    private ArrayList<View> mViews;
    private ArrayList<String> mDatas;
    private DisplayImageOptions mOptions;
    private ImageLoader mLoader;

    private RequestQueue mRequestQueue;

    private ImageRequest mImageRequest;

    public InfoImageAdapter(Context context, ArrayList<String> datas)
    {
        this.mDatas = datas;
        this.mContext = LayoutInflater.from(context).getContext();
        mViews = new ArrayList<View>();
        mRequestQueue = Volley.newRequestQueue(mContext);
        for(int i = 0; i < mDatas.size(); i++)
        {
            initLoader();
            View view  = LayoutInflater.from(context).inflate(R.layout.top_info_image_item,null);
            final ImageView itemImage = (ImageView)view.findViewById(R.id.top_info_images);
            TextView itemCountTxt = (TextView)view.findViewById(R.id.top_info_count);
            itemCountTxt.setText(i+1 +" / "+mDatas.size());
            //DisplayImageUtils.DisplayImage(itemImage, datas.get(i), mOptions);
            //mLoader.displayImage(datas.get(i),itemImage,mOptions);

            mImageRequest = new ImageRequest(datas.get(i), new Response.Listener<Bitmap>()
            {
                @Override
                public void onResponse(Bitmap bitmap)
                {
                    itemImage.setImageBitmap(bitmap);
                }
            }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError volleyError)
                {

                }
            });
            mViews.add(view);
            mRequestQueue.add(mImageRequest);
        }
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
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

    /**
     * 初始化ImageLoader
     */
    private void initLoader()
    {
        mLoader = ImageLoader.getInstance();

        mOptions = new DisplayImageOptions.Builder()

                //加载过程中显示的图片
                .showImageOnLoading(R.mipmap.ic_launcher)

                        //设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)

                        //加载失败显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)

                        //设置下载的图片是否缓存在内存中
                .cacheInMemory(true)

                        //设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(true)

                        //启用EXIF和JPEG图像格式
                .considerExifParams(true)

                        //设置显示风格这里是圆角矩形
                .displayer(new RoundedBitmapDisplayer(20))

                        //设置图片类型
                .bitmapConfig(Bitmap.Config.RGB_565)

                .displayer(new FadeInBitmapDisplayer(388)).build();
    }
}
