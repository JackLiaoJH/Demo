package com.liaojh.loadingdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.liaojh.loadingdemo.widget.ProgressRoundView;

import java.lang.ref.WeakReference;


public class MainActivity extends AppCompatActivity {
    private ProgressRoundView progressRoundView;

    private Handler mHandler = new MyHandler(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressRoundView = (ProgressRoundView) findViewById(R.id.prv);

        progressRoundView.setProgress(0);
        progressRoundView.setMaxProgress(15);
        mHandler.sendEmptyMessage(1);

    }

    private int progress = 0;
    private static class MyHandler extends Handler {
        private WeakReference<Activity> reference;

        public MyHandler(Activity activity) {
            reference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Activity activity = reference.get();

            if (activity != null && activity instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) activity;

                switch (msg.what) {
                    case 1:
//                        mainActivity.progressRoundView.increateProgress();
                        mainActivity.progressRoundView.setProgress(mainActivity.progress ++);
                        mainActivity.mHandler.sendEmptyMessageDelayed(1, 1000);
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}

