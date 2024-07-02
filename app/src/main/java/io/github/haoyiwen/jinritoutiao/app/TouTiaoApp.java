package io.github.haoyiwen.jinritoutiao.app;

import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.litepal.LitePal;

import io.github.haoyiwen.jinritoutiao.app.base.BaseApp;

public class TouTiaoApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();

        /***************************************相关第三方SDK的初始化等操作**************************************************/
        // 初始化logger
        Logger.addLogAdapter(new AndroidLogAdapter());
        // 初始化litepal
        LitePal.initialize(this);
        // 初始化视察返回: ParallaxHelper
        registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());
    }
}
