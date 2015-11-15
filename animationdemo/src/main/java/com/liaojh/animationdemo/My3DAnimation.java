package com.liaojh.animationdemo;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;

/**
 * @author LiaoJH
 * @DATE 15/11/12
 * @VERSION 1.0
 * @DESC TODO
 */
public class My3DAnimation extends Animation
{
    private int mCenterWidth, mCenterHeight;
    private Camera mCamera  = new Camera();
    private float  mRotateY = 0.0f;

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t)
    {
        super.applyTransformation(interpolatedTime, t);
        Matrix matrix = t.getMatrix();
        mCamera.save();
        mCamera.rotateY(mRotateY * interpolatedTime);
        mCamera.getMatrix(matrix);
        mCamera.restore();

        matrix.preTranslate(mCenterWidth, mCenterHeight);
        matrix.preTranslate(-mCenterWidth, -mCenterHeight);
    }

    public void setRotateY(float rotateY)
    {
        mRotateY = rotateY;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight)
    {
        super.initialize(width, height, parentWidth, parentHeight);
        //初始化操作
        setDuration(2000);
        setInterpolator(new BounceInterpolator());
        setFillAfter(true);
//        setRepeatCount(Integer.MAX_VALUE);
//        setRepeatMode(Animation.REVERSE);

        mCenterWidth = width / 2;
        mCenterHeight = height / 2;

    }
}
