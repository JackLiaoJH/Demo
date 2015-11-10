package com.liaojh.floatwindowdemo.other;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.liaojh.floatwindowdemo.R;

public class FloatBallView extends LinearLayout
{

    /**
     * 记录小悬浮窗的宽度
     */
    public static int viewWidth;
    /**
     * 记录小悬浮窗的高度
     */
    public static int viewHeight;

    /**
     * 用于更新小悬浮窗的位置
     */
    private WindowManager              windowManager;
    /**
     * 小悬浮窗的参数
     */
    private WindowManager.LayoutParams mParams;

    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;
    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;
    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;
    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;
    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;
    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;

    private long mDownTime, mUpTime;

    public FloatBallView(Context context)
    {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_ball_layout, this);
        View view = findViewById(R.id.iv_float_ball);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - ScreenUtils.getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - ScreenUtils.getStatusBarHeight();

                mDownTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - ScreenUtils.getStatusBarHeight();
                // 手指移动的时候更新小悬浮窗的位置
                updateViewPosition();

                //移动小悬浮框时，显示删除框
                if (!ShareFloatWindowManager.isDeleteWindowShowing())
                {
                    ShareFloatWindowManager.createDeleteWindow(getContext());
                }

                if (isInDeleteArea(xInScreen, yInScreen))
                {
                    ShareFloatWindowManager.setDeleteTextColor(Color.BLACK);
                }
                else
                {
                    ShareFloatWindowManager.setDeleteTextColor(Color.GRAY);
                }
                break;
            case MotionEvent.ACTION_UP:
                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，
                // 且时间小于2秒,则视为触发了单击事件。
                mUpTime = System.currentTimeMillis();
                if (xDownInScreen == xInScreen && yDownInScreen == yInScreen
                        )
                {
                    if (mUpTime - mDownTime < 2 * 1000)
                    {
                        openFloatStickerWindow();
                    } else {
                        //长按事件
                    }
                }

                //移除删除框
                ShareFloatWindowManager.removeDeleteWindow(getContext());
                //用户将小图标拖动到了删除框，则移除所有悬浮窗，并停止Service
                if (isInDeleteArea(xInScreen, yInScreen))
                {
                    ShareFloatWindowManager.removeFloatStickerWindow(getContext());
                    ShareFloatWindowManager.removeFloatBallWindow(getContext());

                    //这里不需要停止
                    Intent intent = new Intent(getContext(), FloatWindowService.class);
                    getContext().stopService(intent);
                }

                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
     */
    public void setParams(WindowManager.LayoutParams params)
    {
        mParams = params;
    }

    /**
     * 更新小悬浮窗在屏幕中的位置。
     */
    private void updateViewPosition()
    {
        mParams.x = (int) (xInScreen - xInView);
        mParams.y = (int) (yInScreen - yInView);
        windowManager.updateViewLayout(this, mParams);
    }

    /**
     * 打开大悬浮窗，同时关闭小悬浮窗。
     */
    private void openFloatStickerWindow()
    {
        ShareFloatWindowManager.createFloatStickerWindow(getContext());
        ShareFloatWindowManager.removeFloatBallWindow(getContext());
    }

    /**
     * 判断小悬浮框的当前位置是否在删除框范围内
     */
    private boolean isInDeleteArea(float positionX, float positionY)
    {
        if (positionY > ScreenUtils.getScreenH() / 3 * 2 - FloatDeleteView.viewHeight
                && positionY < ScreenUtils.getScreenH() / 3 * 2
                && positionX > (ScreenUtils.getScreenW() - FloatDeleteView.viewWidth) / 2
                && positionX < ScreenUtils.getScreenW() / 2 + FloatDeleteView.viewWidth / 2
                )
        {
            return true;
        }
        return false;
    }

}
