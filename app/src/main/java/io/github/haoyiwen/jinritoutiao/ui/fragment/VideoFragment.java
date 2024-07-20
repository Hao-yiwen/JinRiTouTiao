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
    ColorTrackTabLayout mTabChannel;

    ImageView mIvOperation;

    ViewPager mVpContent;

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
        ChannelPagerAdapter channelPagerAdapter = new ChannelPagerAdapter(mFragmentList, mChannelList, getChildFragmentManager());
        mVpContent.setAdapter(channelPagerAdapter);
        mVpContent.setOffscreenPageLimit(mFragmentList.size());

        mTabChannel.setTabPaddingLeftAndRight(UIUtils.dip2Px(mActivity, 10), UIUtils.dip2Px(mActivity, 10));
        mTabChannel.setupWithViewPager(mVpContent);

        mTabChannel.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup slidingTabStrip = (ViewGroup) mTabChannel.getChildAt(0);
                slidingTabStrip.setMinimumWidth(slidingTabStrip.getMeasuredWidth() + mIvOperation.getMeasuredWidth());
            }
        });
        mTabChannel.setSelectedTabIndicatorHeight(0);

        mVpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Jzvd.releaseAllVideos();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
