package io.github.haoyiwen.uikit.statusbar;

import android.app.Activity;
import android.os.Build;
import android.view.ViewGroup;
import android.view.Window;

public class Eyes {

    public static void translucentStatusBar(Activity activity) {
        translucentStatusBar(activity, false);
    }

    public static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            EyesLollipop.translucentStatusBar(activity, hideStatusBarBackground);
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            EyesKitKat.translucentStatusBar(activity);
        }
    }

    public static void setStatusBarColor(Activity activity, int statusColor) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            EyesLollipop.setStatusBarColor(activity, statusColor);
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            EyesKitKat.setStatusBarColor(activity, statusColor);
        }
    }

    public static void setContentTopPadding(Activity activity, int actionBarHeight) {
        ViewGroup mContentView = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        mContentView.setPadding(0, actionBarHeight, 0, 0);
    }
}
