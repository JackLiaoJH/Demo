package com.liaojh.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author LiaoJH
 * @DATE 15/10/13
 * @VERSION 1.0
 * @DESC TODO
 */
public class DemoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<String> mDemoList;

    public DemoAdapter(Context context, List<String> demoList) {

        mContext = context;
        mDemoList = demoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.demo_item_layout, null);
        return new DemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof  DemoViewHolder) {
            DemoViewHolder demoViewHolder = (DemoViewHolder) holder;
            demoViewHolder.tvDemo.setText(mDemoList.get(position));
            demoViewHolder.tvDemo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v,position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDemoList != null ? mDemoList.size() : 0;
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }

    public class DemoViewHolder extends RecyclerView.ViewHolder {
        TextView tvDemo;
        public DemoViewHolder(View itemView) {
            super(itemView);
            tvDemo = (TextView) itemView.findViewById(R.id.tv_demo);
        }
    }
}
