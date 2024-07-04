package io.github.haoyiwen.uikit.statusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.view.ViewCompat;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class EyesKitKat {
    private static final String TAG_FAKE_STATUS_BAR_VIEW = "statusBarView";
    private static final String TAG_MARGIN_ADDED = "marginAdded";
    static void translucentStatusBar(Activity activity){
        Window window = activity.getWindow();
        //设置透明
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = (ViewGroup) mContentView.getChildAt(0);

        removeFakeStatusBarViewIfExist(activity);

        removeMarginTopOfContentChild(mChildView, getStatusBarHeight(activity));

        if(mChildView != null){
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }
    }

    private static int getStatusBarHeight(Context context){
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resId > 0){
            result = context.getResources().getDimensionPixelSize(resId);
        }
        return result;
    }

    private static void removeFakeStatusBarViewIfExist(Activity activity){
        Window window = activity.getWindow();
        ViewGroup mDecorView = (ViewGroup) window.getDecorView();

        View fakeView = mDecorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
        if (fakeView != null) {
            mDecorView.removeView(fakeView);
        }
    }

    private static void removeMarginTopOfContentChild(View mContentChild, int statusBarHeight){
        if(mContentChild == null){
            return;
        }
        if(TAG_MARGIN_ADDED.equals(mContentChild.getTag())){
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mContentChild.getLayoutParams();
            lp.topMargin -= statusBarHeight;
            mContentChild.setLayoutParams(lp);
        }
    }
}
