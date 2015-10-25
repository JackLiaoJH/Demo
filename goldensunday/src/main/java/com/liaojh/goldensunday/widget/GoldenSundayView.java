package com.liaojh.goldensunday.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaojh.goldensunday.R;
import com.liaojh.goldensunday.TimeUtils;

import java.util.Calendar;
import java.util.Locale;


/**
 * @author LiaoJH
 * @DATE 15/10/25
 * @VERSION 1.0
 * @DESC TODO
 */
public class GoldenSundayView extends RelativeLayout implements View.OnClickListener
{
    private View     mGoldenSundayView;
    private TextView mTvHour1, mTvHour2, mTvMinute1, mTvMinute2, mTvSecond1, mTvSecond2, mTvDay, mTvDayText;
    private ImageView      mIvCancel;
    private RelativeLayout mRlTime, mRlDay;

    private static final String TIME_FLAG        = "-";
    private static final int    DAY_WEEK_COUNT   = 7;
    private static final int    HOUR_DAY_COUNT   = 23;
    private static final int    MINUTE_DAY_COUNT = 60;
    private static final int    SECOND_DAY_COUNT = 60;

    private static final int     WHAT_TIME_UPDATE = 200;
    /**
     * 这里主要是控制是否每秒刷新,超过一天的话不需要每秒都刷新
     */
    private              boolean mIsTime          = false;

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (WHAT_TIME_UPDATE == msg.what)
            {
                updateTime();
                if (mIsTime)
                {
                    mHandler.sendEmptyMessageDelayed(WHAT_TIME_UPDATE, 1000);
                }
            }
        }
    };

    public GoldenSundayView(Context context)
    {
        this(context, null);
    }

    public GoldenSundayView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public GoldenSundayView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
        mHandler.sendEmptyMessage(WHAT_TIME_UPDATE);
    }

    private void init()
    {

        mGoldenSundayView = LayoutInflater.from(getContext())
                                          .inflate(R.layout.traffic_golden_sunday_layout, this);
        mTvHour1 = (TextView) mGoldenSundayView.findViewById(R.id.tv_traffic_golden_sunday_hour1);
        mTvHour2 = (TextView) mGoldenSundayView.findViewById(R.id.tv_traffic_golden_sunday_hour2);
        mTvMinute1 = (TextView) mGoldenSundayView
                .findViewById(R.id.tv_traffic_golden_sunday_minute1);
        mTvMinute2 = (TextView) mGoldenSundayView
                .findViewById(R.id.tv_traffic_golden_sunday_minute2);
        mTvSecond1 = (TextView) mGoldenSundayView
                .findViewById(R.id.tv_traffic_golden_sunday_second1);
        mTvSecond2 = (TextView) mGoldenSundayView
                .findViewById(R.id.tv_traffic_golden_sunday_second2);
        mRlTime = (RelativeLayout) mGoldenSundayView
                .findViewById(R.id.rl_traffic_golden_sunday_time);


        mTvDay = (TextView) mGoldenSundayView.findViewById(R.id.tv_traffic_golden_sunday_day);
        mTvDayText = (TextView) mGoldenSundayView
                .findViewById(R.id.tv_traffic_golden_sunday_day_text);
        mRlDay = (RelativeLayout) mGoldenSundayView
                .findViewById(R.id.rl_traffic_golden_sunday_day);

        mIvCancel = (ImageView) mGoldenSundayView
                .findViewById(R.id.iv_traffic_golden_sunday_cancel);

        mIvCancel.setOnClickListener(this);

        updateTime();
    }

    private void updateTime()
    {
        String time = disSundayDay(false);
        if (time.contains(TIME_FLAG))
        {
            mIsTime = true;
            Log.i("liao", "updateTime..." + time);
            mRlTime.setVisibility(VISIBLE);
            mRlDay.setVisibility(GONE);
            String[] times = time.split(TIME_FLAG);
            mTvHour1.setText(times[0]);
            mTvHour2.setText(times[1]);
            mTvMinute1.setText(times[2]);
            mTvMinute2.setText(times[3]);
            mTvSecond1.setText(times[4]);
            mTvSecond2.setText(times[5]);
        }
        else
        {
            Log.i("liao", "updateDay...");

            mIsTime = false;
            mRlTime.setVisibility(GONE);
            mRlDay.setVisibility(VISIBLE);
            Integer day = Integer.valueOf(time);
            mTvDayText.setText(
                    day > 1 ? getResources().getString(R.string.traffic_golden_sunday_normal_days)
                            : getResources().getString(R.string.traffic_golden_sunday_normal_day));
            mTvDay.setText(time);
        }
    }


    public String disSundayDay(boolean isContainDay)
    {
        StringBuilder time = new StringBuilder();

        Calendar calendar  = Calendar.getInstance(Locale.getDefault());
        int      dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        //模拟
//        int dayOfWeek = Calendar.SATURDAY;

        if (1 == dayOfWeek || DAY_WEEK_COUNT == dayOfWeek) //周六或周日
        {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            time.append(formatTime(HOUR_DAY_COUNT - hour));
            int minute = calendar.get(Calendar.MINUTE);
            time.append(formatTime(MINUTE_DAY_COUNT - minute));
            int second = calendar.get(Calendar.SECOND);
            time.append(formatTime(SECOND_DAY_COUNT - second));
        }
        else
        {
            int disSunday = isContainDay ? DAY_WEEK_COUNT - dayOfWeek + 1 : DAY_WEEK_COUNT - dayOfWeek;
            time.append(disSunday);
        }
        return time.toString();
    }

    private String formatTime(int time)
    {
        StringBuilder sb = new StringBuilder();
        if (time > 9)
        {
            sb.append(time / 10)
              .append(TIME_FLAG)
              .append(time % 10);
        }
        else
        {
            sb.append("0")
              .append(TIME_FLAG)
              .append(time);
        }
        sb.append(TIME_FLAG);
        return sb.toString();
    }


    @Override
    public void onClick(View v)
    {
        mGoldenSundayView.setVisibility(GONE);
        mHandler.removeCallbacksAndMessages(null);
    }
}
