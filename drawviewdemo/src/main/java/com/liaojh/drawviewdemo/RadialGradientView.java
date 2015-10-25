package com.liaojh.drawviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class RadialGradientView extends View
{

    Paint  mPaint          = null;
    // 环形渐变渲染  
    Shader mRadialGradient = null;

    public RadialGradientView(Context context)
    {
        super(context);
        //1.圆心X坐标2.Y坐标3.半径 4.颜色数组 5.相对位置数组，可为null 6.渲染器平铺模式   
        mRadialGradient = new RadialGradient(100, 100, 100, new int[]{
                Color.YELLOW, Color.GREEN, Color.TRANSPARENT, Color.RED}, null,
                Shader.TileMode.REPEAT);

        mPaint = new Paint();
    }

    public RadialGradientView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        // 绘制环形渐变
        mPaint.setShader(mRadialGradient);
        // 第一个,第二个参数表示圆心坐标
        // 第三个参数表示半径
        canvas.drawCircle(100, 100, 100, mPaint);
    }
}  