package io.github.haoyiwen.jinritoutiao.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.base.BaseFragment;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.constants.Constant;
import io.github.haoyiwen.jinritoutiao.databinding.FragmentHomeBinding;
import io.github.haoyiwen.jinritoutiao.listener.OnChannelListener;
import io.github.haoyiwen.jinritoutiao.model.entity.Channel;
import io.github.haoyiwen.jinritoutiao.ui.adapter.ChannelPagerAdapter;
import io.github.haoyiwen.jinritoutiao.utils.PreUtils;
import me.weyye.library.colortrackview.ColorTrackTabLayout;

public class HomeFragment extends BaseFragment<BasePresenter, FragmentHomeBinding> implements OnChannelListener {

    ColorTrackTabLayout mTabChnanel;

    ImageView ivAddChannel;

    ViewPager mVpContent;

    private List<Channel> mSelectedChannels = new ArrayList<>();

    private List<Channel> mUnSelectedChannels = new ArrayList<>();

    private List<NewsListFragment> mChannelFragments = new ArrayList<>();

    private Gson mGson = new Gson();

    private ChannelPagerAdapter mChannelPagerAdapter;

    private String[] mChannelCodes;

    @Override
    protected FragmentHomeBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHomeBinding.inflate(inflater, container, false);
    }

    @Override
    public void initData() {
        initChannelData();
        initChannelFragments();
    }

    @Override
    protected void initView(View rootView) {
        mVpContent = binding.vpContent;
        ivAddChannel = binding.ivOperation;
        mTabChnanel = binding.tabChannel;
    }

    /**
     * 初始化已选频道的fragment的集合
     */
    private void initChannelFragments() {
        Logger.d("initChannelFragments");
        mChannelCodes = getResources().getStringArray(R.array.channel_code);
        for (Channel channel : mSelectedChannels) {
            NewsListFragment newsListFragment = new NewsListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.CHANNEL_CODE, channel.channelCode);
            bundle.putBoolean(Constant.IS_VIDEO_LIST, channel.channelCode.equals(mChannelCodes[1]));
            newsListFragment.setArguments(bundle);
            mChannelFragments.add(newsListFragment);
        }
    }

    private void initChannelData() {
        String selectedChannelJson = PreUtils.getString(Constant.SELECTED_CHANNEL_JSON, "");
        String unselectChannelJson = PreUtils.getString(Constant.UNSELECTED_CHANNEL_JSON, "");

        if (TextUtils.isEmpty(selectedChannelJson) || TextUtils.isEmpty(unselectChannelJson)) {
            String[] channels = getResources().getStringArray(R.array.channel);
            String[] channelCodes = getResources().getStringArray(R.array.channel_code);

            //默认添加全部频道
            for (int i = 0; i < channelCodes.length; i++) {
                String title = channels[i];
                String code = channelCodes[i];
                mSelectedChannels.add(new Channel(title, code));
            }
            selectedChannelJson = mGson.toJson(mSelectedChannels);
            Logger.d("selectedChannelJson:" + selectedChannelJson);
            PreUtils.putString(Constant.SELECTED_CHANNEL_JSON, selectedChannelJson);
        } else {
            List<Channel> selectedChannel = mGson.fromJson(selectedChannelJson, new TypeToken<List<Channel>>() {
            }.getType());
            List<Channel> unselectedChannel = mGson.fromJson(unselectChannelJson, new TypeToken<List<Channel>>() {
            }.getType());
            mSelectedChannels.addAll(selectedChannel);
            mUnSelectedChannels.addAll(unselectedChannel);
        }
    }

    @Override
    public void initListener() {
        mChannelPagerAdapter = new ChannelPagerAdapter(mChannelFragments, mSelectedChannels, getChildFragmentManager());
        mVpContent.setAdapter(mChannelPagerAdapter);
        mVpContent.setOffscreenPageLimit(mSelectedChannels.size());

        mTabChnanel.setupWithViewPager(mVpContent);
        mTabChnanel.setSelectedTabIndicatorHeight(0);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_home;
    }


    @Override
    protected void loadData() {

    }

    @Override
    public void onItemMove(int starPos, int endPos) {

    }

    @Override
    public void onMoveToMyChannel(int starPos, int endPos) {

    }

    @Override
    public void onMoveToOtherChannel(int starPos, int endPos) {

    }
}
