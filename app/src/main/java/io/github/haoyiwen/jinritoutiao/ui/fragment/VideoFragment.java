package io.github.haoyiwen.jinritoutiao.ui.fragment;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewbinding.ViewBinding;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.base.BaseFragment;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.constants.Constant;
import io.github.haoyiwen.jinritoutiao.databinding.FragmentVideoBinding;
import io.github.haoyiwen.jinritoutiao.model.entity.Channel;
import io.github.haoyiwen.jinritoutiao.ui.adapter.ChannelPagerAdapter;
import io.github.haoyiwen.uikit.UIUtils;
import me.weyye.library.colortrackview.ColorTrackTabLayout;

public class VideoFragment extends BaseFragment<BasePresenter, FragmentVideoBinding> {
    TabLayout mTabChannel;

    ImageView mIvOperation;

    ViewPager2 mVpContent;

    private List<Channel> mChannelList = new ArrayList<>();

    private List<NewsListFragment> mFragmentList = new ArrayList<>();

    @Override
    public void initBinding() {
        mTabChannel = binding.tabChannel;
        mIvOperation = binding.ivOperation;
        mVpContent = binding.vpContent;
    }

    @Override
    protected FragmentVideoBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentVideoBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initView(View rootView) {
        mIvOperation.setImageResource(R.mipmap.search_channel);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initData() {
        initChannelData();
        initChannelFragment();
    }

    private void initChannelFragment() {
        for (Channel channel : mChannelList) {
            NewsListFragment newsListFragment = new NewsListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.CHANNEL_CODE, channel.channelCode);
            bundle.putBoolean(Constant.IS_VIDEO_LIST, true);
            newsListFragment.setArguments(bundle);
            mFragmentList.add(newsListFragment);
        }
    }

    private void initChannelData() {
        String[] channels = getResources().getStringArray(R.array.channel_video);
        String[] channelCodes = getResources().getStringArray(R.array.channel_code_video);
        for (int i = 0; i < channelCodes.length; i++) {
            String title = channels[i];
            String code = channelCodes[i];
            mChannelList.add(new Channel(title, code));
        }
    }

    @Override
    public void initListener() {
        ChannelPagerAdapter channelPagerAdapter = new ChannelPagerAdapter(mFragmentList, mChannelList, this.getActivity());
        mVpContent.setAdapter(channelPagerAdapter);
        mVpContent.setOffscreenPageLimit(mFragmentList.size());

        new TabLayoutMediator(mTabChannel, mVpContent, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(TabLayout.Tab tab, int position) {
                tab.setText(mChannelList.get(position).title);
            }
        }).attach();

        mTabChannel.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup slidingTabStrip = (ViewGroup) mTabChannel.getChildAt(0);
                slidingTabStrip.setMinimumWidth(slidingTabStrip.getMeasuredWidth() + mIvOperation.getMeasuredWidth());
            }
        });

        mVpContent.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
    }

    public String getCurrentChannelCode() {
        int currentItem = mVpContent.getCurrentItem();
        return mChannelList.get(currentItem).channelCode;
    }

    @Override
    protected void loadData() {
        Logger.e("loadData");
    }
}
