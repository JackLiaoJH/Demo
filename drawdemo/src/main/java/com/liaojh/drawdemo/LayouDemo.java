package com.liaojh.drawdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author LiaoJH
 * @DATE 15/11/8
 * @VERSION 1.0
 * @DESC TODO 图层演示
 */
public class LayouDemo extends View
{
    public LayouDemo(Context context)
    {
        super(context);
    }

    public LayouDemo(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public LayouDemo(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(150, 150, 100, paint);

        canvas.saveLayerAlpha(0, 0, 400, 400, alpha, Canvas.FULL_COLOR_LAYER_SAVE_FLAG);
        paint.setColor(Color.RED);
        canvas.drawCircle(200,200,100,paint);
        canvas.restore();
    }

    private int alpha = 127;

    public void setAlpha(int alpha) {
        this.alpha = alpha;
        invalidate();
    }
}
