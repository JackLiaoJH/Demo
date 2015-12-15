package com.liaojh.viewdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author LiaoJH
 * @DATE 15/12/15
 * @VERSION 1.0
 * @DESC TODO
 */
public class ViewDemoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_list_layout);

    }

    public void drawView(View view) {
        startActivity(new Intent(this,CircleViewDemo.class));
    }
}
