package io.github.haoyiwen.jinritoutiao.ui.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.base.BaseActivity;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    @Override
    protected Class getBindingClass() {
        return ActivityMainBinding.class;
    }

    @Override
    public void initView() {
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public boolean enableSlideClose() {
        return false;
    }
}