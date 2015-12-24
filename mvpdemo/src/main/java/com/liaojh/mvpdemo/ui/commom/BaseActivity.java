package com.liaojh.mvpdemo.ui.commom;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author LiaoJH
 * @DATE 15/12/20
 * @VERSION 1.0
 * @DESC TODO
 */
public class BaseActivity extends AppCompatActivity {
    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }
}
