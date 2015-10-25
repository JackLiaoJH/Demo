package com.liaojh.drawviewdemo.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SpringProgressView extends View
{

    private static final int[] SECTION_COLORS = {0xffffd300, Color.GREEN, 0xff319ed4};
    private float maxCount;
    private float currentCount;
    private Paint mPaint;
    private int   mWidth, mHeight;

    private Bitmap bitMap;

    public SpringProgressView(Context context, AttributeSet attrs,
                              int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public SpringProgressView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView(context);
    }

    public SpringProgressView(Context context)
    {
        super(context);
        initView(context);
    }

    private void initView(Context context)
    {
        /*bitMap = BitmapFactory.decodeResource(context.getResources(), 
                R.drawable.scrubber_control_pressed_holo);*/
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        int round = mHeight / 2;
        mPaint.setColor(Color.GRAY);

        RectF rectBg = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rectBg, round, round, mPaint);
        mPaint.setColor(Color.WHITE);
        RectF rectBlackBg = new RectF(2, 2, mWidth - 2, mHeight - 2);
        canvas.drawRoundRect(rectBlackBg, round, round, mPaint);

        float section        = currentCount / maxCount;
        RectF rectProgressBg = new RectF(3, 3, (mWidth - 3) * section, mHeight - 3);
        if (section <= 1.0f / 3.0f)
        {
            if (section != 0.0f)
            {
                mPaint.setColor(SECTION_COLORS[0]);
            }
            else
            {
                mPaint.setColor(Color.TRANSPARENT);
            }
        }
        else
        {
            int count = (section <= 1.0f / 3.0f * 2) ? 2 : 3;
            int[] colors = new int[count];
            System.arraycopy(SECTION_COLORS, 0, colors, 0, count);
            float[] positions = new float[count];
            if (count == 2)
            {
                positions[0] = 0.0f;
                positions[1] = 1.0f - positions[0];
            }
            else
            {
                positions[0] = 0.0f;
                positions[1] = (maxCount / 3) / currentCount;
                positions[2] = 1.0f - positions[0] * 2;
            }
            positions[positions.length - 1] = 1.0f;
            LinearGradient shader = new LinearGradient(3, 3, (mWidth - 3) * section, mHeight - 3,
                    colors, null, Shader.TileMode.MIRROR);
            mPaint.setShader(shader);
        }
        canvas.drawRoundRect(rectProgressBg, round, round, mPaint);
        //canvas.drawBitmap(bitMap, rectProgressBg.right-20, rectProgressBg.top-4, null);  
    }

    private int dipToPx(int dip)
    {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    public void setMaxCount(float maxCount)
    {
        this.maxCount = maxCount;
    }

    public void setCurrentCount(float currentCount)
    {
        this.currentCount = currentCount > maxCount ? maxCount : currentCount;
        invalidate();
    }

    public float getMaxCount()
    {
        return maxCount;
    }

    public float getCurrentCount()
    {
        return currentCount;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthSpecMode  = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize  = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST)
        {
            mWidth = widthSpecSize;
        }
        else
        {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED)
        {
            mHeight = dipToPx(15);
        }
        else
        {
            mHeight = heightSpecSize;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();
        Log.i("DEMO", "x:" + x + ",y:" + y);
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                moved(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                moved(x, y);
                break;
            case MotionEvent.ACTION_UP:
                moved(x, y);
                break;
        }
        return true;
    }

    private void moved(float x, float y)
    {
        if (x > mWidth)
        {
            return;
        }
        currentCount = maxCount * (x / mWidth);
        invalidate();
    }


}  