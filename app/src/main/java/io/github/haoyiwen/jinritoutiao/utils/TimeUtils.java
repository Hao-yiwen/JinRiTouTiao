package io.github.haoyiwen.jinritoutiao.utils;

public class TimeUtils {
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
