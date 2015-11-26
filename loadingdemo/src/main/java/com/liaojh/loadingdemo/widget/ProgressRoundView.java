package com.liaojh.loadingdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author LiaoJH
 * @DATE 15/11/25
 * @VERSION 1.0
 * @DESC TODO
 */
public class ProgressRoundView extends View {
    private Paint mOutCirclePaint;
    private Paint mInCirclePaint;
    private Paint mArcPaint;
    private Paint mTextPaint;

    private RectF mArcRectF;
    private Rect mTextRect;

    private float mCircleX = 200, mCircleY = 200;
    private float mInCircleRadius = 70;
    private float mOutCircleRadius = 100;

    private float mArcLeft, mArcTop, mArcRight, mArcBottom;

    private float mStartAngle = 0, mSweepAngle = 0;

    private String mProgressText = "0%";
    private int mMaxProgress = 360;

    public ProgressRoundView(Context context) {
        this(context, null);
    }

    public ProgressRoundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressRoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mOutCirclePaint = new Paint();
        mOutCirclePaint.setAntiAlias(true);
        mOutCirclePaint.setStyle(Paint.Style.STROKE);
        mOutCirclePaint.setColor(Color.GRAY);

        mInCirclePaint = new Paint();
        mInCirclePaint.setAntiAlias(true);
        mInCirclePaint.setStyle(Paint.Style.STROKE);
        mInCirclePaint.setColor(Color.GRAY);

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mOutCircleRadius - mInCircleRadius);
        mArcPaint.setColor(Color.GREEN);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(25);

        mTextRect = new Rect();
        mTextPaint.getTextBounds(mProgressText, 0, mProgressText.length(), mTextRect);

        float ds = (mOutCircleRadius - mInCircleRadius) / 2;
        mArcLeft = mCircleX - mOutCircleRadius + ds;
        mArcTop = mCircleY - mOutCircleRadius + ds;
        mArcRight = mCircleX + mOutCircleRadius - ds;
        mArcBottom = mCircleY + mOutCircleRadius - ds;
        mArcRectF = new RectF(mArcLeft, mArcTop, mArcRight, mArcBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mCircleX, mCircleY, mOutCircleRadius, mOutCirclePaint);
        canvas.drawArc(mArcRectF, mStartAngle, mSweepAngle, false, mArcPaint);
        canvas.drawCircle(mCircleX, mCircleY, mInCircleRadius, mInCirclePaint);
        canvas.drawText(mProgressText, mCircleX - (mTextRect.right - mTextRect.left) / 2, mCircleY, mTextPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else {
            width = getWidth() + getPaddingLeft() + getPaddingRight();
            if (mode == MeasureSpec.AT_MOST) {
                width = Math.min(width,size);
            }
        }

         mode = MeasureSpec.getMode(heightMeasureSpec);
         size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else {
            height = getHeight() + getPaddingTop() + getPaddingBottom();
            if (mode == MeasureSpec.AT_MOST) {
                height = Math.min(height,size);
            }
        }

        setMeasuredDimension(width,height);
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void increateProgress() {
        mSweepAngle++;
        if (mSweepAngle <= 360) {
            mProgressText = (int) (mSweepAngle / 360 * 100) + "%";
            mTextPaint.getTextBounds(mProgressText, 0, mProgressText.length(), mTextRect);
            invalidate();
        }
    }

    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
    }


    public void setProgress(float progress) {
        this.mSweepAngle = progress * (360 / mMaxProgress) + 360 % mMaxProgress;
        if (progress <= mMaxProgress) {
            mProgressText = (int) (progress / mMaxProgress * 100) + "%";
            mTextPaint.getTextBounds(mProgressText, 0, mProgressText.length(), mTextRect);
//            invalidate();
            postInvalidate();
        }
    }
}
