package io.github.haoyiwen.jinritoutiao.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.security.PublicKey;

import io.github.haoyiwen.jinritoutiao.app.TouTiaoApp;

public class UIUtils {

    public static Context getContext(){
        return TouTiaoApp.getmContext();
    }
    public static void hideInput(View view){
        InputMethodManager imm = (InputMethodManager) UIUtils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
