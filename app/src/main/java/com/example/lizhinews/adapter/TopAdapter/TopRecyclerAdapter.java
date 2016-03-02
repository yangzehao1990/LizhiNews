package com.example.lizhinews.adapter.TopAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lizhinews.bean.top_bean.topBean.FeedsData;
import com.example.lizhinews.ui.InfoActivity;
import com.example.lizhinews.ui.R;
import com.example.lizhinews.utils.DisplayImageUtils;
import com.example.lizhinews.utils.Url;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;


public class TopRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 数据源
     */
    private List<FeedsData> mDatas;

    /**
     * ImageLoader 配置
     */
    private DisplayImageOptions mOptions;

    /**
     * 图片新闻
     */
    private final int MAP = 0;

    /**
     * 标题新闻
     */
    private final int ARTICLE = 1;

    /**
     * 头部新闻
     */
    private final int HEADER = 2;

    /**
     * 尾视图
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
     *  是否有HeaderView
     */
    private boolean mIsHeaderView = false;

    /**
     * 是否有FooterView
     */
    private boolean mIsFooterView = false;

    /**
     * 设置头View
     * @param headerView
     */
    public void setHeaderView(View headerView)
    {
        this.mHeaderView = headerView;
        //mDatas.add(0,new FeedsData());
        mIsHeaderView = true;
    }

    /**
     * 设置尾部
     */
    public void setFooterView(View footerView)
    {
        this.mFooterView = footerView;
       // mDatas.add(mDatas.size(),new FeedsData());
        mIsFooterView = true;
    }

    /**
     * 得到所有Json数据
     * @return
     */
    public List<FeedsData> getDatas()
    {
        return mDatas;
    }

    /**
     * 添加所有Json数据
     * @param datas
     */
    public void setDatas(List<FeedsData> datas)
    {
        this.mDatas = datas;
    }

    public TopRecyclerAdapter(Context context, List<FeedsData> datas)
    {
        initLoader();
        mDatas = new ArrayList<FeedsData>();
        this.mContext = LayoutInflater.from(context).getContext();
        this.mDatas = datas;
    }

    /**
     * 创建布局
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType)
        {
            case ARTICLE:
                view = LayoutInflater.from(mContext).inflate(R.layout.top_feeds,null);
                viewHolder = new ArticleHolder(view);
                break;

            case MAP:
                view = LayoutInflater.from(mContext).inflate(R.layout.top_pics,null);
                viewHolder = new MapHolder(view);
                break;

            case HEADER:
                view = mHeaderView;
                viewHolder = new HeaderHolder(view);
                break;

            case FOOTER:
                view = mFooterView;
                viewHolder = new FooterHolder(view);
                break;
        }
        return viewHolder;
    }

    /**
     * 给控件赋值
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        switch (getItemViewType(position))
        {
            case ARTICLE:
                ArticleHolder articleHolder = (ArticleHolder)holder;
                articleHolder.feed_subject.setText(mDatas.get(position).getData().getSubject());
                articleHolder.feed_summary.setText(mDatas.get(position).getData().getSummary());
                articleHolder.feeds_image.setImageResource(R.mipmap.ic_launcher);
                DisplayImageUtils.DisplayImage(articleHolder.feeds_image, Url.IMAGEURL +
                        mDatas.get(position).getData().getCover(), mOptions);
                //articleHolder.feeds_image.setTag(mDatas.get(position).getData().getCover());
                break;

            case MAP:
                MapHolder mapHolder = (MapHolder)holder;
                mapHolder.map_icon1.setImageResource(R.mipmap.ic_launcher);
                mapHolder.map_icon2.setImageResource(R.mipmap.ic_launcher);
                mapHolder.map_icon3.setImageResource(R.mipmap.ic_launcher);
                mapHolder.map_subject.setText(mDatas.get(position).getData().getSubject());
                DisplayImageUtils.DisplayImage(mapHolder.map_icon1,
                        Url.IMAGEURL + mDatas.get(position).getData().getPics().get(0).getPhoto(),mOptions);
                DisplayImageUtils.DisplayImage(mapHolder.map_icon2,
                        Url.IMAGEURL + mDatas.get(position).getData().getPics().get(1).getPhoto(),mOptions);
                DisplayImageUtils.DisplayImage(mapHolder.map_icon3,
                        Url.IMAGEURL + mDatas.get(position).getData().getPics().get(2).getPhoto(),mOptions);
                break;
        }
    }


    /**
     * 根据category的值来判断添加哪种布局
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position)
    {
        String category = mDatas.get(position).getCategory();

        if(position == 0 &&  mIsHeaderView == true)
        {
            return HEADER;
        }
        else if(position == getItemCount() -1 && mIsFooterView == true)
        {
            return FOOTER;
        }
        else if("map".equals(category))
        {
            return MAP;
        }
        else
        {
            return ARTICLE;
        }

    }

    /**
     * 返回Item的大小
     * @return
     */
    @Override
    public int getItemCount()
    {
        if(mDatas == null)
        {
            return 0;
        }
        else
        {
            return mDatas.size();
        }
    }

    /**
     * 图片新闻Holder，加点击事件
     */
    public class MapHolder extends RecyclerView.ViewHolder
    {
        public TextView map_subject;
        public ImageView map_icon1,map_icon2,map_icon3;
        public MapHolder(final View itemView)
        {
            super(itemView);
            map_subject = (TextView)itemView.findViewById(R.id.pics_subject);
            map_icon1 = (ImageView)itemView.findViewById(R.id.top_icon1);
            map_icon2 = (ImageView)itemView.findViewById(R.id.top_icon2);
            map_icon3 = (ImageView)itemView.findViewById(R.id.top_icon3);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String Oid = mDatas.get( getAdapterPosition()).getOid();
                    Intent intent = new Intent(mContext,InfoActivity.class);
                    intent.putExtra("id",Oid);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    /**
     * 标题新闻Holder,加点击事件
     */
    public class ArticleHolder extends RecyclerView.ViewHolder
    {
        public TextView feed_subject,feed_summary;
        public ImageView feeds_image;
        public ArticleHolder(final View itemView)
        {
            super(itemView);
            feed_subject = (TextView)itemView.findViewById(R.id.top_subject_id);
            feed_summary = (TextView)itemView.findViewById(R.id.top_summary_id);
            feeds_image = (ImageView)itemView.findViewById(R.id.top_feeds_image);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(mContext, InfoActivity.class);
                    String Oid = mDatas.get(getAdapterPosition()).getOid();
                    intent.putExtra("id",Oid);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    /**
     * 头View的Holder
     */
    public class HeaderHolder extends RecyclerView.ViewHolder
    {
        public HeaderHolder(final View itemView)
        {
            super(itemView);
        }
    }

    /**
     * footerView的Holder
     */
    public class FooterHolder extends RecyclerView.ViewHolder
    {
        public FooterHolder(View itemView)
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
