package io.github.haoyiwen.jinritoutiao.ui.activities;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.base.BaseActivity;
import io.github.haoyiwen.jinritoutiao.base.BaseFragment;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.databinding.ActivityMainBinding;
import io.github.haoyiwen.jinritoutiao.ui.adapter.MainTabAdapter;
import io.github.haoyiwen.jinritoutiao.ui.fragment.HomeFragment;
import io.github.haoyiwen.jinritoutiao.ui.fragment.MicroFragment;
import io.github.haoyiwen.jinritoutiao.ui.fragment.MineFragment;
import io.github.haoyiwen.jinritoutiao.ui.fragment.VideoFragment;
import io.github.haoyiwen.jinritoutiao.utils.UIUtils;
import io.github.haoyiwen.uikit.NoScrollViewPager;
import io.github.haoyiwen.uikit.bottombar.BottomBarLayout;
import io.github.haoyiwen.uikit.statusbar.Eyes;

public class MainActivity extends BaseActivity {
    @Override
    protected Class getBindingClass() {
        return ActivityMainBinding.class;
    }

    NoScrollViewPager mVpContent;

    BottomBarLayout mBottomBarLayout;

    private List<BaseFragment> mFragments;

    private MainTabAdapter mTabAdapter;

    private int[] mStatusColors = new int[]{
            R.color.color_D33D3C,
            R.color.color_BDBDBD,
            R.color.color_BDBDBD,
    };

    /**
     * @description: 20240711犯了个错，只要初始化fragment，必须要绑定binding。。。负责就是会报错。。这个问题让我找了一晚上原因。但是也阴差阳错研究了一下viewpager
     */
    @Override
    public void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new VideoFragment());
        mFragments.add(new MicroFragment());
        mFragments.add(new MineFragment());
    }

    @Override
    public void initView() {
        mVpContent = ((ActivityMainBinding) mBinding).vpContent;
        mBottomBarLayout = ((ActivityMainBinding) mBinding).bottomBar;
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
    public void initListener() {
        mTabAdapter = new MainTabAdapter(mFragments, getSupportFragmentManager());
        mVpContent.setAdapter(mTabAdapter);
        mVpContent.setOffscreenPageLimit(mFragments.size());
        try {
            mBottomBarLayout.setViewPager(mVpContent);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        mBottomBarLayout.setOnItemSelectedListener((v, i) -> {
            setStatusBarColor(i);//设置状态栏颜色
        });
    }

    private void setStatusBarColor(int position) {
        if(position == 3){
            Eyes.translucentStatusBar(MainActivity.this, true);
        } else {
            Eyes.setStatusBarColor(MainActivity.this, UIUtils.getColor(mStatusColors[position]));
        }
    }


}