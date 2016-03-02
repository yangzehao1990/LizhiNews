package com.example.lizhinews.adapter.TopAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lizhinews.bean.top_bean.topHeaderBean.TopsHeaderData;
import com.example.lizhinews.ui.InfoActivity;
import com.example.lizhinews.ui.R;
import com.example.lizhinews.utils.Url;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;


public class RecyclerHeaderAdapter extends PagerAdapter
{
    private Context mContext;
    private ArrayList<TopsHeaderData> mDatas;
    private ArrayList<View> mViews;
    public  ImageLoader mImageLoader;
    private ImageView mIcon;
    /**
     * ImageLoader 配置
     */
    private DisplayImageOptions mOptions;


    public RecyclerHeaderAdapter(Context context, final ArrayList<TopsHeaderData> datas)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = datas;
        mViews = new ArrayList<View>();
        initLoader();
        for (int i = 0; i < datas.size(); i++)
        {

            View view = inflater.inflate(R.layout.top_header_item,null);
            TextView suject = (TextView)view.findViewById(R.id.top_header_text);
            mIcon = (ImageView)view.findViewById(R.id.top_header_image);
            suject.setText(datas.get(i).getSubject());
            mImageLoader.displayImage(Url.IMAGEURL + datas.get(i).getPhoto(), mIcon, mOptions);
          //  DisplayImageUtils.DisplayImage(icon, Url.IMAGEURL+datas.get(i).getPhoto(),mOptions);
            mViews.add(view);
            final int position = i;
            view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String Oid = datas.get(position % datas.size()).getOid();
                 //   String Id = datas.get(position).getId();
                    Intent intent = new Intent(mContext, InfoActivity.class);
                    intent.putExtra("id",Oid);
                    mContext.startActivity(intent);
                }
            });
        }
    }
    @Override
    public int getCount()
    {
       // return mDatas.size();
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
//        View view = mViews.get(position);
//        container.addView(view);
//        return view;

//        container.addView(mViews.get(Math.abs(position % mViews.size())));
//        return mViews.get(Math.abs(position % mViews.size()));

        position %= mViews.size();
        if (position<0){
            position = mViews.size()+position;
        }
        View view = mViews.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp =view.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        //container.removeView(mViews.get(position));
        //container.removeView(mViews.get(Math.abs(position % mViews.size())));
    }


    /**
     * 初始化ImageLoader
     */
    private void initLoader()
    {
        mImageLoader = ImageLoader.getInstance();

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
