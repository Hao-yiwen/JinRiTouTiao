package io.github.haoyiwen.jinritoutiao.ui.activities;

import android.content.Intent;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.nukc.stateview.StateView;

import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.base.BaseActivity;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.model.entity.CommentData;
import io.github.haoyiwen.jinritoutiao.model.response.CommentResponse;
import io.github.haoyiwen.jinritoutiao.presenter.NewsDetailPresenter;
import io.github.haoyiwen.jinritoutiao.presenter.view.INewsListView;
import io.github.haoyiwen.jinritoutiao.ui.adapter.CommentAdapter;
import io.github.haoyiwen.jinritoutiao.ui.widget.NewsDetailHeaderView;
import io.github.haoyiwen.uikit.powerfulrecyclerview.PowerfulRecyclerView;

public abstract class NewsDetailBaseActivity extends BaseActivity<NewsDetailPresenter, ViewBinding> implements INewsListView, BaseQuickAdapter.RequestLoadMoreListener {
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


    protected abstract ViewBinding initViewBinding();

    @Override
    protected NewsDetailPresenter createPresenter() {
        return new NewsDetailPresenter(this);
    }

    @Override
    public void initView() {
        mStateView = StateView.inject(mFlContent);
        mStateView.setLoadingResource(io.github.haoyiwen.uikit.R.layout.page_loading);
        mStateView.setRetryResource(io.github.haoyiwen.uikit.R.layout.page_net_error);
    }

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

    }
}
