package com.liaojh.baserecycleviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author LiaoJH
 * @DATE 15/11/8
 * @VERSION 1.0
 * @DESC TODO
 */
public class MyAdapter extends BaseRecyclerAdapter<String>
{

    public MyAdapter(Context context, List<String> dataList)
    {
        super(context, dataList);
    }


    @Override
    protected RecyclerView.ViewHolder onCreateView(ViewGroup parent, int viewType)
    {
        View view = mLayoutInflater.inflate(R.layout.item_layout, parent, false);
        return new MyAdapterHolder(view);
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof MyAdapterHolder)
        {
            MyAdapterHolder myHolder = (MyAdapterHolder) holder;
            myHolder.tv.setText(mDataList.get(position));
        }
    }

    public final class MyAdapterHolder extends BaseViewHolder
    {
        public TextView tv;

        public MyAdapterHolder(View itemView)
        {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }


}
