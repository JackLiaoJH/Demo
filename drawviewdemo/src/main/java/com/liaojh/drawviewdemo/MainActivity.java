package com.liaojh.drawviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liaojh.drawviewdemo.widget.SpringProgressView;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setContentView(new LinearGradientView(this));
//        setContentView(new RadialGradientView(this));
//        setContentView(new WaterRipplesView(this));
//        setContentView(new SweepGradientView(this));


        SpringProgressView view = (SpringProgressView)findViewById(R.id.view);
        view.setMaxCount(100);
        view.setCurrentCount(30);
    }
}
