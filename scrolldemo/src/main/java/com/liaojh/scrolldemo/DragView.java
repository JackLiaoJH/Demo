package com.liaojh.scrolldemo;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * @author LiaoJH
 * @DATE 15/11/7
 * @VERSION 1.0
 * @DESC TODO
 */
public class DragView extends View
{
    private float mLastX;
    private float mLastY;

    private Scroller mScroller;

    public DragView(Context context)
    {
        this(context, null);
    }

    public DragView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        //第一步:初始化Scroller,使用默认的滑动时长与插值器
        mScroller = new Scroller(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //获取当前输入点的坐标,(视图坐标)
//        float x = event.getX();
//        float y = event.getY();
        float rawX = event.getRawX();
        float rawY = event.getRawY();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                //记录按下触摸点的位置
//                mLastX = x;
//                mLastY = y;
                mLastX = rawX;
                mLastY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算偏移量(此次坐标值-上次触摸点坐标值)
//                int offSetX = (int) (x - mLastX);
//                int offSetY = (int) (y - mLastY);
                int offSetX = (int) (rawX - mLastX);
                int offSetY = (int) (rawY - mLastY);

                //方式1,layout方法:  在当前left,right,top.bottom的基础上加上偏移量
//                layout(getLeft() + offSetX,
//                        getTop() + offSetY,
//                        getRight() + offSetX,
//                        getBottom() + offSetY
//                );
                //方式2 offsetLeftAndRight()与offsetTopAndBottom()
//                offsetLeftAndRight(offSetX);
//                offsetTopAndBottom(offSetY);

                //方式3 LayoutParams
//                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayoutParams();
//                lp.leftMargin = getLeft() + offSetX;
//                lp.topMargin = getTop() + offSetY;
//                setLayoutParams(lp);

//                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                lp.leftMargin = getLeft() + offSetX;
//                lp.topMargin = getTop() + offSetY;
//                setLayoutParams(lp);

                //方式4:scrollBy
                ((View) getParent()).scrollBy(-offSetX, -offSetY);

                //重新设置初始位置的值
                mLastX = rawX;
                mLastY = rawY;
                break;
            case MotionEvent.ACTION_UP:
                //方式5: scroller
                //第三步
                //当手指离开时,执行滑动过程
                ViewGroup viewGroup = (ViewGroup) getParent();
//                mScroller.startScroll(
//                        viewGroup.getScrollX(),
//                        viewGroup.getScrollY(),
//                        -viewGroup.getScrollX(),
//                        0,
//                        800
//                );
                //刷新布局,从而调用computeScroll方法
//                invalidate();

                //方式6:属性动画
                ObjectAnimator.ofFloat(this, "translationX", viewGroup.getScrollX()).setDuration(500)
                              .start();
                break;
        }
        return true;
    }


    @Override
    //第二步
    public void computeScroll()
    {
        super.computeScroll();
        //判断Scroller是否执行完成
//        if (mScroller.computeScrollOffset())
//        {
//            ((View) getParent()).scrollTo(
//                    mScroller.getCurrX(),
//                    mScroller.getCurrY()
//            );
//            //调用invalidate()computeScroll()方法
//            invalidate();
//        }
    }
}
