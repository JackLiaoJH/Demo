package com.liaojh.floatwindowdemo.other;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author LiaoJH
 * @DATE 15/11/15
 * @VERSION 1.0
 * @DESC TODO
 */
public class ShareloatAdapter extends PagerAdapter {

    private List<View> mViewList;

    public ShareloatAdapter(List<View> views) {
        this.mViewList = views;
    }

    @Override
    public int getCount() {
        return mViewList != null ? mViewList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViewList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
