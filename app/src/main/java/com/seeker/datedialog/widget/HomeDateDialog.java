package com.seeker.datedialog.widget;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.seeker.demo.datedialogexample.R;
import com.seeker.datedialog.widget.util.CalendarHelper;
import com.seeker.datedialog.widget.util.LogUtils;
import com.seeker.datedialog.widget.wheel.WheelTextView;
import com.seeker.datedialog.widget.wheel.abs.WheelAdapterView;
import com.seeker.datedialog.widget.wheel.abs.WheelGallery;

import java.util.Calendar;

/**
 * 强大日期选择对话框. <br/>
 * 1. 支持设定开始日和结束日。<br/>
 * 2. 支持，STATE_TODAY 日期上显示 今日 /昨天等<br/>
 *
 * @author zhengxiaobin <gybin02@Gmail.com>
 * @since 2016/4/6 20:17
 */
public class HomeDateDialog extends AbsBottomDialog {
    /**
     * 是否出现脏数据
     */
    private boolean isDirty = false;
    private String TAG = "DateDialog";

    public static int[] mYears = {}, mMonths = {}, mDays = {};

    public static String[] mYearStrs = {}, mMonthStrs = {}, mDayStrs = {};

    private TextView tvTitle;

    private boolean bOk = false;

    // 默认值
    private int mYear = 2015;
    private int mMonth = 8;
    private int mDay = 1;

    private WheelTextView wheelYear;
    private WheelTextView wheelMonth;
    private WheelTextView wheelDay;


    public static final int STATE_NORMAL = 0;
    /**
     * 日期滚轮上加显示 （今日/昨天/前天） 等字样
     */
    public static final int STATE_TODAY = 1;

    private String mTitle;
    /**
     * #STATE_NORMAL <br>
     * #STATE_TODAY
     */
    public int state = 0;
    private OnDialogResult onDialogResult;

    private Calendar startCalendar;
    private Calendar endCalendar;
    private Calendar selectCalendar;
    Activity mContext;

    public HomeDateDialog(Activity context, @NonNull Calendar startCalendar, @NonNull Calendar endCalendar, @NonNull Calendar selectCalendar, String title, int state, OnDialogResult onDialogResult) {
        super(context);
        this.mTitle = title;
        this.state = state;
        this.onDialogResult = onDialogResult;
        this.startCalendar = startCalendar;
        this.endCalendar = endCalendar;
        this.selectCalendar = selectCalendar;
        mYear = selectCalendar.get(Calendar.YEAR);
        mMonth = selectCalendar.get(Calendar.MONTH) + 1;
        mDay = selectCalendar.get(Calendar.DAY_OF_MONTH);
        mContext = context;

        if (startCalendar.after(endCalendar) || !CalendarHelper.isBetween(startCalendar, endCalendar, selectCalendar)) {
            //异常数据过滤
            LogUtils.i(TAG, "日期对话框数据传入异常：开始日期：%1s,结束日期：%2s 选中日期：%3s", startCalendar.getTime().toLocaleString(), endCalendar.getTime().toLocaleString(), selectCalendar.getTime().toLocaleString());
            isDirty = true;
            return;
        }

        initView();
        initUI();
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_date_home;
    }

    public void initUI() {
        findViewById(R.id.reminder_no).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        bOk = false;
                        dismissDialogEx();
                    }
                });
        findViewById(R.id.reminder_yes).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        bOk = true;
                        dismissDialogEx();
                    }
                });


        setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                if (bOk) {
                    Calendar calendar = getCalendar();
                    onDialogResult.onSelectedResult(true, calendar);
                } else {
                    onDialogResult.onSelectedResult(false, null);
                }

            }
        });
//        fillResource();
    }

    @NonNull
    private Calendar getCalendar() {
        Calendar calendar = (Calendar) Calendar.getInstance().clone();
        calendar.set(mYear, mMonth - 1, mDay);
        LogUtils.i(TAG, "Picker返回结果:" + calendar.getTime().toLocaleString());
        return calendar;
    }


    public void dismissDialogEx() {
        try {
            this.dismiss();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void show() {
        if (isDirty) {
//            ToastUtils.showToast(mContext, "经期记录不能在未来哦，请联系客服人员修改～");
            return;
        }
        super.show();
    }

    // 准备数据
    private void initView() {
        tvTitle = (TextView) findViewById(R.id.dialog_title);
        if (!TextUtils.isEmpty(mTitle)) {
            tvTitle.setText(mTitle);
            tvTitle.setVisibility(View.VISIBLE);
        }
        wheelYear = (WheelTextView) findViewById(R.id.wtv_year);
        wheelMonth = (WheelTextView) findViewById(R.id.wtv_month);
        wheelDay = (WheelTextView) findViewById(R.id.wtv_day);

        wheelYear.setScrollCycle(false);
        wheelMonth.setScrollCycle(false);
        wheelDay.setScrollCycle(false);

        initYears();
        initMonths(mYear);
        initDays(mYear, mMonth);
    }

    private void initYears() {
        //时间选择器可选择时间跨度为上次经期结束后的第六天至今天。
        //如图2，当日日期后备注今天，往前三天分别备注：昨天、前天、大前天，更换非当下年月，则不再显示该备注信息。


        int startYear = startCalendar.get(Calendar.YEAR);
        int endYear = endCalendar.get(Calendar.YEAR);
        if (startYear != endYear) {
            int count = endYear - startYear + 1;
            mYears = new int[count];
            for (int i = 0; i < count; i++) {
                mYears[i] = startYear + i;
            }
        } else {
            mYears = new int[1];
            mYears[0] = startYear;
        }
        mYearStrs = new String[mYears.length];
        for (int i = 0; i < mYears.length; i++) {
            mYearStrs[i] = mYears[i] + "年";
        }


        wheelYear.setData(mYearStrs);
        int yearPosition = getCurrentPosition(mYear, mYears);
        wheelYear.setSelect(yearPosition);
        wheelYear.setOnWheelItemSelectListener(new WheelTextView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(WheelAdapterView<?> parent, View view, int position, long id) {
                if (mYears != null && mYears.length > 0) {
                    mYear = mYears[position];
                }
                initMonths(mYear);
                initDays(mYear, mMonth);
            }
        });

        wheelYear.setOnEndFlingListener(new WheelGallery.OnEndFlingListener() {
            @Override
            public void onEndFling(WheelGallery v) {

                onDialogResult.onScrollFinish(getCalendar());
            }
        });
    }

    private void initMonths(int yearSelect) {
        Calendar instance = Calendar.getInstance();
        Calendar calendarNew = (Calendar) instance.clone();
        calendarNew.set(Calendar.YEAR, yearSelect);

        int maximum = calendarNew.getActualMaximum(Calendar.MONTH);
        int start = 0;
        int count = 0;
        int monthBetween = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
//            if (dayBetween >= 0) {
        if (CalendarHelper.isYearSame(startCalendar, endCalendar)) {
            count = monthBetween + 1;
            start = startCalendar.get(Calendar.MONTH);
        } else {
            if (CalendarHelper.isYearSame(startCalendar, calendarNew)) {
                int monthOfYear = startCalendar.get(Calendar.MONTH);
                count = maximum - monthOfYear + 1;
                start = monthOfYear;
            } else if (CalendarHelper.isYearSame(endCalendar, calendarNew)) {
                count = endCalendar.get(Calendar.MONTH) + 1;
                start = 0;
            } else {
                count = maximum;
                start = 0;
            }

        }

        // init month
        mMonths = new int[count];
        mMonthStrs = new String[count];
        for (int i = 0; i < count; i++) {
            int index = start + i + 1;
            mMonths[i] = index;
            if (index < 10) {
                mMonthStrs[i] = /*"0" +*/ (index) + "月";
            } else {
                mMonthStrs[i] = index + "" + "月";
            }
        }
//            }
//        }

        // 设置月份
        wheelMonth.setData(mMonthStrs);
        int monthPosition;
        monthPosition = getCurrentPosition(mMonth, mMonths);
        wheelMonth.setSelect(monthPosition);
        wheelMonth.setOnWheelItemSelectListener(new WheelTextView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(WheelAdapterView<?> parent, View view, int position, long id) {
                if (mMonths != null && mMonths.length > 0) {
                    mMonth = mMonths[position];
                }
                initDays(mYear, mMonth);
            }
        });
        wheelMonth.setOnEndFlingListener(new WheelGallery.OnEndFlingListener() {
            @Override
            public void onEndFling(WheelGallery v) {
                onDialogResult.onScrollFinish(getCalendar());
            }
        });

    }


    /**
     * 根据年份和月份获取获取该月的日子
     *
     * @param year
     * @param month
     */
    private void initDays(int year, int month) {

        Calendar instance = Calendar.getInstance();
        // 设置年月日
        Calendar calendarNew = (Calendar) instance.clone();
        calendarNew.set(Calendar.YEAR, year);
        calendarNew.set(Calendar.MONTH, month - 1);
        LogUtils.i(TAG, "根据时间：" + calendarNew.getTime().toLocaleString() + "就是月份");

        int maximum = calendarNew.getActualMaximum(Calendar.DAY_OF_MONTH);
        int start = 1;
        int count = 0;
        int dayBetween = endCalendar.get(Calendar.DAY_OF_MONTH) - startCalendar.get(Calendar.DAY_OF_MONTH);

        if (CalendarHelper.isYearMonthSame(startCalendar, endCalendar)) {
            count = dayBetween + 1;
            start = startCalendar.get(Calendar.DAY_OF_MONTH);
        } else {
            if (CalendarHelper.isYearMonthSame(startCalendar, calendarNew)) {
                int dayOfMonth = startCalendar.get(Calendar.DAY_OF_MONTH);
                count = maximum - dayOfMonth + 1;
                start = dayOfMonth;
            } else if (CalendarHelper.isYearMonthSame(endCalendar, calendarNew)) {
                count = endCalendar.get(Calendar.DAY_OF_MONTH);
                start = 1;
            } else {
                count = maximum;
                start = 1;
            }
        }
        // 初始化日列表
        mDays = new int[count];
        mDayStrs = new String[count];
        for (int i = 0; i < count; i++) {
            int index = start + i;
            mDays[i] = index;
            if (index < 10) {
                mDayStrs[i] = /*"0" +*/ index + "日";
            } else {
                mDayStrs[i] = index + "" + "日";
            }

            if (state == STATE_TODAY) {
                calendarNew.set(Calendar.DAY_OF_MONTH, index);
                calendarNew.set(Calendar.HOUR_OF_DAY, 0);

                Calendar today = Calendar.getInstance();
                int daysBetween = CalendarHelper.daysBetween(calendarNew, today);
                if (daysBetween == 0) {
                    mDayStrs[i] += "（今天）";
                } else if (daysBetween == 1) {
                    mDayStrs[i] += "（昨天）";
                } else if (daysBetween == 2) {
                    mDayStrs[i] += "（前天）";
                } else if (daysBetween == 3) {
                    mDayStrs[i] += "";
                }
            }
        }

        // 设置日
        wheelDay.setData(mDayStrs);
        int dayPosition = getCurrentPosition(mDay, mDays);
        wheelDay.setSelect(dayPosition);
        wheelDay.setOnWheelItemSelectListener(new WheelTextView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(WheelAdapterView<?> parent, View view, int position, long id) {
                if (mDays != null && mDays.length > 0) {
                    mDay = mDays[position];
                }
            }
        });
        wheelDay.setOnEndFlingListener(new WheelGallery.OnEndFlingListener() {
            @Override
            public void onEndFling(WheelGallery v) {
                onDialogResult.onScrollFinish(getCalendar());
            }
        });

    }

    private int getCurrentPosition(int i, int[] array) {
        for (int j = 0; j < array.length; j++) {
            if (i == array[j]) {
                return j;
            }
        }
        return array.length / 2;
    }

    /**
     * Dialog结果回调
     */
    public interface OnDialogResult {
        void onSelectedResult(boolean bOk, Calendar calendar);

        void onScrollFinish(Calendar calendar);
    }

}