package com.liaojh.drawdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * @author LiaoJH
 * @DATE 15/11/8
 * @VERSION 1.0
 * @DESC TODO
 */
public class YiBiaoPan extends View
{
    private Paint paintCircle;
    private Paint paintDegree;
    private Paint paintHour;
    private Paint paintMinute;
    private Paint paintSecond;

    private int mScreenWidth;
    private int mScreenHeight;

    private int circleRadius;

    private int secondDegree = 0;

    private  final  int degree = circleRadius/30;

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (msg.what == 1)
            {


                invalidate();
                mHandler.sendEmptyMessageDelayed(1, 1000);
            }
        }
    };

    public YiBiaoPan(Context context)
    {
        this(context, null);
    }

    public YiBiaoPan(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public YiBiaoPan(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mScreenHeight = metrics.heightPixels;
        mScreenWidth = metrics.widthPixels;
        circleRadius = mScreenWidth / 2;

        //外边大圆
        paintCircle = new Paint();
        paintCircle.setAntiAlias(true);
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setStrokeWidth(5);

        //画刻度画笔
        paintDegree = new Paint();
        paintDegree.setStrokeWidth(3);

        //
        paintHour = new Paint();
        paintHour.setStrokeWidth(10);

        paintMinute = new Paint();
        paintMinute.setStrokeWidth(7);

        paintSecond = new Paint();
        paintSecond.setStrokeWidth(5);

        mHandler.sendEmptyMessageDelayed(1,1000);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        //画大圆
        canvas.drawCircle(mScreenWidth / 2, mScreenHeight / 2, circleRadius, paintCircle);

        for (int i = 0; i < 24; i++)
        {
            if (0 == i || 6 == i || 12 == i || 18 == i)
            {
                paintDegree.setStrokeWidth(5);
                paintDegree.setTextSize(30);
                canvas.drawLine(
                        mScreenWidth / 2,
                        mScreenHeight / 2 - circleRadius,
                        mScreenWidth / 2,
                        mScreenHeight / 2 - circleRadius + 60,
                        paintDegree
                );

                String value = String.valueOf(i);
                canvas.drawText(
                        value,
                        mScreenWidth / 2 - paintDegree.measureText(value) / 2,
                        mScreenHeight / 2 - circleRadius + 90,
                        paintDegree
                );
            }
            else
            {
                paintDegree.setStrokeWidth(3);
                paintDegree.setTextSize(20);
                canvas.drawLine(
                        mScreenWidth / 2,
                        mScreenHeight / 2 - circleRadius,
                        mScreenWidth / 2,
                        mScreenHeight / 2 - circleRadius + 30,
                        paintDegree
                );

                String value = String.valueOf(i);
                canvas.drawText(
                        value,
                        mScreenWidth / 2 - paintDegree.measureText(value) / 2,
                        mScreenHeight / 2 - circleRadius + 60,
                        paintDegree
                );
            }

            //通过旋转坐标简化坐标计算
            canvas.rotate(15, mScreenWidth / 2, mScreenHeight / 2);
        }


        canvas.save();
        //将画板移到圆心
        canvas.translate(mScreenWidth / 2, mScreenHeight / 2);
        canvas.drawLine(0, 0, 100, circleRadius - 180, paintHour);
        canvas.drawLine(0, 0, 100, circleRadius- 140, paintMinute);
        canvas.drawLine(0, 0, 0, circleRadius-  100, paintSecond);

        canvas.restore();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }
}
