package com.example.lizhinews.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lizhinews.adapter.TopAdapter.TopInfoAdapter;
import com.example.lizhinews.bean.top_bean.topInfoBean.InfoContents;
import com.example.lizhinews.utils.Url;

import java.util.ArrayList;


public class InfoActivity extends AppCompatActivity
{
//    private String mSubject;
//    private String mSummary;
//    private String mOrigined;
//    private String mChanged;

    /**
     * Volley请求队列
     */
    private RequestQueue mRequestQueue;


    /**
     * 解析后的json,标题
     */
    private String mSubtitle;


    /**
     * 解析后的json,机构
     */
    private String mOrigin;

    /**
     * 解析后的json,创建时间
     */
    private String mCreated;

    /**
     * 数据源
     */
    private ArrayList<InfoContents> mDatas;

    /**
     * 列表
     */
    private RecyclerView mRecyclerView;

    /**
     * 适配器
     */
    private TopInfoAdapter mAdapter;

    /**
     * title
     */
    private Toolbar mToolbar;

    /**
     * 头View
     */
    private View mHeaderView;

    /**
     * 正在加载
     */
    private LinearLayout mLoadmoreLayout;

    /**
     * 标题
     */
    private TextView mSubTitleTv;

    /**
     * 机构
     */
    private TextView mOriginTv;

    /**
     * 创建时间
     */
    private TextView mCreateTv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mRecyclerView  = (RecyclerView)findViewById(R.id.top_info_recycler_id);
        mLoadmoreLayout = (LinearLayout)findViewById(R.id.top_info_loadmore_id);
        mRequestQueue = Volley.newRequestQueue(InfoActivity.this);
        mDatas = new ArrayList<InfoContents>();
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        loadJson(Url.Information_url1 + id + Url.Information_url2);
        initToolbar();
        initRecycler();
        setInfoHeader();
 //       setInfoFooter();
    }

//    private void setInfoFooter()
//    {
//        LinearLayout footLayout = new LinearLayout(InfoActivity.this);
//    }

    /**
     * 设置头View
     */
    private void setInfoHeader()
    {
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.top_info_header, null);
        mSubTitleTv = (TextView)mHeaderView.findViewById(R.id.top_info_subtitle);
        mOriginTv = (TextView)mHeaderView.findViewById(R.id.top_info_origin);
        mCreateTv = (TextView)mHeaderView.findViewById(R.id.top_info_create);
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecycler()
    {
        LinearLayoutManager manager = new LinearLayoutManager(InfoActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
    }

    /**
     * 初始化Toolbar
     */
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
                InfoActivity.this.finish();
            }
        });
    }

    /**
     * 下载json
     * @param url
     */
    private void loadJson(String url)
    {
        StringRequest request = new StringRequest(url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String s)
            {
                parseJson(s);
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
     * @param json
     */
    private void parseJson(String json)
    {
        JSONObject object = JSONObject.parseObject(json);
        String status = object.getString("status");
        if(status.equals("ok"))
        {
            JSONObject paramzObj  = object.getJSONObject("paramz");
            JSONObject articleObj = paramzObj.getJSONObject("article");
           // mSubject = articleObj.getString("subject");
            mSubtitle = articleObj.getString("subtitle");
           // mSummary = articleObj.getString("summary");
            JSONArray array = articleObj.getJSONArray("contents");
            mOrigin = articleObj.getString("origin");
          //  mOrigined = articleObj.getString("origined");
           // mChanged = articleObj.getString("changed");
            mCreated = articleObj.getString("created");
            mDatas = (ArrayList<InfoContents>)
                    JSONArray.parseArray(array.toString(),InfoContents.class);
            mAdapter = new TopInfoAdapter(InfoActivity.this,mDatas);
            ArrayList<String> datas = new ArrayList<String>();//将图片链接保存
            int count = 0; //图片的索引
            for(int i = 0; i < mDatas.size(); i++)
            {
                if(mDatas.get(i).getLink() != null)
                {
                    datas.add(mDatas.get(i).getLink());
                    count++;
                    mAdapter.mImagePosition.put(i+1,count);
                }

            }
            mAdapter.setImageDatas(datas);
            mSubTitleTv.setText(mSubtitle);
            mOriginTv.setText(mOrigin);
            mCreateTv.setText(mCreated);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setHeaderView(mHeaderView);
            mLoadmoreLayout.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
