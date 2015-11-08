package com.liaojh.scrolldemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener
{
    private static final String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView) findViewById(R.id.tv);

        tv.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener()
                {
                    @Override
                    public void onGlobalLayout()
                    {
                        Log.i(TAG, tv.getLeft() + "," + tv.getRight() + "," + tv.getTop() + "," + tv
                                .getBottom());
                        Log.i(TAG, tv.getWidth() + "," + tv.getHeight());
                        tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });

        findViewById(R.id.iv).setOnTouchListener(this);
        tv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MainActivity.this.startActivity(new Intent(MainActivity.this,DragDemo.class));
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //获取当前输入点的坐标,(视图坐标)
        float x = event.getX();
        float y = event.getY();

//        Log.i(TAG, x + "," + y);

        float rawX = event.getRawX();
        float rawY = event.getRawY();
//        Log.i(TAG, rawX + "," + rawY);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                //处理输入按下事件
                break;
            case MotionEvent.ACTION_MOVE:
                //处理输入的移动事件
                break;
            case MotionEvent.ACTION_UP:
                //处理输入的离开事件
                break;
        }
        return true; //注意,这里必须返回true,否则只能响应按下事件
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        //获取当前输入点的坐标,(视图坐标)
        float x = event.getX();
        float y = event.getY();

        Log.i(TAG, x + "," + y);

        float rawX = event.getRawX();
        float rawY = event.getRawY();
        Log.i(TAG, rawX + "," + rawY);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                //处理输入按下事件
                break;
            case MotionEvent.ACTION_MOVE:
                //处理输入的移动事件
                break;
            case MotionEvent.ACTION_UP:
                //处理输入的离开事件
                break;
        }
        return true; //注意,这里必须返回true,否则只能响应按下事件
    }
}
