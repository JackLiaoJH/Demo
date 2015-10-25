package com.liaojh.drawviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.graphics.Shader;
import android.view.View;

public class LinearGradientView extends View
{

    private LinearGradient linearGradient = null;
    private Paint          paint          = null;

    public LinearGradientView(Context context)
    {
        super(context);
        linearGradient = new LinearGradient(0, 0, 100, 100, new int[]{
                Color.YELLOW, Color.GREEN, Color.TRANSPARENT, Color.WHITE}, null,
                Shader.TileMode.REPEAT);
        paint = new Paint();
    }

    public LinearGradientView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        // TODO Auto-generated method stub  
        super.onDraw(canvas);
        //设置渲染器  
        paint.setShader(linearGradient);
        //绘制圆环
        canvas.drawCircle(100, 100, 100, paint);
    }

}  