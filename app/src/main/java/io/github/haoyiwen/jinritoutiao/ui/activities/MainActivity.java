package io.github.haoyiwen.jinritoutiao.ui.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

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
import io.github.haoyiwen.uikit.statusbar.Eyes;

public class MainActivity extends BaseActivity {
    @Override
    protected Class getBindingClass() {
        return ActivityMainBinding.class;
    }

    private static String[] mTitles = new String[]{
            "首页",
            "视频",
            "微头条",
            "我的"
    };

    private static int[] mImages = new int[]{
            R.mipmap.tab_home_normal,
            R.mipmap.tab_video_normal,
            R.mipmap.tab_micro_normal,
            R.mipmap.tab_me_normal
    };

    ViewPager2 mVpContent;

    TabLayout mBottomBarLayout;

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
        mTabAdapter = new MainTabAdapter(mFragments, this);
        mVpContent.setAdapter(mTabAdapter);
        mVpContent.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
        });
        mVpContent.setOffscreenPageLimit(mFragments.size());
        mVpContent.setUserInputEnabled(false);

        new TabLayoutMediator(mBottomBarLayout, mVpContent, (tab, position) -> {
            tab.setText(mTitles[position]);
            View tabView = LayoutInflater.from(this).inflate(R.layout.item_bottom_bar, null);
            TextView tabTitle = tabView.findViewById(R.id.tv_text);
            ImageView imageView = tabView.findViewById(R.id.iv_icon);
            tabTitle.setText(mTitles[position]);
            imageView.setImageResource(mImages[position]);
            tab.setCustomView(tabView);
        }).attach();

    }

    private void setStatusBarColor(int position) {
        if (position == 3) {
            Eyes.translucentStatusBar(MainActivity.this, true);
        } else {
            Eyes.setStatusBarColor(MainActivity.this, UIUtils.getColor(mStatusColors[position]));
        }
    }


}