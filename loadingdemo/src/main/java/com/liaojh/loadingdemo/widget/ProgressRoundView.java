package com.liaojh.loadingdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
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
    private RectF mArcRectF;

    private float mCircleX = 200, mCircleY = 200;
    private float mInCircleRadius = 70;
    private float mOutCircleRadius = 100;

    private float mArcLeft, mArcTop, mArcRight, mArcBottom;

    private float mStartAngle = 0, mSweepAngle = 220;

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

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
