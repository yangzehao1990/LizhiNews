package com.example.lizhinews.adapter.JiangsuAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lizhinews.bean.js_bean.jsBean.JsFeedData;
import com.example.lizhinews.ui.InfoActivity;
import com.example.lizhinews.ui.JsWebViewActivity;
import com.example.lizhinews.ui.R;
import com.example.lizhinews.utils.DisplayImageUtils;
import com.example.lizhinews.utils.Url;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;


public class JsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 数据源
     */
    private ArrayList<JsFeedData> mDatas;

    /**
     * ImageLoader 配置
     */
    private DisplayImageOptions mOptions;

    /**
     * 头View类型
     */
    private final int HEADER = 1;

    /**
     * 体View类型
     */
    private final int BODY = 2;

    /**
     * 尾View类型
     */
    private final int FOOTER = 3;

    /**
     * 头View
     */
    private View mHeaderView;

    /**
     * 尾View
     */
    private View mFooterView;

    /**
     * 是否添加了头View
     */
    private boolean mIsHeaderView = false;

    /**
     * 是否添加了尾View
     */
    private boolean mIsFooterView = false;

    /**
     * 得到所有的数据源
     * @return
     */
    public ArrayList<JsFeedData> getDatas()
    {
        return mDatas;
    }

    /**
     * 添加所有的数据源
     * @param datas
     */
    public void setDatas(ArrayList<JsFeedData> datas)
    {
        this.mDatas = datas;
    }

    /**
     * 添加头View
     * @param headerView
     */
    public void setHeaderView(View headerView)
    {
        this.mIsHeaderView = true;
        this.mHeaderView = headerView;
        mDatas.add(0,new JsFeedData());
    }

    /**
     * 添加尾View
     * @param footerView
     */
    public void setFooterView(View footerView)
    {
        this.mIsFooterView = true;
        this.mFooterView = footerView;
       // mDatas.add(mDatas.size(),new JsFeedData());
    }


    public JsRecyclerAdapter(Context context,ArrayList<JsFeedData> datas)
    {
        this.mContext = LayoutInflater.from(context).getContext();
        this.mDatas = datas;
        initLoader();
    }

    /**
     * 控件绑定布局
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
                viewHolder = new JsHeaderViewHolder(view);
                break;

            case BODY:
                view = LayoutInflater.from(mContext).inflate(R.layout.js_feeds,null);
                viewHolder = new JsListViewHolder(view);
                break;

            case FOOTER:
                view = mFooterView;
                viewHolder = new JsFooterViewHolder(view);
                break;
        }
        return viewHolder;
    }

    /**
     * 控件绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        switch (getItemViewType(position))
        {
            case BODY:
                JsListViewHolder jsListViewHolder = (JsListViewHolder)holder;
                jsListViewHolder.JsSummary_text.setText(mDatas.get(position).getData().getSummary());
                jsListViewHolder.JsSubject_text.setText(mDatas.get(position).getData().getSubject());
                DisplayImageUtils.DisplayImage(
                        jsListViewHolder.JsIcon_image, Url.IMAGEURL + mDatas.get(position).getData().getCover(), mOptions);
                if(!mDatas.get(position).getCategory().equals("advert"))
                {
                    jsListViewHolder.JsSpecial_text.setVisibility(View.GONE);
                }
                else
                {
                    jsListViewHolder.JsSpecial_text.setVisibility(View.VISIBLE);
                }
                break;
        }


    }

    /**
     * 返回数据源类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position)
    {
        if(mIsHeaderView == true && position == 0)
        {
            return HEADER;
        }
        else if(mIsFooterView == true && position == getItemCount() -1)
        {
            return FOOTER;
        }
        else
        {
            return BODY;
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
     * 体View的holder
     */
    class JsListViewHolder extends RecyclerView.ViewHolder
    {
        ImageView JsIcon_image;
        TextView JsSubject_text,JsSummary_text,JsSpecial_text;
        public JsListViewHolder(View itemView)
        {
            super(itemView);
            JsIcon_image = (ImageView)itemView.findViewById(R.id.js_feeds_image);
            JsSubject_text = (TextView)itemView.findViewById(R.id.js_subject_id);
            JsSummary_text = (TextView)itemView.findViewById(R.id.js_summary_id);
            JsSpecial_text = (TextView)itemView.findViewById(R.id.js_special_id);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (mDatas.get(getAdapterPosition()).getCategory().equals("advert"))
                    {
                        //Toast.makeText(mContext,"advert",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext, JsWebViewActivity.class);
                        intent.putExtra("adlink",mDatas.get(getAdapterPosition()).getData().getAdlink());
                        mContext.startActivity(intent);
                    }
                    else
                    {
                        //Toast.makeText(mContext,"article",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext, InfoActivity.class);
                        intent.putExtra("id",mDatas.get(getAdapterPosition()).getOid());
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    /*
     * 头View的holder
     */
    class JsHeaderViewHolder extends RecyclerView.ViewHolder
    {
        public JsHeaderViewHolder(View itemView)
        {
            super(itemView);
        }
    }

    /**
     * 尾View的holder
     */
    class JsFooterViewHolder extends RecyclerView.ViewHolder
    {
        public JsFooterViewHolder(View itemView)
        {
            super(itemView);
        }
    }

    /**
     * 初始化ImageLoader
     */
    private void initLoader()
    {
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
