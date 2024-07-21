package io.github.haoyiwen.jinritoutiao.ui.activities;

import android.content.Intent;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.nukc.stateview.StateView;

import org.greenrobot.eventbus.EventBus;

import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import cn.jzvd.JZDataSource;
import cn.jzvd.JZMediaInterface;
import cn.jzvd.JZMediaSystem;
import cn.jzvd.JZTextureView;
import cn.jzvd.JZUtils;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.base.BaseActivity;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.constants.Constant;
import io.github.haoyiwen.jinritoutiao.model.entity.CommentData;
import io.github.haoyiwen.jinritoutiao.model.event.DetailCloseEvent;
import io.github.haoyiwen.jinritoutiao.model.response.CommentResponse;
import io.github.haoyiwen.jinritoutiao.model.response.NewDetail;
import io.github.haoyiwen.jinritoutiao.presenter.NewsDetailPresenter;
import io.github.haoyiwen.jinritoutiao.presenter.view.INewsDetailView;
import io.github.haoyiwen.jinritoutiao.presenter.view.INewsListView;
import io.github.haoyiwen.jinritoutiao.ui.adapter.CommentAdapter;
import io.github.haoyiwen.jinritoutiao.ui.widget.NewsDetailHeaderView;
import io.github.haoyiwen.jinritoutiao.utils.ListUitis;
import io.github.haoyiwen.uikit.powerfulrecyclerview.PowerfulRecyclerView;

public abstract class NewsDetailBaseActivity<T extends ViewBinding> extends BaseActivity<NewsDetailPresenter, T> implements INewsDetailView, BaseQuickAdapter.RequestLoadMoreListener {
    public static final String CHANNEL_CODE = "channelCode";

    public static final String VIDEO_URL = "videoUrl";

    public static final String PROGRESS = "progress";

    public static final String POSITION = "position";

    public static final String DETAIL_URL = "detailUrl";

    public static final String GROUP_ID = "groupId";

    public static final String ITEM_ID = "itemId";

    FrameLayout mFlContent;

    PowerfulRecyclerView mRvComment;

    TextView mTvCommentCount;

    private List<CommentData> commentList = new ArrayList<>();

    protected StateView mStateView;

    private CommentAdapter mCommentAdapter;

    protected NewsDetailHeaderView mHeaderView;

    private String mDetailUrl;

    private String mGroupId;

    private String mItemId;

    protected CommentResponse mCommentResponse;

    protected String mChannelCode;

    protected int mPosition;


    @Override
    protected NewsDetailPresenter createPresenter() {
        return new NewsDetailPresenter(this);
    }

    @Override
    public void initView() {
        initBinding();
        mStateView = StateView.inject(mFlContent);
        mStateView.setLoadingResource(io.github.haoyiwen.uikit.R.layout.page_loading);
        mStateView.setRetryResource(io.github.haoyiwen.uikit.R.layout.page_net_error);
    }

    protected abstract void initBinding();

    @Override
    public void initData() {
        Intent intent = getIntent();

        mChannelCode = intent.getStringExtra(CHANNEL_CODE);
        mPosition = intent.getIntExtra(POSITION, 0);
        mDetailUrl = intent.getStringExtra(DETAIL_URL);
        mGroupId = intent.getStringExtra(GROUP_ID);
        mItemId = intent.getStringExtra(ITEM_ID);
        mItemId = mItemId.replace("i", "");

        mPresenter.getNewsDetail(mDetailUrl);
        loadCommentData();
    }

    private void loadCommentData() {
        mStateView.showLoading();
        mPresenter.getComment(mGroupId, mItemId, 1);
    }

    @Override
    public void initListener() {
        mCommentAdapter = new CommentAdapter(this, R.layout.item_comment, commentList);
        mRvComment.setAdapter(mCommentAdapter);

        mHeaderView = new NewsDetailHeaderView(this);
        mCommentAdapter.addHeaderView(mHeaderView);

        mCommentAdapter.setEnableLoadMore(true);
        mCommentAdapter.setOnLoadMoreListener(this, mRvComment);

        mCommentAdapter.setEmptyView(io.github.haoyiwen.uikit.R.layout.pager_no_comment);
        mCommentAdapter.setHeaderAndEmpty(true);


        mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                loadCommentData();
            }
        });
    }

    @Override
    public void onGetCommentSuccess(CommentResponse response) {
        mCommentResponse = response;

        if (ListUitis.isEmpty(response.data)) {
            mCommentAdapter.loadMoreEnd();
            return;
        }

        if (response.total_number > 0) {
            mTvCommentCount.setVisibility(StateView.VISIBLE);
            mTvCommentCount.setText(String.valueOf(response.total_number));
        }

        commentList.addAll(response.data);
        mCommentAdapter.notifyDataSetChanged();

        if (!response.has_more) {
            mCommentAdapter.loadMoreEnd();
        } else {
            mCommentAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onError() {
        mStateView.showRetry();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getComment(mGroupId, mItemId, commentList.size() / Constant.COMMENT_PAGE_SIZE + 1);
    }

    protected void postVideoEvent(boolean isVideoDetail) {
        DetailCloseEvent event = new DetailCloseEvent();
        event.setChannelCode(mChannelCode);
        event.setPosition(mPosition);

        if (mCommentResponse != null) {
            event.setCommentCount(mCommentResponse.total_number);
        }

        // todo
        event.setProgress(0);
        EventBus.getDefault().postSticky(event);
        finish();
    }
}
