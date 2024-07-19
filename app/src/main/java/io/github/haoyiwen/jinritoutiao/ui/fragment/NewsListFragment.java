package io.github.haoyiwen.jinritoutiao.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.base.BaseFragment;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.constants.Constant;
import io.github.haoyiwen.jinritoutiao.databinding.FragmentNewsListBinding;
import io.github.haoyiwen.jinritoutiao.model.entity.NewRecord;
import io.github.haoyiwen.jinritoutiao.model.entity.News;
import io.github.haoyiwen.jinritoutiao.presenter.NewListPresenter;
import io.github.haoyiwen.jinritoutiao.presenter.view.INewsListView;
import io.github.haoyiwen.jinritoutiao.ui.adapter.NewsListAdapter;
import io.github.haoyiwen.jinritoutiao.ui.adapter.VideoListAdapter;
import io.github.haoyiwen.jinritoutiao.utils.UIUtils;
import io.github.haoyiwen.uikit.TipView;
import io.github.haoyiwen.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import io.github.haoyiwen.uikit.refreshlayout.BGARefreshLayout;
import io.github.haoyiwen.uikit.refreshlayout.BgaNormalRefreshViewHolder;

public class NewsListFragment extends BaseFragment<NewListPresenter, FragmentNewsListBinding> implements INewsListView, BaseQuickAdapter.RequestLoadMoreListener, BGARefreshLayout.BGARefreshLayoutDelegate {
    public static final String TAG = NewsListFragment.class.getSimpleName();

    TipView mTipView;

    BGARefreshLayout mRefreshLayout;

    FrameLayout mFlContent;

    PowerfulRecyclerView mRvNews;

    private String mChnnelCode;

    private boolean isValidList;

    private boolean isRecommendChannel;

    private List<News> mNewsList = new ArrayList<>();

    protected BaseQuickAdapter mNewsAdapter;

    private boolean isClickTabRefreshing;

    private RotateAnimation mRotateAnimation;

    private Gson mGson = new Gson();

    private NewRecord mNewRecord;

    // 用于标记是否是首页底部刷新，如果记在成功后发送完成的事件
    private boolean isHomeTabRefresh;

    private boolean isVideoList;

    @Override
    protected FragmentNewsListBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentNewsListBinding.inflate(inflater, container, false);
    }

    @Override
    public void initBinding() {
        // 绑定binding
        mTipView = binding.tipView;
        mRefreshLayout = binding.refreshLayout;
        mFlContent = binding.flContent;
        mRvNews = binding.rvNews;
    }

    @Override
    protected NewListPresenter createPresenter() {
        return new NewListPresenter(this);
    }

    @Override
    protected View getStateViewRoot() {
        return mFlContent;
    }


    @Override
    protected void initView(View rootView) {
        mRefreshLayout.setDelegate(this);
        mRvNews.setLayoutManager(new GridLayoutManager(mActivity, 1));
        // 设置下拉刷新和上拉加载更多的风格
        BgaNormalRefreshViewHolder refreshViewHolder = new BgaNormalRefreshViewHolder(mActivity, false);
        // 设置下拉刷新
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.color_F3F5F4);
        refreshViewHolder.setPullDownRefreshText(UIUtils.getString(R.string.refresh_pull_down_text));
        refreshViewHolder.setReleaseRefreshText(UIUtils.getString(R.string.refresh_release_text));
        refreshViewHolder.setRefreshingText(UIUtils.getString(R.string.refresh_ing_text));

        // 设置下拉刷新和上拉记在更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.shouldHandleRecyclerViewLoadingMore(mRvNews);
    }

    @Override
    public void initData() {
        mChnnelCode = getArguments().getString(Constant.CHANNEL_CODE);
        isVideoList = getArguments().getBoolean(Constant.IS_VIDEO_LIST, false);
        String[] channelCodes = UIUtils.getStringArr(R.array.channel_code);
        isRecommendChannel = mChnnelCode.equals(channelCodes[0]);
    }

    @Override
    public void initListener() {
        if(isVideoList){
            mNewsAdapter = new VideoListAdapter(mNewsList);
        } else {
            mNewsAdapter = new NewsListAdapter(mChnnelCode, mNewsList);
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onGetNewsListSuccess(List<News> newsList, String tipInfo) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
