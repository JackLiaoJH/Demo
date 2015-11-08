package com.liaojh.baserecycleviewdemo;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author LiaoJH
 * @DATE 15/11/8
 * @VERSION 1.0
 * @DESC TODO
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    protected List<T>               mDataList;
    protected LayoutInflater        mLayoutInflater;
    private   OnItemOnClickListener mListener;


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;

    private View mHeaderView;
    private View mFooterView;

    private boolean mIsHeaderShow = false;
    private boolean mIsFooterShow = false;

    public BaseRecyclerAdapter(Context context, List<T> dataList)
    {
        mLayoutInflater = LayoutInflater.from(context);
        this.mDataList = dataList;
    }


    public void addHeaderView(View headerView)
    {
        if (headerView != null)
        {
            this.mHeaderView = headerView;
            notifyItemChanged(0);
        }
    }

    public void addFooterView(View footerView)
    {
        if (footerView != null)
        {
            this.mFooterView = footerView;
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public void addItem(T data, int position)
    {
        mDataList.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(int position)
    {
        if (position < 0 || position >= mDataList.size())
        {
            return;
        }
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    public void addDatas(List<T> datas)
    {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == TYPE_HEADER)
        {
            return new BaseViewHolder(mHeaderView);
        }
        else if (viewType == TYPE_FOOTER)
        {
            return new BaseViewHolder(mFooterView);
        }
        else
        {
            return onCreateView(parent, viewType);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER)
        {
            return;
        }
        if (mIsHeaderShow && mHeaderView != null)
        {
            position -= 1;
        }
        onBindView(holder, position);

    }

    protected abstract RecyclerView.ViewHolder onCreateView(ViewGroup parent, int viewType);

    protected abstract void onBindView(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount()
    {
        int size           = mDataList != null ? mDataList.size() : 0;
        int hasHeaderCount = (mIsHeaderShow && mHeaderView != null) ? size + 1 : size;
        return (mIsFooterShow && mFooterView != null) ? hasHeaderCount + 1 : hasHeaderCount;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (mHeaderView != null && position == 0 && mIsHeaderShow)
        {
            return TYPE_HEADER;
        }
        else if (mFooterView != null && position == getItemCount() - 1 && mIsFooterShow)
        {
            return TYPE_FOOTER;
        }
        else
        {
            return TYPE_NORMAL;
        }

    }

    public void setRefreshComplement(boolean isRefresh)
    {
        mIsHeaderShow = !isRefresh;
        notifyDataSetChanged();
    }

    public void setLoadMoreComplement(boolean isLoadMore)
    {
        mIsFooterShow = !isLoadMore;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
        //头,尾占一行
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager)
        {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
            {
                @Override
                public int getSpanSize(int position)
                {
                    return getItemViewType(position) == TYPE_HEADER || getItemViewType(
                            position) == TYPE_FOOTER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder)
    {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && ((holder.getLayoutPosition() == 0 && mIsHeaderShow) ||
                (holder.getLayoutPosition() == getItemCount() - 1 && mIsFooterShow)))
        {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }


    public void setItemOnClickListener(OnItemOnClickListener listener)
    {
        this.mListener = listener;
    }


    public static interface OnItemOnClickListener
    {
        void onItemOnClick(View view, int position);
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder
    {

        public BaseViewHolder(View itemView)
        {
            super(itemView);
            if (itemView == mHeaderView || itemView == mFooterView) return;
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (mListener != null)
                    {
                        mListener.onItemOnClick(v,
                                mIsHeaderShow ? getLayoutPosition() - 1 : getLayoutPosition());
                    }
                }
            });

        }
    }
}
