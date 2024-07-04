package io.github.haoyiwen.jinritoutiao.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.base.BaseActivity;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.databinding.ActivitySplashBinding;
import io.github.haoyiwen.jinritoutiao.utils.UIUtils;
import io.github.haoyiwen.uikit.statusbar.Eyes;

public class SplashActivity extends BaseActivity {

    @Override
    public void initView() {
        Eyes.translucentStatusBar(this, false);

        UIUtils.postTaskDelay(new Runnable() {

            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public boolean enableSlideClose() {
        return false;
    }

    @Override
    protected Class getBindingClass() {
        return ActivitySplashBinding.class;
    }
}