package com.liaojh.scrolldemo;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @author LiaoJH
 * @DATE 15/11/7
 * @VERSION 1.0
 * @DESC TODO
 */
public class DragLayout extends FrameLayout
{
    private ViewDragHelper viewDragHelper;
    private View           mMainView;
    private View           mMenuView;

    public DragLayout(Context context)
    {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        //1. 初始化ViewDragHelper
        viewDragHelper = ViewDragHelper.create(this, callback);

    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback()
    {
        @Override
        //此方法中可以指定在创建ViewDragHelper时,参数ViewParent中的那些子View可以被移动
        //根据返回结果决定当前child是否可以拖拽
        //  child 当前被拖拽的View
        //  pointerId 区分多点触摸的id
        public boolean tryCaptureView(View child, int pointerId)
        {
            //如果当前触摸的view是mMainView时开始检测
            return mMainView == child;
        }

        @Override
        //水平方向的滑动
        // 根据建议值 修正将要移动到的(横向)位置   (重要)
        // 此时没有发生真正的移动
        public int clampViewPositionHorizontal(View child, int left, int dx)
        {
            //返回要滑动的距离,默认返回0,既不滑动
            //参数参考clampViewPositionVertical
            if (child == mMainView)
            {
                if (left > 300)
                {
                    left = 300;
                }
                if (left < 0)
                {
                    left = 0;
                }
            }
            return left;
        }

        @Override
        //垂直方向的滑动
        // 根据建议值 修正将要移动到的(纵向)位置   (重要)
        // 此时没有发生真正的移动
        public int clampViewPositionVertical(View child, int top, int dy)
        {
            //top : 垂直向上child滑动的距离,
            //dy: 表示比较前一次的增量,通常只需返回top即可,如果需要精确计算padding等属性的话,就需要对left进行处理
            return super.clampViewPositionVertical(child, top, dy); //0
        }

        @Override
        //拖动结束时调用
        public void onViewReleased(View releasedChild, float xvel, float yvel)
        {
            if (mMainView.getLeft() < 150)
            {
                // 触发一个平滑动画,关闭菜单,相当于Scroll的startScroll方法
                if (viewDragHelper.smoothSlideViewTo(mMainView, 0, 0))
                {
                    // 返回true代表还没有移动到指定位置, 需要刷新界面.
                    // 参数传this(child所在的ViewGroup)
                    ViewCompat.postInvalidateOnAnimation(DragLayout.this);
                }
            }
            else
            {
                //打开菜单
                if (viewDragHelper.smoothSlideViewTo(mMainView, 300, 0)) ;
                {
                    ViewCompat.postInvalidateOnAnimation(DragLayout.this);
                }
            }
            super.onViewReleased(releasedChild, xvel, yvel);
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId)
        {
            // 当capturedChild被捕获时,调用.
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public int getViewHorizontalDragRange(View child)
        {
            // 返回拖拽的范围, 不对拖拽进行真正的限制. 仅仅决定了动画执行速度
            return 300;
        }

        @Override
        //当View位置改变的时候, 处理要做的事情 (更新状态, 伴随动画, 重绘界面)
        // 此时,View已经发生了位置的改变
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy)
        {
            // changedView 改变位置的View
            // left 新的左边值
            // dx 水平方向变化量
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId)
        {
            super.onEdgeDragStarted(edgeFlags, pointerId);
        }

        @Override
        public void onViewDragStateChanged(int state)
        {
            super.onViewDragStateChanged(state);
        }
    };

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        //2. 将事件交给ViewDragHelper
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //2. 将触摸事件传递给ViewDragHelper,不可少
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    //3. 重写computeScroll
    @Override
    public void computeScroll()
    {
        //持续平滑动画 (高频率调用)
        if (viewDragHelper.continueSettling(true))
        //如果返回true, 动画还需要继续执行
        {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        int count = getChildCount();
        if (count < 2)
        {
            throw new RuntimeException("count view >= 2");
        }

        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
    }
}
