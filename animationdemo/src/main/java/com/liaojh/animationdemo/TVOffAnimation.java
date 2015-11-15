package com.liaojh.animationdemo;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @author LiaoJH
 * @DATE 15/11/12
 * @VERSION 1.0
 * @DESC TODO
 */
public class TVOffAnimation extends Animation
{
    private int mCenterWidth, mCenterHeight;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight)
    {
        super.initialize(width, height, parentWidth, parentHeight);
        mCenterWidth = width / 2;
        mCenterHeight = height / 2;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t)
    {
        super.applyTransformation(interpolatedTime, t);
        Matrix matrix = t.getMatrix();
        matrix.preScale(interpolatedTime, 1 - interpolatedTime, mCenterWidth, mCenterHeight);
    }
}
