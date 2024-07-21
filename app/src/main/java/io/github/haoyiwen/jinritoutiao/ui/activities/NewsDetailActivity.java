package io.github.haoyiwen.jinritoutiao.ui.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;

import org.w3c.dom.Text;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.databinding.ActivityNewsDetailBinding;
import io.github.haoyiwen.jinritoutiao.model.response.NewDetail;
import io.github.haoyiwen.jinritoutiao.ui.widget.NewsDetailHeaderView;
import io.github.haoyiwen.jinritoutiao.utils.GlideUtils;
import io.github.haoyiwen.jinritoutiao.utils.UIUtils;
import io.github.haoyiwen.uikit.statusbar.Eyes;

public class NewsDetailActivity extends NewsDetailBaseActivity<ActivityNewsDetailBinding> {
    ImageView mIvBack;


    LinearLayout mllUser;


    ImageView mIvAvatar;

    TextView mTvAuthor;

    @Override
    protected Class getBindingClass() {
        return ActivityNewsDetailBinding.class;
    }

    @Override
    public void initListener() {
        super.initListener();

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postVideoEvent(false);
            }
        });

        int llInfoBottom = mHeaderView.mLlInfo.getBottom();
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRvComment.getLayoutManager();
        mRvComment.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                int position = layoutManager.findFirstVisibleItemPosition();
                View firstVisiableChildView = layoutManager.findViewByPosition(position);
                int itemHeight = firstVisiableChildView.getHeight();
                int scrollHeight = (position) * itemHeight - firstVisiableChildView.getTop();

                Logger.i("scrollHeight" + scrollHeight);
                Logger.i("llInfoBottom" + llInfoBottom);

                mllUser.setVisibility(scrollHeight > llInfoBottom ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onGetNewsDetailSuccess(NewDetail newDetail) {
        mHeaderView.setDetail(newDetail, new NewsDetailHeaderView.LoadWebListener() {
            @Override
            public void onLoadFinished() {
                mStateView.showContent();
            }
        });

        mllUser.setVisibility(View.GONE);

        if (newDetail.media_user != null) {
            GlideUtils.loadRound(this, newDetail.media_user.avatar_url, mIvAvatar);
            mTvAuthor.setText(newDetail.media_user.screen_name);
        }
    }

    @Override
    public void initView() {
        super.initView();
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_BDBDBD));
    }

    @Override
    protected void initBinding() {
        mFlContent = mBinding.flContent;
        mRvComment = mBinding.rvComment;
        mTvCommentCount = mBinding.bottomIncluded.tvCommentCount;

        mIvBack = mBinding.included.ivBack;
        mllUser = mBinding.included.llUser;
        mIvAvatar = mBinding.included.ivAvatar;
        mTvAuthor = mBinding.included.tvAuthor;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        postVideoEvent(false);
    }
}
