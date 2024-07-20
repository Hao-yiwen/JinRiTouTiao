package io.github.haoyiwen.jinritoutiao.utils;

import android.text.format.DateFormat;
import android.text.format.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    public static final long ONE_MINUTE_MILLIONS = 60 * 1000;

    public static final long ONE_HOUR_MILLIONS = 60 * ONE_MINUTE_MILLIONS;

    public static final long ONE_DAY_MILLIONS = 24 * ONE_HOUR_MILLIONS;

    public static String getShortTime(long millis) {
        Date date = new Date(millis);
        Date curDate = new Date();

        String str = "";
        long durTime = curDate.getTime() - date.getTime();

        int dataStatus = calculateDayStatus(date, new Date());

        if (durTime <= 10 * ONE_MINUTE_MILLIONS) {
            str = "刚刚";
        } else if (durTime < ONE_HOUR_MILLIONS) {
            str = durTime / ONE_MINUTE_MILLIONS + "分钟前";
        } else if (dataStatus == 0) {
            str = durTime / ONE_HOUR_MILLIONS + "小时前";
        } else if (dataStatus == -1) {
            str = "昨天" + DateFormat.format("HH:mm", date);
        } else if (isSameYear(date, curDate) && dataStatus < -1) {
            str = DateFormat.format("MM-dd", date).toString();
        } else {
            str = DateFormat.format("yyyy-MM", date).toString();
        }
        return str;
    }

    private static boolean isSameYear(Date targetTime, Date compareTime) {
        Calendar curCalendar = Calendar.getInstance();
        curCalendar.setTime(targetTime);
        int curYear = curCalendar.get(Calendar.YEAR);

        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int compareYear = compareCalendar.get(Calendar.YEAR);
        return curYear == compareYear;
    }

    private static int calculateDayStatus(Date targetTime, Date compareTime) {
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarDayOfYear = tarCalendar.get(Calendar.DAY_OF_YEAR);


        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int compareOfYear = compareCalendar.get(Calendar.DAY_OF_YEAR);
        return tarDayOfYear - compareOfYear;
    }

    // 将秒转化为00:00
    // time
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int min = 0;
        int second = 0;
        if (time <= 0) {
            return "00:00";
        } else {
            min = time / 60;
            if (min < 60) {
                second = time % 60;
                timeStr = unitFormat(min) + ":" + unitFormat(second);
            } else {
                hour = min / 60;
                if (hour > 99) {
                    return "99:59:59";
                }
                min = min % 60;
                second = time - hour * 3600 - min * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(min) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    private static String unitFormat(int second) {
        int i = second;
        String resStr = null;
        if (i >= 0 && i < 10) {
            resStr = "0" + Integer.toString(i);
        } else {
            resStr = "" + i;
        }
        return resStr;
    }
}
