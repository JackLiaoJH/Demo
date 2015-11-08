package com.liaojh.baserecycleviewdemo;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * @author LiaoJH
 * @DATE 15/11/8
 * @VERSION 1.0
 * @DESC TODO 具有下拉刷新的RecyclerView
 */
public class PullRefreshRecyclerView extends RecyclerView
{
    private OnRefreshListener   mRefreshListener;
    private OnLoadMoreListener  mLoadMoreListener;
    private BaseRecyclerAdapter mAdapter;

    private int mLastPosition;
    private int mFirstPosition;

    private boolean mIsRefresh;
    private boolean mIsLoadMore;

    private View mHeaderView;
    private View mFooterView;

    private LayoutInflater mLayoutInflater;

    public PullRefreshRecyclerView(Context context)
    {
        this(context, null);
    }

    public PullRefreshRecyclerView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public PullRefreshRecyclerView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context)
    {
        addOnScrollListener(new RecyclerViewOnScrollListener());
        mLayoutInflater = LayoutInflater.from(context);
    }


    public void setLoadMoreListener(
            OnLoadMoreListener loadMoreListener)
    {
        this.mLoadMoreListener = loadMoreListener;
    }

    public void setRefreshListener(
            OnRefreshListener refreshListener)
    {
        this.mRefreshListener = refreshListener;
    }

    public static interface OnRefreshListener
    {
        void onRefresh();
    }

    public static interface OnLoadMoreListener
    {
        void onLoadMore();
    }

    public void setLoadMoreComplement(boolean isLoadMoreComplement)
    {
        if (mAdapter != null)
        {
            mIsLoadMore = false;
            mAdapter.setLoadMoreComplement(isLoadMoreComplement);
        }
    }

    public void setRefreshComplement(boolean isRefreshComplement)
    {
        if (mAdapter != null)
        {
            mIsRefresh = false;
            mAdapter.setRefreshComplement(isRefreshComplement);
        }
    }

    private final class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener
    {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState)
        {
            super.onScrollStateChanged(recyclerView, newState);
//            Log.i("recycler-", "newState:" + newState + "----:" + recyclerView.getScrollY());

            if (recyclerView.SCROLL_STATE_IDLE == newState &&
                    recyclerView.getLayoutManager().getItemCount() - 1 == mLastPosition
                    )
            {
//                Log.i("recycler-", "最后一个,显示加载更多");
                if (mAdapter != null)
                {
                    if (!mIsLoadMore)
                    {
                        mIsLoadMore = true;
                        mAdapter.setLoadMoreComplement(false);
                        if (mLoadMoreListener != null)
                        {
                            mLoadMoreListener.onLoadMore();
                        }
                    }
                }
            }
            else if (recyclerView.SCROLL_STATE_IDLE == newState && mFirstPosition == 0)
            {
//                Log.i("recycler-", "第一个一个,显示下拉刷新");

                if (mAdapter != null)
                {
                    if (!mIsRefresh)
                    {
                        mIsRefresh = true;
                        mAdapter.setRefreshComplement(false);
                        if (mRefreshListener != null)
                        {
                            mRefreshListener.onRefresh();
                        }
                    }
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy)
        {
            super.onScrolled(recyclerView, dx, dy);
//            Log.i("recycler-", "dy:" + dy);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager)
            {
                mLastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                mFirstPosition = ((LinearLayoutManager) layoutManager)
                        .findFirstVisibleItemPosition();
            }
            else if (layoutManager instanceof GridLayoutManager)
            {
                mLastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                mFirstPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
            }
            else if (layoutManager instanceof StaggeredGridLayoutManager)
            {
                int[] positions = ((StaggeredGridLayoutManager) layoutManager)
                        .findLastVisibleItemPositions(null);

                for (int i = 0; i < positions.length; i++)
                {
                    Log.i("recycler-", ":" + positions[i]);
                }

                mLastPosition = ((StaggeredGridLayoutManager) layoutManager)
                        .findLastVisibleItemPositions(null)[2];
                mFirstPosition = ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(null)[0];
            }
        }
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        //默认加载的布局
        mHeaderView = mLayoutInflater.inflate(R.layout.header, null);
        MarginLayoutParams lp = new MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20,
                getContext().getResources().getDisplayMetrics());
        lp.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
                getContext().getResources().getDisplayMetrics());
        mHeaderView.setLayoutParams(lp);
        mFooterView = mLayoutInflater.inflate(R.layout.footer, null);
    }

    @Override
    public void setAdapter(Adapter adapter)
    {
        super.setAdapter(adapter);
        if (adapter != null && adapter instanceof BaseRecyclerAdapter)
        {
            BaseRecyclerAdapter recyclerAdapter = (BaseRecyclerAdapter) adapter;
            this.mAdapter = recyclerAdapter;
            recyclerAdapter.addHeaderView(mHeaderView);
            recyclerAdapter.addFooterView(mFooterView);
        }
    }

    public void addHeaderView(View headerView)
    {
        if (headerView != null)
        {
            this.mHeaderView = headerView;
            if (mAdapter != null)
            {
                mAdapter.addHeaderView(headerView);
            }
        }
    }

    public void addFooterView(View footerView)
    {
        if (footerView != null)
        {
            this.mFooterView = footerView;
            if (mAdapter != null)
            {
                mAdapter.addFooterView(footerView);
            }
        }
    }
}
