package com.liaojh.viewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * @author LiaoJH
 * @DATE 15/12/15
 * @VERSION 1.0
 * @DESC TODO 完整实现自定义View案例
 */
public class CircleView extends View {
    private int mColor = Color.RED;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mRadius;
    private int WIDTH_DEFAULT_SIZE = 200;
    private int HEIGHT_DEFAULT_SIZE = 200;

    private float mScale=1.0f;
    private float mDx, mDy;
    ValueAnimator valueAnimator;
    ValueAnimator translateAnim;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
            mColor = ta.getColor(R.styleable.CircleView_circle_color, mColor);
            ta.recycle();
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnim();
            }
        }, 100);
    }

    private void startAnim() {

        valueAnimator = ValueAnimator.ofFloat(0.4f, 0.8f, 1.0f);
        valueAnimator.setDuration(750);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                mScale = value;
                postInvalidate();
            }
        });
//        valueAnimator.start();


        translateAnim = ValueAnimator.ofFloat(0, mWidth / 15, mWidth / 15 * 2);
        translateAnim.setDuration(750);
        translateAnim.setRepeatCount(-1);
        translateAnim.setRepeatMode(ValueAnimator.REVERSE);
        translateAnim.setInterpolator(new OvershootInterpolator(3));
        translateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDx = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        translateAnim.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int px = getPaddingLeft() + mWidth / 2;
        int py = getPaddingTop() + mHeight / 2;
        canvas.save();
        canvas.scale(mScale, mScale);
        canvas.translate(mDx, mDy);
        canvas.drawCircle(px, py, mRadius, mPaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (MeasureSpec.AT_MOST == widthMode && MeasureSpec.AT_MOST == heightMode) {
            setMeasuredDimension(WIDTH_DEFAULT_SIZE, HEIGHT_DEFAULT_SIZE);
        } else if (MeasureSpec.AT_MOST == widthMode) {
            setMeasuredDimension(WIDTH_DEFAULT_SIZE, heightSize);
        } else if (MeasureSpec.AT_MOST == heightMode) {
            setMeasuredDimension(widthSize, HEIGHT_DEFAULT_SIZE);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        mWidth = getMeasuredWidth() - getPaddingRight() - getPaddingLeft();
        mRadius = Math.min(mWidth, mHeight) / 2;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
            valueAnimator = null;
        }
        if (translateAnim != null && translateAnim.isRunning()) {
            translateAnim.cancel();
            translateAnim = null;
        }
    }
}
