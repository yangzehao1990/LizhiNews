package com.example.lizhinews.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.lizhinews.adapter.SelectAdapter.CheckedAdapter;
import com.example.lizhinews.adapter.SelectAdapter.UnCheckedAdapter;
import com.example.lizhinews.bean.select_bean.Checked_bean;
import com.example.lizhinews.bean.select_bean.UnChecked_bean;
import com.example.lizhinews.callback.ItemClickListener;
import com.example.lizhinews.callback.ItemLongClickListener;
import com.example.lizhinews.utils.SelectManager;
import com.example.lizhinews.view.CheckedManager;

import java.util.ArrayList;


public class SelectActivity extends AppCompatActivity
{
    private RecyclerView mCheckedRecycler;
    private RecyclerView mUnCheckedRecycler;
    private CheckedAdapter mCheckedAdapter;
    private UnCheckedAdapter mUnCheckedAdapter;
    private CheckedManager mCheckedManager;
    private CheckedManager mUnCheckedManager;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        mCheckedRecycler = (RecyclerView)findViewById(R.id.mycheck_channel);
        mUnCheckedRecycler = (RecyclerView)findViewById(R.id.uncheck_channel);
        SelectManager.mCheckDatas = new ArrayList<Checked_bean>();
        SelectManager.mUnCheckDatas = new ArrayList<UnChecked_bean>();
        SelectManager.addCheckDatas();
        SelectManager.addUncheckDatas();
        initCheckItem();
        initUnCheckItem();
    }


    private void initCheckItem()
    {
        mCheckedAdapter = new CheckedAdapter(SelectActivity.this,SelectManager.mCheckDatas);
        mCheckedManager = new CheckedManager(SelectActivity.this,4);
        mCheckedRecycler.setLayoutManager(mCheckedManager);
        mCheckedRecycler.setAdapter(mCheckedAdapter);
        mCheckedAdapter.setOnItemClickListener(new ItemClickListener()
        {
            @Override
            public void onItemClick(View view, int postion, String name)
            {
                if (SelectManager.mCheckDatas.size() <= 5)
                {
                    Toast.makeText(SelectActivity.this,R.string.not_less_than_five,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SelectManager.mCheckDatas.remove(postion);
                    mCheckedAdapter.notifyDataSetChanged();
                    SelectManager.mUnCheckDatas.add(new UnChecked_bean(name));
                    mUnCheckedAdapter.notifyDataSetChanged();
                }
            }
        });

        mCheckedAdapter.setOnItemLongClickListener(new ItemLongClickListener()
        {
            @Override
            public void onItemLongClick(View view, int postion)
            {
                Toast.makeText(SelectActivity.this,"选中长点击"+postion,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUnCheckItem()
    {
        mUnCheckedAdapter = new UnCheckedAdapter(SelectActivity.this,SelectManager.mUnCheckDatas);
        mUnCheckedManager = new CheckedManager(SelectActivity.this,4);
        mUnCheckedRecycler.setLayoutManager(mUnCheckedManager);
        mUnCheckedRecycler.setAdapter(mUnCheckedAdapter);
        mUnCheckedAdapter.setOnItemClickListener(new ItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position, String name)
            {
                SelectManager.mUnCheckDatas.remove(position);
                mUnCheckedAdapter.notifyDataSetChanged();
                SelectManager.mCheckDatas.add(new Checked_bean(name));
                mCheckedAdapter.notifyDataSetChanged();
            }
        });

        mUnCheckedAdapter.setOnItemLongClickListener(new ItemLongClickListener()
        {
            @Override
            public void onItemLongClick(View view, int postion)
            {
                Toast.makeText(SelectActivity.this,"长点击"+postion,Toast.LENGTH_SHORT).show();
            }
        });

    }


}
