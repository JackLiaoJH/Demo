package com.liaojh.drawviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.liaojh.drawviewdemo.widget.ProgressCircleView;


/**
 * @author LiaoJH
 * @DATE 15/11/22
 * @VERSION 1.0
 * @DESC TODO
 */
public class SecondActivity extends Activity {
    private ProgressCircleView progressCircleView;
    private int size = 14;
    private int mCurrentSize;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                progressCircleView.setSize(++mCurrentSize);
                mHandler.sendEmptyMessageDelayed(1, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layut);
        progressCircleView = (ProgressCircleView) findViewById(R.id.pcv);

        progressCircleView.setMaxSize(size);
        progressCircleView.setSize(mCurrentSize);
        mHandler.sendEmptyMessageDelayed(1, 1000);

    }
}
