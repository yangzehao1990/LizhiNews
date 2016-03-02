package com.example.lizhinews.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lizhinews.adapter.TopAdapter.RecyclerHeaderAdapter;
import com.example.lizhinews.adapter.TopAdapter.TopRecyclerAdapter;
import com.example.lizhinews.bean.top_bean.topBean.FeedsData;
import com.example.lizhinews.bean.top_bean.topHeaderBean.TopsHeaderData;
import com.example.lizhinews.database.HeaderNewsDB;
import com.example.lizhinews.database.NewsDB;
import com.example.lizhinews.ui.R;
import com.example.lizhinews.utils.DensityUtil;
import com.example.lizhinews.utils.NetUtils;
import com.example.lizhinews.utils.Url;
import com.example.lizhinews.view.DividerItemDecoration;

import java.util.ArrayList;


public class TopFragment extends Fragment
{
    /**
     * 列表
     */
    private RecyclerView mRecyclerView;

    /**
     * 下拉刷新组件
     */
    private SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * RecyclerView 的适配器
     */
    private TopRecyclerAdapter mRecyclerAdapter;

    /**
     * Volley的请求队列
     */
    private RequestQueue mRequestQueue;

    /**
     * RecyclerView 的布局管理器
     */
    private LinearLayoutManager mManager;

    /**
     * RecyclerView的数据源，解析好的json
     */
    private ArrayList<FeedsData> mParseArray;

    /**
     * headerView的viewpager
     */
    private ViewPager mTopViewPager;

    /**
     * headerView的viewpagerAdapter
     */
    private RecyclerHeaderAdapter mHeaderAdapter;

    /**
     * 头View的5个标识小点
     */
    private ImageView[] mImageViews = new ImageView[5];

    /**
     * 列表页码
     */
    private int mCurPageIndex=1;

    /**
     * 头部ViewPager页码
     */
    private int mViewPagerIndex = 0;

    /**
     * RecyclerView 最后一个Item的索引
     */
    private int mLastVisibleItem;

    /**
     * 数据库，缓存json
     */
    private NewsDB mNewsDB;

    /**
     * 头View数据库缓存
     */
    private HeaderNewsDB mHeaderNewsDB;

    /**
     * footerView 整体布局
     */
    private View mJsFooter_view;

    /**
     * footerView 的进度条
     */
    private ProgressBar mJsFooter_progress;

    /**
     * footerView 的正在加载
     */
    private TextView mJsFooter_text;

    /**
     *  headerView的数据源
     */
    ArrayList<TopsHeaderData> mHeaderDatas;

    /**
     * 正在加载
     */
    private LinearLayout mLoadmoreLayout;


    /**
     * 头部
     */
    private View mHeaderView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_top, null);
        mRequestQueue = Volley.newRequestQueue(getContext());
        mRecyclerView = (RecyclerView)view.findViewById(R.id.top_recycler_id);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.top_swipe_id);
        mLoadmoreLayout = (LinearLayout)view.findViewById(R.id.top_loadmore_id);
        initRecycler();
        initHeader();
        /**
         * 下拉刷新
         */
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                if(mJsFooter_view != null && mJsFooter_progress.getVisibility() == View.GONE)
                {
                    mJsFooter_progress.setVisibility(View.VISIBLE);
                    mJsFooter_text.setText(R.string.loadmore);
                }
                mImageViews[0].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_015);
                mImageViews[1].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                mImageViews[2].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                mImageViews[3].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                mImageViews[4].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                if(NetUtils.NetWorkUtils(getContext()) &&
                        mHeaderAdapter.mImageLoader.getMemoryCache() != null)
                {
                    loadUpdata();
                    loadHeaderJson(Url.TOP_URL1 + 0 + Url.TOP_URL2,true);
                    mHeaderAdapter.mImageLoader.clearMemoryCache();
                    Toast.makeText(getActivity(), R.string.refresh_finish, Toast.LENGTH_SHORT).show();
                }
                else if(!NetUtils.NetWorkUtils(getContext()))
                {
                    Toast.makeText(getContext(),R.string.no_network,Toast.LENGTH_SHORT).show();
                }
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });

        /**
         * 上拉加载
         */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItem + 1 == mRecyclerAdapter.getItemCount())
                {
                    loadMorePage();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mManager.findLastVisibleItemPosition();
            }
        });
        return view;
    }

    /**
     * 下拉刷新
     */
    private void loadUpdata()
    {
        mCurPageIndex = 1;
        loadListJson(Url.FRONTPAGEURL1 + mCurPageIndex + Url.FRONTPAGEURL2, false);
    }

    /**
     * 上拉加载
     */
    private void loadMorePage()
    {
        if(mCurPageIndex <= 20)
        {
            mCurPageIndex++;
            loadListJson(Url.FRONTPAGEURL1 + mCurPageIndex + Url.FRONTPAGEURL2, true);
        }
        else
        {
            mJsFooter_progress.setVisibility(View.GONE);
            mJsFooter_text.setText(R.string.all_to_load);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mRecyclerAdapter.setHeaderView(mHeaderView);
        mHeaderNewsDB = new HeaderNewsDB(getContext());
        String json = mHeaderNewsDB.getJson(0);
        if(mHeaderDatas != null)
        {
            mTopViewPager.setAdapter(mHeaderAdapter);
            //mTopViewPager.setCurrentItem(mHeaderDatas.size()*30);
        }
        if (!TextUtils.isEmpty(json) && mHeaderDatas == null)
        {
            parseHeaderJson(json, false);
        }
        else if(TextUtils.isEmpty(json))
        {
            loadHeaderJson(Url.TOP_URL1 + mViewPagerIndex + Url.TOP_URL2,true);
        }
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState)
//    {
//        super.onSaveInstanceState(outState);
//        outState.putInt("select_pager",mHeaderDatas.size() * 30);
//    }
//
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
//    {
//        super.onViewStateRestored(savedInstanceState);
//        if(savedInstanceState != null)
//        {
//            int count = savedInstanceState.getInt("select_pager");
//            mTopViewPager.setCurrentItem(count);
//        }
//
//    }

    /**
     * 头View
     */
    private void initHeader()
    {
        mViewPagerIndex = 0;

        //得到屏幕管理器
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        //得到屏幕的宽高
        int width = size.x;
        int height = size.y;
        mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.top_header,null);  //headerView布局
        FrameLayout frameLayout = (FrameLayout)mHeaderView.findViewById(R.id.header_fragement_id);
        LinearLayout linearLayout = (LinearLayout)frameLayout.findViewById(R.id.top_guide_modle);
        View guide_layout = LayoutInflater.from(getContext()).inflate(R.layout.top_header_guide,null);
        linearLayout.addView(guide_layout);
        mImageViews[0] = (ImageView) guide_layout.findViewById(R.id.top_header_guide1);
        mImageViews[1] = (ImageView) guide_layout.findViewById(R.id.top_header_guide2);
        mImageViews[2] = (ImageView) guide_layout.findViewById(R.id.top_header_guide3);
        mImageViews[3] = (ImageView) guide_layout.findViewById(R.id.top_header_guide4);
        mImageViews[4] = (ImageView) guide_layout.findViewById(R.id.top_header_guide5);
        mImageViews[0].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_015);
        mTopViewPager = new ViewPager(getContext());
        final FrameLayout.LayoutParams viewpager_params = new FrameLayout.LayoutParams(width,height/10*3);
        mTopViewPager.setLayoutParams(viewpager_params);
        //mRecyclerAdapter.setHeaderView(mHeaderView);
//        mHeaderNewsDB = new HeaderNewsDB(getContext());
//        String json = mHeaderNewsDB.getJson(0);
//        if (!TextUtils.isEmpty(json) && mHeaderDatas == null)
//        {
//            parseHeaderJson(json, false);
//        }
//        else if(TextUtils.isEmpty(json))
//        {
//            loadHeaderJson(Url.TOP_URL1 + mViewPagerIndex + Url.TOP_URL2,true);
//        }

        /**
         * ViewPager 的滑动事件，控制小点改变
         */
        mTopViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                switch (position % mImageViews.length)
                {
                    case 0:
                        mImageViews[0].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_015);
                        mImageViews[1].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        mImageViews[2].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        mImageViews[3].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        mImageViews[4].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        break;

                    case 1:
                        mImageViews[0].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        mImageViews[1].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_015);
                        mImageViews[2].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        mImageViews[3].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        mImageViews[4].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        break;

                    case 2:
                        mImageViews[0].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        mImageViews[1].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        mImageViews[2].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_015);
                        mImageViews[3].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        mImageViews[4].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        break;

                    case 3:
                        mImageViews[0].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        mImageViews[1].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        mImageViews[2].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        mImageViews[3].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_015);
                        mImageViews[4].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        break;

                    case 4:
                        mImageViews[0].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        mImageViews[1].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        mImageViews[2].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        mImageViews[3].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_000);
                        mImageViews[4].setImageResource(R.mipmap.abc_btn_radio_to_on_mtrl_015);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
        frameLayout.addView(mTopViewPager, 0);
    }

    /**
     * footerView 正在加载
     * @return
     */
    private View footerView()
    {
        mJsFooter_view = LayoutInflater.from(getContext()).inflate(R.layout.footer_view,null);
        int height_dp = DensityUtil.dippx(getContext(), 50);
        LinearLayout footer_linear = (LinearLayout) mJsFooter_view.findViewById(R.id.footer_view_linear);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, height_dp);
        footer_linear.setLayoutParams(params);
        footer_linear.setGravity(Gravity.CENTER);
        mJsFooter_progress = (ProgressBar) mJsFooter_view.findViewById(R.id.footer_view_pro);
        mJsFooter_text = (TextView) mJsFooter_view.findViewById(R.id.footer_view_text);
        return mJsFooter_view;
    }

    /**
     * 初始化RecyclerView控件,设置头部
     */
    private void initRecycler()
    {
        mManager = new LinearLayoutManager(getContext());
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mManager);

        //设置分割线,只有纵向的时候才能显示
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerAdapter = new TopRecyclerAdapter(getContext(),null); //设置空数据，方便完成上拉和下拉功能
        mNewsDB = new NewsDB(getContext());
        String json = mNewsDB.getJson(0);
        if(!TextUtils.isEmpty(json))
        {
            parseListJson(json,false);
            mRecyclerAdapter.setFooterView(footerView());
        }
        if(mParseArray != null && TextUtils.isEmpty(json))
        {
            mRecyclerAdapter.setFooterView(footerView());
        }
        mRecyclerView.setHasFixedSize(true);   //设置RecyclerView的Item的高度固定
        mRecyclerView.setAdapter(mRecyclerAdapter);
        loadListJson(Url.FRONTPAGEURL1 + mCurPageIndex + Url.FRONTPAGEURL2, false);
    }

    /**
     * 下载Json数据
     * @param url 路径
     * @param isAdd  是否追加
     */
    private void loadListJson(String url, final boolean isAdd)
    {
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String s)
                    {
                        parseListJson(s, isAdd);
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {

            }
        });
        mRequestQueue.add(request);
    }

    /**
     * 解析Json,先从数据库中拿到json;上拉追加数据，下拉不追加数据
     * @param json
     * @param isAddData true 表示追加，false 表示不追加
     */
    private void parseListJson(String json,boolean isAddData)
    {
        if(!isAddData)
        {
            mNewsDB.insert(json,0);
        }
        JSONObject topObject = JSONObject.parseObject(json);
        String status = topObject.getString("status");
        if(status.equals("ok"))
        {
            JSONObject obj = topObject.getJSONObject("paramz");
            JSONArray array = obj.getJSONArray("feeds");
            mParseArray = (ArrayList<FeedsData>)
                    JSONArray.parseArray(array.toString(),FeedsData.class);
            if (isAddData)
            {
                mRecyclerAdapter.getDatas().addAll(mParseArray);
            }
            else
            {
                mRecyclerAdapter.setDatas(mParseArray);
                mRecyclerAdapter.setFooterView(footerView());
            }
            mRecyclerAdapter.notifyDataSetChanged();
            mLoadmoreLayout.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 头View Json       数据解析
     * @param url        地址
     */
    private void loadHeaderJson(String url, final boolean isAdd)
    {
        StringRequest request = new StringRequest(url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String s)
            {
                parseHeaderJson(s,isAdd);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {

            }
        });
        mRequestQueue.add(request);
    }

    /**
     *
     * @param json
     * @param isAdd true 表示缓存 ，false 表示不缓存
     */
    private void parseHeaderJson(String json,boolean isAdd)
    {
        if (isAdd)
        {
            mHeaderNewsDB.insert(json,0);
        }
        JSONObject object = JSONObject.parseObject(json);
        String status = object.getString("status");
        if(status.equals("ok"))
        {
            JSONObject paramzObj = object.getJSONObject("paramz");
            JSONArray array = paramzObj.getJSONArray("tops");
            mHeaderDatas = (ArrayList<TopsHeaderData>)
                    JSONArray.parseArray(array.toString(), TopsHeaderData.class);
            mHeaderAdapter = new RecyclerHeaderAdapter(getContext(),mHeaderDatas);
            mTopViewPager.setAdapter(mHeaderAdapter);
        }
        mHeaderAdapter.notifyDataSetChanged();
      //  mTopViewPager.setCurrentItem(mHeaderDatas.size()*30);
    }
}
