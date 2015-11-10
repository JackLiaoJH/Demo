package com.liaojh.floatwindowdemo.other;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liaojh.floatwindowdemo.R;

public class FloatDeleteView extends LinearLayout
{
    /**
     * 记录删除悬浮窗的宽度
     */
    public static int viewWidth;
    /**
     * 记录删除悬浮窗的高度
     */
    public static int viewHeight;

    private TextView textView;

    public FloatDeleteView(Context context)
    {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.float_delete_layout, this);
        textView = (TextView) findViewById(R.id.delete_textview);
        viewWidth = textView.getLayoutParams().width;
        viewHeight = textView.getLayoutParams().height;


    }

    public TextView getTextView()
    {
        return textView;
    }
}
