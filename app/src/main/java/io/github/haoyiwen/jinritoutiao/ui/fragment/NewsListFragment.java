package io.github.haoyiwen.jinritoutiao.ui.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;

import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.github.haoyiwen.jinritoutiao.base.BaseFragment;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.databinding.FragmentNewsListBinding;
import io.github.haoyiwen.jinritoutiao.model.entity.NewRecord;
import io.github.haoyiwen.jinritoutiao.model.entity.News;
import io.github.haoyiwen.jinritoutiao.presenter.NewListPresenter;
import io.github.haoyiwen.jinritoutiao.presenter.view.INewsListView;
import io.github.haoyiwen.uikit.TipView;
import io.github.haoyiwen.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import io.github.haoyiwen.uikit.refreshlayout.BGARefreshLayout;

public class NewsListFragment extends BaseFragment<NewListPresenter, FragmentNewsListBinding> implements INewsListView,BaseQuickAdapter.RequestLoadMoreListener{
    public static final String TAG = NewsListFragment.class.getSimpleName();

    TipView mTipView;

    BGARefreshLayout mRefreshLayout;

    FrameLayout mFlContent;

    PowerfulRecyclerView mRvNewsList;

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

    @Override
    protected FragmentNewsListBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentNewsListBinding.inflate(inflater, container, false);
    }

    @Override
    protected NewListPresenter createPresenter() {
        return new NewListPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return 0;
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
}
