package io.github.haoyiwen.uikit.statusbar;

import android.app.Activity;
import android.os.Build;

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
}
