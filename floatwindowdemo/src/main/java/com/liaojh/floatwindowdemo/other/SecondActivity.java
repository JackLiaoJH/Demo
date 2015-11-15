package com.liaojh.floatwindowdemo.other;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaojh.floatwindowdemo.R;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;


public class SecondActivity extends Activity implements View.OnClickListener {

    //    private ImageView ivHistory, ivDownload, ivTop, ivNew;
    private ViewPager viewPager;


    private List<View> mViewList, mNewViewList;
    private ShareloatAdapter adapter;


    private int mTabItemCount;


    private ShareFloatTabView mShareFloatTabView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);


        initView();
        initData();
        mTabItemCount = mViewList.size();

        setListener();
    }

    private void initData() {
        mViewList = new ArrayList<>();

        TextView textView = new TextView(this);
        textView.setText("History");
        textView.setTextSize(40);
        textView.setTextColor(Color.BLUE);
        mViewList.add(textView);

        textView = new TextView(this);
        textView.setText("Download");
        textView.setTextSize(40);
        textView.setTextColor(Color.BLUE);
        mViewList.add(textView);

        textView = new TextView(this);
        textView.setText("Top");
        textView.setTextSize(40);
        textView.setTextColor(Color.BLUE);
        mViewList.add(textView);

        textView = new TextView(this);
        textView.setText("New");
        textView.setTextSize(40);
        textView.setTextColor(Color.BLUE);
        mViewList.add(textView);

        textView = new TextView(this);
        textView.setText("Recommend1");
        textView.setTextSize(40);
        textView.setTextColor(Color.BLUE);
        mViewList.add(textView);

        textView = new TextView(this);
        textView.setText("Recommend2");
        textView.setTextSize(40);
        textView.setTextColor(Color.BLUE);
        mViewList.add(textView);

        textView = new TextView(this);
        textView.setText("Recommend3");
        textView.setTextSize(40);
        textView.setTextColor(Color.BLUE);
        mViewList.add(textView);

        textView = new TextView(this);
        textView.setText("Recommend4");
        textView.setTextSize(40);
        textView.setTextColor(Color.BLUE);
        mViewList.add(textView);

//        Collections.copy();

        int count = 3;


        int[] imageId = {R.mipmap.ic_launcher, R.mipmap.app_logo, R.mipmap.spliding_open};

        mNewViewList = new ArrayList<>();

        for (int i = 0; i < mViewList.size(); i++) {
            if (4 <= i && i < 4 + count) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(imageId[i - 4]);
                mNewViewList.add(imageView);
            } else {
                mNewViewList.add(mViewList.get(i));
            }
        }


    }

    private void initView() {
//
//        ivHistory = (ImageView) findViewById(R.id.iv_history);
//        ivDownload = (ImageView) findViewById(R.id.iv_download);
//        ivTop = (ImageView) findViewById(R.id.iv_top);
//        ivNew = (ImageView) findViewById(R.id.iv_new);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mShareFloatTabView = (ShareFloatTabView) findViewById(R.id.share_float_tab_view);


    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setListener() {
//        ivHistory.setOnClickListener(this);
//        ivDownload.setOnClickListener(this);
//        ivTop.setOnClickListener(this);
//        ivNew.setOnClickListener(this);
//        ivRecommend1.setOnClickListener(this);
//        ivRecommend2.setOnClickListener(this);
//        ivRecommend3.setOnClickListener(this);
//        ivRecommend4.setOnClickListener(this);

        viewPager.addOnPageChangeListener(mShareFloatTabView);


        adapter = new ShareloatAdapter(mNewViewList);
        viewPager.setAdapter(adapter);



        mShareFloatTabView.setTabViews(mViewList);
        mShareFloatTabView.setOnTabClickListener(new ShareFloatTabView.OnTabItemClickListener() {
            @Override
            public void onTabClick(View view, int position) {
                viewPager.setCurrentItem(position);
//                switch (position) {
//                    case 0:
//                        viewPager.setCurrentItem(0);
//                        break;
//                    case 1:
//                        viewPager.setCurrentItem(1);
//                        break;
//                    case 2:
//                        viewPager.setCurrentItem(2);
//                        break;
//                    case 3:
//                        viewPager.setCurrentItem(3);
//                        break;
////            case 4:
////                viewPager.setCurrentItem(4);
////                break;
////            case 5:
////                viewPager.setCurrentItem(5);
////                break;
////            case R.id.iv_recommend3:
////                viewPager.setCurrentItem(6);
////                break;
////            case R.id.iv_recommend4:
////                viewPager.setCurrentItem(7);
////                break;
////                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.iv_history:
//                viewPager.setCurrentItem(0);
//                break;
//            case R.id.iv_download:
//                viewPager.setCurrentItem(1);
//                break;
//            case R.id.iv_top:
//                viewPager.setCurrentItem(2);
//                break;
//            case R.id.iv_new:
//                viewPager.setCurrentItem(3);
//                break;
//            case R.id.iv_recommend1:
//                viewPager.setCurrentItem(4);
//                break;
//            case R.id.iv_recommend2:
//                viewPager.setCurrentItem(5);
//                break;
//            case R.id.iv_recommend3:
//                viewPager.setCurrentItem(6);
//                break;
//            case R.id.iv_recommend4:
//                viewPager.setCurrentItem(7);
//                break;
        }
    }

}
