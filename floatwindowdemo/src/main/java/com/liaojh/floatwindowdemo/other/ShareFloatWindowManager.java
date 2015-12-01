package com.liaojh.floatwindowdemo.other;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class ShareFloatWindowManager
{

    /**
     * 悬浮球View的实例
     */
    private static FloatBallView    mFloatBallView;
    /**
     * 悬浮窗View的实例
     */
    private static FloatStickerView mFloatStickerView;
    /**
     * 删除框
     */
    private static FloatDeleteView  mFloatDeleteView;

    /**
     * 悬浮球View的参数
     */
    private static LayoutParams mFloatBallParams;
    /**
     * 悬浮窗View的参数
     */
    private static LayoutParams mFloatStickerParams;
    /**
     * 删除框的参数
     */
    private static LayoutParams mFloatDeleteParams;

    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager mWindowManager;

    /**
     * 创建一个悬浮球。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createFloatBallView(Context context)
    {
        WindowManager windowManager = getWindowManager(context);
        if (mFloatBallView == null)
        {
            mFloatBallView = new FloatBallView(context);
            if (mFloatBallParams == null)
            {
                mFloatBallParams = new LayoutParams();
                mFloatBallParams.type = LayoutParams.TYPE_PHONE;
                mFloatBallParams.format = PixelFormat.RGBA_8888;
                mFloatBallParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | LayoutParams.FLAG_NOT_FOCUSABLE;
                mFloatBallParams.gravity = Gravity.LEFT | Gravity.TOP;
                mFloatBallParams.width = FloatBallView.viewWidth;
                mFloatBallParams.height = FloatBallView.viewHeight;
                mFloatBallParams.x = ScreenUtils.getScreenW() / 6;
                mFloatBallParams.y = ScreenUtils.getScreenH() / 3 * 2;
            }
            mFloatBallView.setParams(mFloatBallParams);
            windowManager.addView(mFloatBallView, mFloatBallParams);
        }

    }

    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeFloatBallWindow(Context context)
    {
        if (mFloatBallView != null)
        {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(mFloatBallView);
            mFloatBallView = null;
        }
    }

    /**
     * 创建表情分享悬浮窗
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createFloatStickerWindow(Context context)
    {
        WindowManager windowManager = getWindowManager(context);
        if (mFloatStickerView == null)
        {
            mFloatStickerView = new FloatStickerView(context);
            if (mFloatStickerParams == null)
            {
                mFloatStickerParams = new LayoutParams();
                mFloatStickerParams.y = ScreenUtils.getScreenH() - FloatStickerView.viewHeight;
                mFloatStickerParams.x = 0;
                mFloatStickerParams.type = LayoutParams.TYPE_PHONE;
                mFloatStickerParams.format = PixelFormat.RGBA_8888;
                mFloatStickerParams.gravity = Gravity.LEFT | Gravity.TOP;
                mFloatStickerParams.width = FloatStickerView.viewWidth;
                mFloatStickerParams.height = FloatStickerView.viewHeight;
            }
            windowManager.addView(mFloatStickerView, mFloatStickerParams);
        }
    }

    /**
     * 将表情悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeFloatStickerWindow(Context context)
    {
        if (mFloatStickerView != null)
        {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(mFloatStickerView);
            mFloatStickerView = null;
        }
    }

    /**
     * 创建删除悬浮框，位于屏幕下方
     */
    public static void createDeleteWindow(Context context)
    {
        WindowManager windowManager = getWindowManager(context);
        if (mFloatDeleteView == null)
        {
            mFloatDeleteView = new FloatDeleteView(context);
            if (mFloatDeleteParams == null)
            {
                mFloatDeleteParams = new LayoutParams();
                mFloatDeleteParams.x = (ScreenUtils.getScreenW() -
                        FloatDeleteView.viewWidth) / 2;
                mFloatDeleteParams.y = ScreenUtils.getScreenH() / 3;
                mFloatDeleteParams.type = LayoutParams.TYPE_PHONE;
                mFloatDeleteParams.format = PixelFormat.RGBA_8888;
                mFloatDeleteParams.gravity = Gravity.LEFT | Gravity.BOTTOM;
                mFloatDeleteParams.width = FloatDeleteView.viewWidth;
                mFloatDeleteParams.height = FloatDeleteView.viewHeight;
            }
            windowManager.addView(mFloatDeleteView, mFloatDeleteParams);
        }
    }

    /**
     * 移除删除悬浮框
     */
    public static void removeDeleteWindow(Context context)
    {
        if (mFloatDeleteView != null)
        {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(mFloatDeleteView);
            mFloatDeleteView = null;
        }
    }


    /**
     * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static boolean isWindowShowing()
    {
        return mFloatBallView != null || mFloatStickerView != null;
    }

    /**
     * 删除悬浮框是否正在显示
     */
    public static boolean isDeleteWindowShowing()
    {
        return mFloatDeleteView != null;
    }

    /**
     * 设置删除框字体颜色
     */
    public static void setDeleteTextColor(int color)
    {
        if (mFloatDeleteView != null)
        {
            mFloatDeleteView.getTextView().setTextColor(color);
        }
    }

    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context 必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private static WindowManager getWindowManager(Context context)
    {
        if (mWindowManager == null)
        {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }
}
