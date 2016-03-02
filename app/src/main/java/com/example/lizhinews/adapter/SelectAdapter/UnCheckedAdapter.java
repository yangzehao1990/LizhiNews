package com.example.lizhinews.adapter.SelectAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lizhinews.bean.select_bean.UnChecked_bean;
import com.example.lizhinews.callback.ItemClickListener;
import com.example.lizhinews.callback.ItemLongClickListener;
import com.example.lizhinews.ui.R;

import java.util.ArrayList;


public class UnCheckedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context mContext;
    private ArrayList<UnChecked_bean> mDatas;
    private ItemClickListener mItemClickListener;
    private ItemLongClickListener mItemLongClickListener;


    public UnCheckedAdapter(Context context, ArrayList<UnChecked_bean> datas)
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.unselect_item,null);
        UnCheckedViewHolder viewHolder = new UnCheckedViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        UnCheckedViewHolder unCheckedViewHolder = (UnCheckedViewHolder)holder;
        unCheckedViewHolder.unCheckItem.setText(mDatas.get(position).getName());
        unCheckedViewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mItemClickListener != null)
                {
                    String name = mDatas.get(position).getName();
                    mItemClickListener.onItemClick(v,position,name);
                }
            }
        });

        unCheckedViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener()
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

    public static class UnCheckedViewHolder extends RecyclerView.ViewHolder
    {
        private TextView unCheckItem;

        public UnCheckedViewHolder(View itemView)
        {
            super(itemView);
            unCheckItem = (TextView)itemView.findViewById(R.id.unselect_item_id);
        }
    }
}
