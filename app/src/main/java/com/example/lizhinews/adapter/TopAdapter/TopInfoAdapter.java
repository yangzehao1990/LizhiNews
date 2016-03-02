package com.example.lizhinews.adapter.TopAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lizhinews.bean.top_bean.topInfoBean.InfoContents;
import com.example.lizhinews.ui.ImageActivity;
import com.example.lizhinews.ui.R;
import com.example.lizhinews.utils.DisplayImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;


public class TopInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    /**
     * 数据源
     */
    private ArrayList<InfoContents> mDatas;

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * ImageLoader配置
     */
    private DisplayImageOptions mOptions;

    /**
     * 头部
     */
    private final int HEADER = 1;

    /**
     * 文字
     */
    private final int TXT = 2;

    /**
     * 图片
     */
    private final int IMAGE = 3;

    /**
     * 尾部
     */
    private final int FOOTER = 4;

    /**
     * 是否加载头部
     */
    private boolean isHeader = false;

    /**
     * 是否加载尾部
     */
    private boolean isFooter = false;

    /**
     * 头view
     */
    private View mHeaderView;

    /**
     * 尾view
     */
    private View mFooterView;

    /**
     *  图片的路径
     */
    private ArrayList<String> mImageDatas = new ArrayList<String>();

    /**
     * 图片的索引
     */
    public HashMap<Integer,Integer> mImagePosition = new HashMap<Integer,Integer>();


   // private int mCount = 0;

    /**
     * 存放图片的网址
     * @param imageDatas
     */
    public void setImageDatas(ArrayList<String> imageDatas)
    {
        this.mImageDatas = imageDatas;
    }

    public ArrayList<String> getmImageDatas()
    {
        return mImageDatas;
    }

    /**
     * 设置头部
     * @param headerView
     */
    public void setHeaderView(View headerView)
    {
        mDatas.add(0,new InfoContents());
        this.mHeaderView = headerView;
        isHeader = true;
    }

    /**
     * 设置尾部
     * @param footerView
     */
    public void setFooterView(View footerView)
    {
        mDatas.add(mDatas.size(),new InfoContents());
        this.mFooterView = footerView;
        isFooter = true;
    }

    public TopInfoAdapter(Context context, ArrayList<InfoContents> datas)
    {
        this.mContext = LayoutInflater.from(context).getContext();
        this.mDatas = datas;
        initLoader();
    }

    /**
     * 创建item布局
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType)
        {
            case HEADER:
                view = mHeaderView;
                viewHolder = new HeaderHolder(view);
                break;

            case TXT:
                view = LayoutInflater.from(mContext).inflate(R.layout.info_txt_item,null);
                viewHolder = new TxtHolder(view);
                break;

            case IMAGE:
                view = LayoutInflater.from(mContext).inflate(R.layout.info_image_item,null);
                viewHolder = new ImageHolder(view);
                break;

            case FOOTER:
                view = mFooterView;
                viewHolder = new FooterHolder(view);
                break;
        }
        return viewHolder;
    }

    /**
     * 绑定数据源
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        switch (getItemViewType(position))
        {
            case TXT:
                TxtHolder txtHolder = (TxtHolder)holder;
                txtHolder.textView.setText("        " + mDatas.get(position).getText());
                break;

            case IMAGE:
                ImageHolder imageHolder = (ImageHolder)holder;
                DisplayImageUtils.DisplayImage(imageHolder.imageView, mDatas.get(position).getLink(), mOptions);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                imageHolder.linearLayout.setLayoutParams(params);
                break;
        }
    }

    /**
     * 返回数据源大小
     * @return
     */
    @Override
    public int getItemCount()
    {
        if(mDatas != null)
        {
            return mDatas.size();
        }
        else
        {
            return 0;
        }
    }

    /**
     * 返回布局类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position)
    {
        if(position == 0 && isHeader == true)
        {
            return HEADER;
        }
        else if (position == getItemCount()-1 && isFooter == true)
        {
            return FOOTER;
        }
        else if (mDatas.get(position).getCategory().equals("txt"))
        {
            return TXT;
        }
        else
        {
            return IMAGE;
        }
    }

    class TxtHolder extends RecyclerView.ViewHolder
    {
        public TextView textView;
        public TxtHolder(View itemView)
        {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.top_info_txt);
        }
    }


    class HeaderHolder extends RecyclerView.ViewHolder
    {
        public HeaderHolder(View itemView)
        {
            super(itemView);
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder
    {

        public FooterHolder(View itemView)
        {
            super(itemView);
        }
    }


    class ImageHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public LinearLayout linearLayout;
        public ImageHolder(final View itemView)
        {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.top_info_image);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.top_info_layout);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(mImageDatas == null)
                    {
                        Toast.makeText(mContext,"   ",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Intent intent = new Intent(mContext, ImageActivity.class);
                        intent.putStringArrayListExtra("images", mImageDatas);
                        intent.putExtra("position", mImagePosition.get(getAdapterPosition()));
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }


    /**
     * 初始化ImageLoader
     */
    private void initLoader()
    {
       // mLoader = ImageLoader.getInstance();

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
