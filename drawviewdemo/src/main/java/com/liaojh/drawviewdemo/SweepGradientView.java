package com.liaojh.drawviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;


public class SweepGradientView extends View
{

    Paint  mPaint         = null;
    // 梯度渲染      
    Shader mSweepGradient = null;

    public SweepGradientView(Context context)
    {
        super(context);

        // 创建SweepGradient对象
        // 第一个,第二个参数中心坐标      
        // 后面的参数与线性渲染相同      
        mSweepGradient = new SweepGradient(100, 200,
                new int[]{Color.CYAN, Color.DKGRAY, Color.GRAY, Color.LTGRAY, Color.MAGENTA,
                        Color.GREEN, Color.TRANSPARENT, Color.BLUE}, null);
        mPaint = new Paint();
    }


    public SweepGradientView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        // 绘制梯度渐变
        mPaint.setShader(mSweepGradient);

        canvas.drawCircle(100, 200, 200, mPaint);
    }
}  