package io.github.haoyiwen.uikit.utils;

import android.content.Context;

public class UIUtils {

    public UIUtils() {
    }

    public static int dip2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        int px = (int) (dpValue * density + 0.5f);
        return px;
    }

    public static int sp2px(Context context, float pxValue) {
        float density = context.getResources().getDisplayMetrics().scaledDensity;
        int dp = (int) (pxValue * density + 0.5f);
        return dp;
    }
}
