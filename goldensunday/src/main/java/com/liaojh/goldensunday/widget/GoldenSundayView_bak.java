package com.liaojh.goldensunday.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.liaojh.goldensunday.R;

/**
 * @author LiaoJH
 * @DATE 15/10/24
 * @VERSION 1.0
 * @DESC TODO
 */
public class GoldenSundayView_bak extends View
{
    private String mNormalText;
    private int    mNormalTextColor;
    private int    mNormalTextSize;

    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;

    private Rect mTimeBound;

    private Paint mPaint;
    /**
     * 时间文字画笔
     */
    private Paint mTimePaint;

    private Paint mBgPaint;

    private int mNormalDisTimeText = 8;

    String t1 = "1";
    String t2 = "2";

    private Bitmap mBackGround;
    private Bitmap mBitmapCancel;

    /**
     * 时间之间的距离
     */
    private float mTimeTextDis = 16;

    public GoldenSundayView_bak(Context context)
    {
        this(context, null);
    }

    public GoldenSundayView_bak(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public GoldenSundayView_bak(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getResources()
                                       .obtainAttributes(attrs, R.styleable.GoldenSunday);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++)
        {
            int id = typedArray.getIndex(i);
            switch (id)
            {
                case R.styleable.GoldenSunday_normalText:
                    mNormalText = typedArray.getString(id);
                    break;
                case R.styleable.GoldenSunday_normalTextColor:
                    mNormalTextColor = typedArray.getColor(id, Color.BLACK);
                    break;
                case R.styleable.GoldenSunday_normalTextSize:
                    // 默认设置为12sp，TypeValue也可以把sp转化为px
                    mNormalTextSize = typedArray.getDimensionPixelSize(id, (int) TypedValue
                            .applyDimension(
                                    TypedValue.COMPLEX_UNIT_SP, 12,
                                    getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();
        init();
    }

    private void init()
    {
        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setTextSize(mNormalTextSize);
        // mPaint.setColor(mNormalTextColor);
        mBound = new Rect();
        mPaint.getTextBounds(mNormalText, 0, mNormalText.length(), mBound);

        mTimePaint = new Paint();
        mTimePaint.setTextSize(25);
        mTimePaint.setColor(Color.RED);
        //设置成粗体
        mTimePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        mTimeBound = new Rect();
        mTimePaint.getTextBounds(t1, 0, t1.length(), mTimeBound);

        mBackGround = ((BitmapDrawable) this.getResources().getDrawable(R.mipmap.bg)).getBitmap();
        mBgPaint = new Paint();


        BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(R.mipmap.delete_selector);
        mBitmapCancel = bd.getBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);


        mPaint.setColor(mNormalTextColor);
        mPaint.setTextSize(12);
        float textHeight = getHeight() / 2.0f + mBound.height() / 2.0f;
        canvas.drawText(mNormalText, 0, textHeight, mPaint);
        int normalTextWidth = mBound.width();



        float hour1X = normalTextWidth + mTimeBound.width() + mNormalDisTimeText;
        float hour1Y = textHeight;
        canvas.drawText(t1, hour1X, hour1Y, mTimePaint);
        float hour2X = hour1X + mTimeTextDis;
        float hour2Y = hour1Y;
        canvas.drawText(t2, hour2X, hour2Y, mTimePaint);

//        mBgPaint.setAntiAlias(true);
//        canvas.drawBitmap(mBackGround, normalTextWidth + mTimeBound.width() + mNormalDisTimeText, 0,
//                mBgPaint);

        //删除按钮
        int   left = getWidth() - getPaddingRight() - mBitmapCancel.getWidth() - mNormalDisTimeText;
        float top  = getHeight() / 2 - mBitmapCancel.getHeight() / 2;
        canvas.drawBitmap(mBitmapCancel, left, top, mPaint);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode  = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize  = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //match_parent
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize; //默认屏幕宽
        }
        else //wrap_content或自己定义
        {
            mPaint.setTextSize(mNormalTextSize);
            mPaint.getTextBounds(mNormalText, 0, mNormalText.length(), mBound);
            float textWidth = mBound.width();
            //获取宽
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        }
        else
        {
            mPaint.setTextSize(mNormalTextSize);
            mPaint.getTextBounds(mNormalText, 0, mNormalText.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }


        setMeasuredDimension(width, height);
    }
}
