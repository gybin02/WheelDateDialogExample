package com.seeker.datedialog.widget.util;

import java.util.Calendar;

/**
 * @author zhengxiaobin <gybin02@Gmail.com>
 * @since 2016/7/12
 */
public class CalendarHelper {

    /**
     * End - Star 两个日期之间的天数<br>
     *
     * @param start
     * @param end
     * @return end 在 start之后 ，大于0  <br>
     * end 在 start之前，小于0
     */
    public static int get2CalendarDaysBetween(Calendar start, Calendar end) {

        start = (start == null) ? Calendar.getInstance() : start;
        end = (end == null) ? Calendar.getInstance() : end;

        if (end.get(Calendar.YEAR) != start.get(Calendar.YEAR)) {
            Calendar startTag = (Calendar) start.clone();
            startTag.set(Calendar.HOUR_OF_DAY, end.get(Calendar.HOUR_OF_DAY));
            startTag.set(Calendar.MINUTE, end.get(Calendar.MINUTE));
            startTag.set(Calendar.SECOND, end.get(Calendar.SECOND));
            float second = (end.getTimeInMillis() - startTag
                    .getTimeInMillis()) / 1000;
            float res = second / (24 * 60 * 60);
            return Math.round(res);
        } else {
            return end.get(Calendar.DAY_OF_YEAR)
                    - start.get(Calendar.DAY_OF_YEAR);
        }
    }

    /**
     * End - Star 两个日期之间的天数<br>
     *
     * @param start
     * @param end
     * @return 可能小于0;
     */
    public static int daysBetween(Calendar start, Calendar end) {
        //只有 时间一致，相减才能得出正确的天数
        Calendar startTag = (Calendar) start.clone();
        startTag.set(Calendar.HOUR_OF_DAY, end.get(Calendar.HOUR_OF_DAY));
        startTag.set(Calendar.MINUTE, end.get(Calendar.MINUTE));
        startTag.set(Calendar.SECOND, end.get(Calendar.SECOND));
        startTag.set(Calendar.MILLISECOND, end.get(Calendar.MILLISECOND));
        long timeStart = startTag.getTimeInMillis();
        long timeEnd = end.getTimeInMillis();
        long days = (timeEnd - timeStart) / (1000 * 3600 * 24);
        return Math.round(days);
    }

    /**
     * 日期date是否在start ，End 之间
     *
     * @param start
     * @param end
     * @param date
     * @return
     */
    public static boolean isBetween(Calendar start, Calendar end, Calendar date) {
        return get2CalendarDaysBetween(start, date) >= 0 && get2CalendarDaysBetween(end, date) <= 0;
    }


    /**
     * 是否是同一个月
     *
     * @param calendar
     * @param calendar2
     * @return
     */
    public static boolean isYearMonthSame(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            return false;
        }
        if (calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) {
            if (calendar.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是同一个月
     *
     * @param calendar
     * @param calendar2
     * @return
     */
    public static boolean isYearSame(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            return false;
        }
        if (calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) {
            return true;
        }
        return false;
    }

    public static boolean isSameOnlyDay(Calendar start, Calendar end) {
        return start.get(Calendar.DAY_OF_MONTH) == end.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 是否是同一天
     *
     * @param calendar
     * @param calendar2
     * @return
     */
    public static boolean isYearMonthDaySame(Calendar calendar, Calendar calendar2) {
        try {

            if (calendar == null || calendar2 == null) {
                return false;
            }
            if (calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) {
                if (calendar.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)) {
                    if (calendar.get(Calendar.DAY_OF_MONTH) == calendar2
                            .get(Calendar.DAY_OF_MONTH)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
