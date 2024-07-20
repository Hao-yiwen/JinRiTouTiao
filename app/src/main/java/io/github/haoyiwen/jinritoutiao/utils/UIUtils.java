package io.github.haoyiwen.jinritoutiao.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.security.PublicKey;

import io.github.haoyiwen.jinritoutiao.app.TouTiaoApp;

public class UIUtils {
    public static Toast mToast;

    public static int getColor(int colorId) {
        return getResource().getColor(colorId, null);
    }

    private static Resources getResource() {
        return getContext().getResources();
    }

    public static Context getContext() {
        return TouTiaoApp.getmContext();
    }

    public static void hideInput(View view) {
        InputMethodManager imm = (InputMethodManager) UIUtils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void postTaskDelay(Runnable task, int delayMillis) {
        getMainThreadHandler().postDelayed(task, delayMillis);
    }

    public static void postTaskDelay(Runnable task) {
        int currentThreadId = android.os.Process.myTid();
        if (currentThreadId == TouTiaoApp.getmMainThreadId()) {
            task.run();
        } else {
            getMainThreadHandler().post(task);
        }
    }

    public static Handler getMainThreadHandler() {
        return TouTiaoApp.getmHandler();
    }

    public static String getString(int resId) {
        return getResource().getString(resId);
    }

    public static String[] getStringArr(int channelCode) {
        return getResource().getStringArray(channelCode);
    }

    public static void showToast(String toast) {
        showToast(toast, Toast.LENGTH_SHORT);
    }

    public static void showToast(String toast, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), "", duration);
        }
        mToast.setText(toast);
        mToast.show();
    }
}
