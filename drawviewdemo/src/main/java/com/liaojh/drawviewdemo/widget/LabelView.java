package com.liaojh.drawviewdemo.widget;/*
 * Copyright (C) 2007 The Android Open Source Project 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

// Need the following import to get access to the app resources, since this  
// class is in a sub-package.  

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.liaojh.drawviewdemo.R;


/**
 * Example of how to write a custom subclass of View. LabelView
 * is used to draw simple text views. Note that it does not handle
 * styled text or right-to-left writing systems.
 */
public class LabelView extends View
{
    private Paint  mTextPaint;
    private String mText;
    private int    mAscent;

    /**
     * Constructor.  This version is only needed if you will be instantiating
     * the object manually (not from a layout XML file).
     *
     * @param context
     */
    public LabelView(Context context)
    {
        super(context);
        initLabelView();
    }

    /**
     * Construct object, initializing with any attributes we understand from a
     * layout file. These attributes are defined in
     * SDK/assets/res/any/classes.xml.
     *
     * @see android.view.View#View(android.content.Context, android.util.AttributeSet)
     */
    public LabelView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initLabelView();

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.LabelView);

        CharSequence s = a.getString(R.styleable.LabelView_text);
        if (s != null)
        {
            setText(s.toString());
        }

        // Retrieve the color(s) to be used for this view and apply them.  
        // Note, if you only care about supporting a single color, that you  
        // can instead call a.getColor() and pass that to setTextColor().  
        setTextColor(a.getColor(R.styleable.LabelView_textColor, 0xFF000000));

        int textSize = a.getDimensionPixelOffset(R.styleable.LabelView_textSize, 0);
        if (textSize > 0)
        {
            setTextSize(textSize);
        }

        a.recycle();
    }

    private final void initLabelView()
    {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        // Must manually scale the desired text size to match screen density  
        mTextPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
        mTextPaint.setColor(0xFF000000);
        setPadding(3, 3, 3, 3);
    }

    /**
     * Sets the text to display in this label
     *
     * @param text The text to display. This will be drawn as one line.
     */
    public void setText(String text)
    {
        mText = text;
        requestLayout();
        invalidate();
    }

    /**
     * Sets the text size for this label
     *
     * @param size Font size
     */
    public void setTextSize(int size)
    {
        // This text size has been pre-scaled by the getDimensionPixelOffset method  
        mTextPaint.setTextSize(size);
        requestLayout();
        invalidate();
    }

    /**
     * Sets the text color for this label.
     *
     * @param color ARGB value for the text
     */
    public void setTextColor(int color)
    {
        mTextPaint.setColor(color);
        invalidate();
    }

    /**
     * @see android.view.View#measure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    private int measureWidth(int measureSpec)
    {
        int result   = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.i("DEMO", "measureSpec:" + Integer.toBinaryString(measureSpec));
        Log.i("DEMO", "specMode:" + Integer.toBinaryString(specMode));
        Log.i("DEMO", "specSize:" + Integer.toBinaryString(specSize));

        /**
         * 一般来说，自定义控件都会去重写View的onMeasure方法，因为该方法指定该控件在屏幕上的大小。 
         protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec)
         onMeasure传入的两个参数是由上一层控件传入的大小，有多种情况，重写该方法时需要对计算控件的实际大小，然后调用setMeasuredDimension(int, int)设置实际大小。
         onMeasure传入的widthMeasureSpec和heightMeasureSpec不是一般的尺寸数值，而是将模式和尺寸组合在一起的数值。
         我们需要通过int mode = MeasureSpec.getMode(widthMeasureSpec)得到模式，用int size = MeasureSpec.getSize(widthMeasureSpec)得到尺寸。
         mode共有三种情况，取值分别为MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY, MeasureSpec.AT_MOST。
         MeasureSpec.EXACTLY是精确尺寸，当我们将控件的layout_width或layout_height指定为具体数值时如andorid:layout_width="50dip"，
         或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。
         MeasureSpec.AT_MOST是最大尺寸，当控件的layout_width或layout_height指定为WRAP_CONTENT时，
         控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
         MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，通过measure方法传入的模式。
         因此，在重写onMeasure方法时要根据模式不同进行尺寸计算。下面代码就是一种比较典型的方式：
         */

        if (specMode == MeasureSpec.EXACTLY)
        {
            // We were told how big to be  
            result = specSize;
        }
        else
        {
            // Measure the text  
            result = (int) mTextPaint.measureText(mText) + getPaddingLeft()
                    + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST)
            {
                // Respect AT_MOST value if that was what is called for by measureSpec  
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec)
    {
        int result   = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        // ascent：是baseline之上至字符最高处的距离
        mAscent = (int) mTextPaint.ascent();
        if (specMode == MeasureSpec.EXACTLY)
        {
            // We were told how big to be  
            result = specSize;
        }
        else
        {
            // Measure the text (beware: ascent is a negative number)  
            result = (int) (-mAscent + mTextPaint.descent()) + getPaddingTop()
                    + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST)
            {
                // Respect AT_MOST value if that was what is called for by measureSpec  
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * Render the text
     *
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawText(mText, getPaddingLeft(), getPaddingTop() - mAscent, mTextPaint);
    }
}  