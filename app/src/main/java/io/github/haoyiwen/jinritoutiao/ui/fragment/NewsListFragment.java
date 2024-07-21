package io.github.haoyiwen.jinritoutiao.ui.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.base.BaseFragment;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.constants.Constant;
import io.github.haoyiwen.jinritoutiao.databinding.FragmentNewsListBinding;
import io.github.haoyiwen.jinritoutiao.model.entity.NewRecord;
import io.github.haoyiwen.jinritoutiao.model.entity.News;
import io.github.haoyiwen.jinritoutiao.model.entity.VideoEntity;
import io.github.haoyiwen.jinritoutiao.model.event.DetailCloseEvent;
import io.github.haoyiwen.jinritoutiao.model.event.TabRefreshCompletedEvent;
import io.github.haoyiwen.jinritoutiao.model.event.TabRefreshEvent;
import io.github.haoyiwen.jinritoutiao.presenter.NewListPresenter;
import io.github.haoyiwen.jinritoutiao.presenter.view.INewsListView;
import io.github.haoyiwen.jinritoutiao.ui.activities.NewsDetailBaseActivity;
import io.github.haoyiwen.jinritoutiao.ui.activities.VideoDetailActivity;
import io.github.haoyiwen.jinritoutiao.ui.adapter.NewsListAdapter;
import io.github.haoyiwen.jinritoutiao.ui.adapter.VideoListAdapter;
import io.github.haoyiwen.jinritoutiao.utils.ListUitis;
import io.github.haoyiwen.jinritoutiao.utils.NetWorkUtils;
import io.github.haoyiwen.jinritoutiao.utils.NewsRecordHelper;
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
        if (isVideoList) {
            mNewsAdapter = new VideoListAdapter(mNewsList);
        } else {
            mNewsAdapter = new NewsListAdapter(mChnnelCode, mNewsList);
        }
        mRvNews.setAdapter(mNewsAdapter);

        mNewsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                News news = mNewsList.get(position);

                String itemId = news.item_id;
                StringBuffer urlSb = new StringBuffer("http://m.toutiao.com/i");
                urlSb.append(itemId).append("/info/");
                String url = urlSb.toString();
                Intent intent = null;
                if (news.has_video) {
                    intent = new Intent(mActivity, VideoDetailActivity.class);
                    VideoEntity videoDetailInfo = news.video_detail_info;
                    String videoUrl = "";
                    if (videoDetailInfo != null && !TextUtils.isEmpty(videoDetailInfo.parse_video_url)) {
                        videoUrl = videoDetailInfo.parse_video_url;
                    }
                    intent.putExtra(VideoDetailActivity.VIDEO_URL, videoUrl);
                }
                intent.putExtra(NewsDetailBaseActivity.CHANNEL_CODE, mChnnelCode);
                intent.putExtra(NewsDetailBaseActivity.POSITION, position);

                intent.putExtra(NewsDetailBaseActivity.DETAIL_URL, url);
                intent.putExtra(NewsDetailBaseActivity.GROUP_ID, news.group_id);
                intent.putExtra(NewsDetailBaseActivity.ITEM_ID, itemId);

                startActivity(intent);
            }
        });

        mNewsAdapter.setEnableLoadMore(true);
        mNewsAdapter.setOnLoadMoreListener(this, mRvNews);

        if (isVideoList) {
            mRvNews.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(@NonNull View view) {

                }

                @Override
                public void onChildViewDetachedFromWindow(@NonNull View view) {

                }
            });
        }
    }

    @Override
    protected void loadData() {
        mStateView.showLoading();

        mNewRecord = NewsRecordHelper.getLastNewsRecord(mChnnelCode);

        if (mNewRecord == null) {
            mNewRecord = new NewRecord();
            mPresenter.getNewsList(mChnnelCode);
            return;
        }

        List<News> newsList = NewsRecordHelper.convertToNewsList(mNewRecord.getJson());
        mNewsList.addAll(newsList);
        mNewsAdapter.notifyDataSetChanged();

        mStateView.showContent();
        if (mNewRecord.getTime() - System.currentTimeMillis() == 10 * 60 * 100) {
            mRefreshLayout.beginRefreshing();
        }
    }

    @Override
    public void onGetNewsListSuccess(List<News> newsList, String tipInfo) {
        mRefreshLayout.endRefreshing();
        if (isHomeTabRefresh) {
            postRefreshCompletedEvent();
        }

        // 如果是第一次数据
        if (ListUitis.isEmpty(mNewsList)) {
            if (ListUitis.isEmpty(newsList)) {
                mStateView.showEmpty();
                return;
            }
            mStateView.showContent();
        }

        if (ListUitis.isEmpty(newsList)) {
            UIUtils.showToast(UIUtils.getString(R.string.no_news_now));
            return;
        }

        if (TextUtils.isEmpty(newsList.get(0).title)) {
            newsList.remove(0);
        }

        dealRepeat(newsList);

        mNewsList.addAll(0, newsList);

        mNewsAdapter.notifyDataSetChanged();

        mTipView.show(tipInfo);
        NewsRecordHelper.save(mChnnelCode, mGson.toJson(newsList));
    }

    private void dealRepeat(List<News> newsList) {
        if (isRecommendChannel && !ListUitis.isEmpty(mNewsList)) {
            mNewsList.remove(0);
            if (newsList.size() > 4) {
                News fourthNews = newsList.get(3);
                if (fourthNews.tag != null && fourthNews.tag.equals(Constant.ARTICLE_GENRE_AD)) {
                    newsList.remove(fourthNews);
                }
            }
        }
    }

    private void postRefreshCompletedEvent() {
        if (isClickTabRefreshing) {
            EventBus.getDefault().post(new TabRefreshCompletedEvent());
            isClickTabRefreshing = false;
        }
    }

    @Override
    public void onError() {
        mTipView.show();

        if (ListUitis.isEmpty(mNewsList)) {
            mStateView.showRetry();
        }

        if (mRefreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING) {
            mRefreshLayout.endRefreshing();
        }
        postRefreshCompletedEvent();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mNewRecord.getPage() == 0 || mNewRecord.getPage() == 1) {
            mNewsAdapter.loadMoreEnd();
            return;
        }

        NewRecord preNewsRecord = NewsRecordHelper.getPreNewsRecord(mChnnelCode, mNewRecord.getPage());
        if (preNewsRecord == null) {
            mNewsAdapter.loadMoreEnd();
            return;
        }

        mNewRecord = preNewsRecord;

        long startTime = System.currentTimeMillis();
        List<News> newsList = NewsRecordHelper.convertToNewsList(mNewRecord.getJson());

        if (isRecommendChannel) {
            newsList.remove(0);
        }

        Logger.e(newsList.toString());

        long endTime = System.currentTimeMillis();
        if (endTime - startTime <= 1000) {
            UIUtils.postTaskDelay(new Runnable() {
                @Override
                public void run() {
                    mNewsAdapter.loadMoreComplete();
                    mNewsList.addAll(newsList);
                    mNewsAdapter.notifyDataSetChanged();
                }
            }, (int) (1000 - (endTime - startTime)));
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (!NetWorkUtils.isNetworkAvailable(mActivity)) {
            mTipView.show();
            if (mRefreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING) {
                mRefreshLayout.endRefreshing();
            }
            return;
        }
        mPresenter.getNewsList(mChnnelCode);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerEventBus(NewsListFragment.this);
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterEventBus(NewsListFragment.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.e("onDestory" + mChnnelCode);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(TabRefreshEvent event) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onDetailCloseEvent(DetailCloseEvent event) {

    }
}
