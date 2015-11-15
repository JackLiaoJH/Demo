package com.liaojh.floatwindowdemo.other;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.liaojh.floatwindowdemo.R;
import com.nineoldandroids.view.ViewHelper;

import java.util.Collections;
import java.util.List;

/**
 * @author LiaoJH
 * @DATE 15/11/15
 * @VERSION 1.0
 * @DESC TODO
 */
public class ShareFloatTabView extends FrameLayout implements ViewPager.OnPageChangeListener {
    private Context mContext;
    private HorizontalScrollView hsv;
    private View mTabLine;
    private RelativeLayout mRlShareFloatTabContainer;

    private int mCurrentPosition;
    private float mTabLineWidth;

    private int mItemTabWidth, mItemTabHeight;
    private float mItemTabLeftMargin, mLLContentLeftMargin;

    public OnTabItemClickListener listener;

    private List<View> mTabViews = Collections.emptyList();

    public ShareFloatTabView(Context context) {
        this(context, null);
    }

    public ShareFloatTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShareFloatTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        mTabLineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50 + 15, getResources().getDisplayMetrics());
        mLLContentLeftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        mItemTabLeftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());

        mItemTabWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        mItemTabHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

        LayoutInflater.from(mContext).inflate(R.layout.tab_layout, this);
        hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        mTabLine = findViewById(R.id.tab_line);
        mRlShareFloatTabContainer = (RelativeLayout) findViewById(R.id.ll_content);
    }

    public void setTabViews(final List<View> views) {
        this.mTabViews = views;
        int[] itemUrl = {R.mipmap.ic_launcher, R.mipmap.app_logo,
                R.mipmap.spliding_close, R.mipmap.spliding_open,
                R.mipmap.tab_icon_1, R.mipmap.tab_icon_2,
                R.mipmap.tab_icon_2, R.mipmap.tab_icon_1
        };
        for (int i = 0; i < views.size(); i++) {
            ImageView itemImageView = new ImageView(mContext);
            itemImageView.setImageResource(itemUrl[i]);
//            itemImageView.setBackgroundColor(Color.RED);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mItemTabWidth, mItemTabHeight);
            lp.leftMargin = (int) ((mItemTabLeftMargin + mItemTabWidth) * i + mLLContentLeftMargin);
            itemImageView.setLayoutParams(lp);
            mRlShareFloatTabContainer.addView(itemImageView, i, lp);

            final int finalI = i;
            itemImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onTabClick(v, finalI);
                    }
                }
            });

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if ((mCurrentPosition == 0 && position == 0) //0--->1
                || (mCurrentPosition == 1 && position == 1) //1--->2
                || (mCurrentPosition == 2 && position == 2) //2--->3
                || (mCurrentPosition == 3 && position == 3) //3--->4
                || (mCurrentPosition == 4 && position == 4)
                || (mCurrentPosition == 5 && position == 5)
                || (mCurrentPosition == 6 && position == 6)
                || (mCurrentPosition == 7 && position == 7)
                ) {
            ViewHelper.setTranslationX(mTabLine, mCurrentPosition * mTabLineWidth + mTabLineWidth * positionOffset);
            hsv.smoothScrollTo((int) (mCurrentPosition * mTabLineWidth + mTabLineWidth * positionOffset), 0);
        } else if ((mCurrentPosition == 1 && position == 0)// 1--->0
                || (mCurrentPosition == 2 && position == 1) // 2--->1
                || (mCurrentPosition == 3 && position == 2) // 3--->2
                || (mCurrentPosition == 4 && position == 3) // 4--->3
                || (mCurrentPosition == 5 && position == 4)
                || (mCurrentPosition == 6 && position == 5)
                || (mCurrentPosition == 7 && position == 6)
                || (mCurrentPosition == 8 && position == 7)
                ) {
            ViewHelper.setTranslationX(mTabLine, mCurrentPosition * mTabLineWidth + mTabLineWidth * (positionOffset - 1));
            hsv.smoothScrollTo((int) (mCurrentPosition * mTabLineWidth + mTabLineWidth * (positionOffset - 1)), 0);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setOnTabClickListener(OnTabItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnTabItemClickListener {
        void onTabClick(View view, int position);
    }
}
