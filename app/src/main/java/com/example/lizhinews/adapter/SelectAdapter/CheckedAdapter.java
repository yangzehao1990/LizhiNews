package com.example.lizhinews.adapter.SelectAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lizhinews.bean.select_bean.Checked_bean;
import com.example.lizhinews.callback.ItemClickListener;
import com.example.lizhinews.callback.ItemLongClickListener;
import com.example.lizhinews.ui.R;

import java.util.ArrayList;


public class CheckedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private Context mContext;
    private ArrayList<Checked_bean> mDatas;
    private ItemClickListener mItemClickListener;
    private ItemLongClickListener mItemLongClickListener;

    public CheckedAdapter(Context context, ArrayList<Checked_bean> datas)
    {
        this.mContext = LayoutInflater.from(context).getContext();
        this.mDatas = datas;
    }

    public void setOnItemClickListener(ItemClickListener listener)
    {
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(ItemLongClickListener listener)
    {
        this.mItemLongClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.select_item,null);
        CheckedViewHolder holder = new CheckedViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        CheckedViewHolder checkedViewHolder = (CheckedViewHolder)holder;
        checkedViewHolder.checkItem.setText(mDatas.get(position).getName());

        checkedViewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mItemClickListener != null)
                {
                    String name = mDatas.get(position).getName();
                    if (position != 0)
                    {
                        mItemClickListener.onItemClick(v,position,name);
                    }
                }
            }
        });

        checkedViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                if (mItemLongClickListener != null)
                {
                    mItemLongClickListener.onItemLongClick(v,position);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }


    public class CheckedViewHolder extends RecyclerView.ViewHolder
    {
        private TextView checkItem;
        private RelativeLayout relativeLayout;
        public CheckedViewHolder(View itemView)
        {
            super(itemView);
            checkItem = (TextView)itemView.findViewById(R.id.select_item_id);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.select_layout_id);
        }
    }
}
