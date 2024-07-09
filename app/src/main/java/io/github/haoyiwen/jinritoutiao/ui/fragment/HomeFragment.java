package io.github.haoyiwen.jinritoutiao.ui.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.base.BaseFragment;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.databinding.FragmentHomeBinding;
import io.github.haoyiwen.jinritoutiao.listener.OnChannelListener;
import io.github.haoyiwen.jinritoutiao.model.entity.Channel;
import me.weyye.library.colortrackview.ColorTrackTabLayout;

public class HomeFragment extends BaseFragment<BasePresenter, FragmentHomeBinding> implements OnChannelListener {

    ColorTrackTabLayout mTabChnanel;

    ImageView ivAddChannel;

    ViewPager mVpContent;

    private List<Channel> mSelectedChannels = new ArrayList<>();

    private List<Channel> mUnSelectedChannels = new ArrayList<>();

    private List<NewsListFragment> mChannelFragments = new ArrayList<>();

    private Gson mGson = new Gson();

//    private ChannelPagerAdapter mChannelPagerAdapter;

    private String[] mChannelCodes;

    @Override
    protected FragmentHomeBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHomeBinding.inflate(inflater, container, false);
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
