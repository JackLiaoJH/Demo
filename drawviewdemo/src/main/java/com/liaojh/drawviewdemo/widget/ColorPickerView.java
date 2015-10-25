package com.liaojh.drawviewdemo.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ColorPickerView extends View
{

    private Paint                  mPaint;//渐变色环画笔
    private Paint                  mCenterPaint;//中间圆画笔
    private int[]                  mColors;//渐变色环颜色
    private OnColorChangedListener mListener;//颜色改变回调    

    private static final int CENTER_X      = 100;
    private static final int CENTER_Y      = 100;
    private static final int CENTER_RADIUS = 22;

    public ColorPickerView(Context context)
    {
        super(context);
    }

    public ColorPickerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mColors = new int[]{//渐变色数组
                0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00,
                0xFFFFFF00, 0xFFFF0000
        };
        Shader s = new SweepGradient(0, 0, mColors, null);
        //初始化渐变色画笔    
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setShader(s);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(32);

        //初始化中心园画笔    
        mCenterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterPaint.setColor(Color.RED);
        mCenterPaint.setStrokeWidth(15);
    }

    public ColorPickerView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    private boolean mTrackingCenter;
    private boolean mHighlightCenter;

    @Override
    protected void onDraw(Canvas canvas)
    {
        float r = CENTER_X - mPaint.getStrokeWidth() * 0.5f;

        //移动中心    
        canvas.translate(CENTER_X, CENTER_Y);

        //画出色环和中心园    
        canvas.drawOval(new RectF(-r, -r, r, r), mPaint);
        canvas.drawCircle(0, 0, CENTER_RADIUS, mCenterPaint);

        if (mTrackingCenter)
        {
            int c = mCenterPaint.getColor();
            mCenterPaint.setStyle(Paint.Style.STROKE);

            if (mHighlightCenter)
            {
                mCenterPaint.setAlpha(0xFF);
            }
            else
            {
                mCenterPaint.setAlpha(0x80);
            }
            canvas.drawCircle(0, 0,
                    CENTER_RADIUS + mCenterPaint.getStrokeWidth(),
                    mCenterPaint);

            mCenterPaint.setStyle(Paint.Style.FILL);
            mCenterPaint.setColor(c);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension(CENTER_X * 2, CENTER_Y * 2);
    }

    private int ave(int s, int d, float p)
    {
        return s + java.lang.Math.round(p * (d - s));
    }

    private int interpColor(int colors[], float unit)
    {
        if (unit <= 0)
        {
            return colors[0];
        }
        if (unit >= 1)
        {
            return colors[colors.length - 1];
        }

        /**
         * 1,2,3,4,5 
         * 0.6*4=2.4 
         * i=2,p=0.4 
         */
        float p = unit * (colors.length - 1);
        int   i = (int) p;
        p -= i;

        // now p is just the fractional part [0...1) and i is the index    
        int c0 = colors[i];
        int c1 = colors[i + 1];
        int a  = ave(Color.alpha(c0), Color.alpha(c1), p);
        int r  = ave(Color.red(c0), Color.red(c1), p);
        int g  = ave(Color.green(c0), Color.green(c1), p);
        int b  = ave(Color.blue(c0), Color.blue(c1), p);

        return Color.argb(a, r, g, b);
    }

    private static final float PI = 3.1415926f;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float   x        = event.getX() - CENTER_X;
        float   y        = event.getY() - CENTER_Y;
        boolean inCenter = java.lang.Math.sqrt(x * x + y * y) <= CENTER_RADIUS;

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                mTrackingCenter = inCenter;
                if (inCenter)
                {
                    mHighlightCenter = true;
                    invalidate();
                    break;
                }
            case MotionEvent.ACTION_MOVE:
                if (mTrackingCenter)
                {
                    if (mHighlightCenter != inCenter)
                    {
                        mHighlightCenter = inCenter;
                        invalidate();
                    }
                }
                else
                {
                    float angle = (float) java.lang.Math.atan2(y, x);
                    // need to turn angle [-PI ... PI] into unit [0....1]    
                    float unit = angle / (2 * PI);
                    if (unit < 0)
                    {
                        unit += 1;
                    }
                    mCenterPaint.setColor(interpColor(mColors, unit));
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mTrackingCenter)
                {
                    if (inCenter)
                    {
                        if (mListener != null)
                        {
                            mListener.colorChanged(mCenterPaint.getColor());
                        }
                    }
                    mTrackingCenter = false;
                    invalidate();
                }
                break;
        }
        return true;
    }

    public interface OnColorChangedListener
    {
        public void colorChanged(int color);
    }
}
