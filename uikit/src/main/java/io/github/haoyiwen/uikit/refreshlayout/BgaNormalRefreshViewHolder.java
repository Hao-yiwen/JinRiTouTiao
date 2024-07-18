package io.github.haoyiwen.uikit.refreshlayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.haoyiwen.uikit.R;

//  类似新浪微博的下拉刷新风格
public class BgaNormalRefreshViewHolder extends BGARefreshViewHolder {
    private TextView mHeaderStatusTv;
    private ImageView mHeaderArrowIv;

    private ImageView mHeaderChrysanthemumIv;

    private AnimationDrawable mHeaderChrysanthemumAd;

    private RotateAnimation mUpAnim;

    private RotateAnimation mDownAnim;

    private String mPullDownRefreshText = "下拉刷新";

    private String mReleaseRefreshText = "释放更新";

    private String mRefreshingText = "加载中...";


    public BgaNormalRefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
        initAnimation();
    }

    private void initAnimation() {
        mUpAnim = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mUpAnim.setDuration(150);
        mUpAnim.setFillAfter(true);

        mDownAnim = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mDownAnim.setFillAfter(true);
    }

    // 设置未满足刷新条件 提示继续往下拉的文本
    public void setPullDownRefreshText(String pullDownRefreshText) {
        mPullDownRefreshText = pullDownRefreshText;
    }

    // 设置满足刷新条件的文本
    public void setReleaseRefreshText(String releaseRefreshText) {
        mReleaseRefreshText = releaseRefreshText;
    }

    // 设置正在刷新时的文本
    public void setRefreshingText(String refreshingText) {
        mRefreshingText = refreshingText;
    }

    @Override
    public View getRefreshHeaderView() {
        if (mRefreshHeaderView == null) {
            mRefreshHeaderView = View.inflate(mContext, R.layout.view_refresh_header_normal, null);
            mRefreshHeaderView.setBackgroundColor(Color.TRANSPARENT);
            if (mRefreshViewBackgroundColorRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundColorRes);
            }
            if (mRefreshViewBackgroundDrawableRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundDrawableRes);
            }
            mHeaderStatusTv = mRefreshHeaderView.findViewById(R.id.tv_normal_refresh_header_status);
            mHeaderArrowIv = mRefreshHeaderView.findViewById(R.id.iv_normal_refresh_header_arrow);
            mHeaderChrysanthemumIv = mRefreshHeaderView.findViewById(R.id.iv_normal_refresh_header_chrysanthemum);
            mHeaderChrysanthemumAd = (AnimationDrawable) mHeaderChrysanthemumIv.getDrawable();
            mHeaderStatusTv.setText(mPullDownRefreshText);
        }
        return mRefreshHeaderView;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {
    }

    @Override
    public void changeToIdle() {
    }

    @Override
    public void changeToPullDown() {
        mHeaderStatusTv.setText(mPullDownRefreshText);
        mHeaderChrysanthemumIv.setVisibility(View.INVISIBLE);
        mHeaderChrysanthemumAd.stop();
        mHeaderChrysanthemumIv.setVisibility(View.VISIBLE);
        mDownAnim.setDuration(150);
        mHeaderArrowIv.setAnimation(mDownAnim);
    }

    @Override
    public void changeToReleaseRefresh() {
        mHeaderStatusTv.setText(mReleaseRefreshText);
        mHeaderChrysanthemumIv.setVisibility(View.INVISIBLE);
        mHeaderChrysanthemumAd.stop();
        mHeaderArrowIv.setVisibility(View.VISIBLE);
        mHeaderArrowIv.startAnimation(mUpAnim);
    }

    @Override
    public void changeToRefreshing() {
        mHeaderStatusTv.setText(mRefreshingText);
        mHeaderArrowIv.clearAnimation();
        mHeaderArrowIv.setVisibility(View.INVISIBLE);
        mHeaderChrysanthemumIv.setVisibility(View.VISIBLE);
        mHeaderChrysanthemumAd.start();
    }

    @Override
    public void onEndRefreshing() {
        mHeaderStatusTv.setText(mPullDownRefreshText);
        mHeaderChrysanthemumIv.setVisibility(View.INVISIBLE);
        mHeaderChrysanthemumAd.stop();
        mHeaderArrowIv.setVisibility(View.VISIBLE);
        mDownAnim.setDuration(0);
        mHeaderArrowIv.startAnimation(mDownAnim);
    }
}
