package com.liaojh.mvpdemo.app;

import android.app.Application;
import android.util.Log;

/**
 * @author LiaoJH
 * @DATE 15/12/20
 * @VERSION 1.0
 * @DESC TODO
 */
public class MvpDemoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MvpDemoApp","MvpDemoApp onCreate------ ");
    }
}
