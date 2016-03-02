package com.example.lizhinews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.lizhinews.adapter.JiangsuAdapter.JsRecyclerAdapter;
import com.example.lizhinews.bean.js_bean.jsBean.JsFeedData;
import com.example.lizhinews.database.NewsDB;
import com.example.lizhinews.ui.R;
import com.example.lizhinews.utils.DensityUtil;
import com.example.lizhinews.utils.Url;
import com.example.lizhinews.view.DividerItemDecoration;

import java.util.ArrayList;



public class JiangsuFragment extends Fragment
{
    /**
     * 列表
     */
    private RecyclerView mJsRecyclerView;

    /**
     * 下拉刷新组件
     */
    private SwipeRefreshLayout mJsSwipeRefreshLayout;

    /**
     * Volley的请求队列
     */
    private RequestQueue mRequestQueue;

    /**
     * 网络数据的页码
     */
    private int mCurPageIndex = 1;

    /**
     * RecycLerView 的数据源
     */
    private ArrayList<JsFeedData> mParseArray;

    /**
     * RecyclerView 的适配器
     */
    private JsRecyclerAdapter mJsRecyclerAdapter;

    /**
     * RecyclerView 当前页的最后一个Item
     */
    private int mLastVisibleItem;

    /**
     * RecyclerView的布局管理器
     */
    private LinearLayoutManager mManager;

    /**
     * 数据库做缓存
     */
    private NewsDB mNewsDB;

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
     * 正在加载
     */
    private LinearLayout mLoadmoreLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragmnet_js, null);
        mRequestQueue = Volley.newRequestQueue(getContext());
        LoadListJson(Url.JIANGSU_URL1 + mCurPageIndex + Url.JIANGSU_URL2,false);
        initUi(view);

        /**
         * 下拉刷新
         */
        mJsSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                if(mJsFooter_view != null && mJsFooter_progress.getVisibility() == View.GONE)
                {
                    mJsFooter_progress.setVisibility(View.VISIBLE);
                    mJsFooter_text.setText(R.string.loadmore);
                }
                loadUpdata();
                mJsSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), R.string.refresh_finish, Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 上拉加载
         */
        mJsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                if (mLastVisibleItem + 1 == mJsRecyclerAdapter.getItemCount())
                {
                    loadMorePager();
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
     * 初始化Ui
     * @param view
     */
    public void initUi(View view)
    {
        mJsRecyclerView = (RecyclerView)view.findViewById(R.id.js_recycler_id);
        mJsSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.js_swipe_id);
        mLoadmoreLayout = (LinearLayout)view.findViewById(R.id.js_loadmore_id);
        mManager = new LinearLayoutManager(getContext());
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        mJsRecyclerView.setLayoutManager(mManager);
        //设置分割线,只有纵向的时候才能显示
        mJsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));
        mJsRecyclerAdapter = new JsRecyclerAdapter(getContext(),null);

        mNewsDB =  new NewsDB(getContext());
        String json = mNewsDB.getJson(1);
        if(!TextUtils.isEmpty(json))
        {
            praseJson(json, false);
            mJsRecyclerAdapter.setFooterView(footerView());
        }
        if(mParseArray != null)
        {
            mJsRecyclerAdapter.setFooterView(footerView());
        }
        mJsRecyclerView.setHasFixedSize(true);
        mJsRecyclerView.setAdapter(mJsRecyclerAdapter);
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
     * 刷新
     */
    private void loadUpdata()
    {
        mCurPageIndex = 1;
        LoadListJson(Url.JIANGSU_URL1 + mCurPageIndex + Url.JIANGSU_URL2, false);
    }

    /**
     * 加载
     */
    private void loadMorePager()
    {
        if (mCurPageIndex <= 20)
        {
            mCurPageIndex++;
            LoadListJson(Url.JIANGSU_URL1 + mCurPageIndex + Url.JIANGSU_URL2,true);
        }
        else
        {
            mJsFooter_progress.setVisibility(View.GONE);
            mJsFooter_text.setText(R.string.all_to_load);
        }
    }


    /**
     * 下载数据
     * @param url
     * @param isAdd
     */
    private void LoadListJson(String url, final boolean isAdd)
    {
        StringRequest request = new StringRequest(
                url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String s)
            {

                praseJson(s,isAdd);
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
     * 解析Json
     * true 表示追加，false表示不追加
     * @param json
     * @param isAdd
     */
    private void praseJson(String json,boolean isAdd)
    {
        if(!isAdd)
        {
            mNewsDB.insert(json,1);
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        String status = jsonObject.getString("status");
        if(status.equals("ok"))
        {
            JSONObject obj = jsonObject.getJSONObject("paramz");
            JSONArray array = obj.getJSONArray("feeds");
            mParseArray = (ArrayList<JsFeedData>)
                    JSONArray.parseArray(array.toString(),JsFeedData.class);

            if(isAdd == true)
            {
                mJsRecyclerAdapter.getDatas().addAll(mParseArray);
            }
            else
            {
                mJsRecyclerAdapter.setDatas(mParseArray);
                mJsRecyclerAdapter.setFooterView(footerView());
            }
            mJsRecyclerAdapter.notifyDataSetChanged();
            mLoadmoreLayout.setVisibility(View.GONE);
            mJsSwipeRefreshLayout.setVisibility(View.VISIBLE);
        }
    }
}
