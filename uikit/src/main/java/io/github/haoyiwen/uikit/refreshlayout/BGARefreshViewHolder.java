package io.github.haoyiwen.uikit.refreshlayout;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * 实现各种下拉刷新效果
 * 继承该抽象类实现响应的抽象方法，做出各种下拉刷新效果。参考BGANormalRefreshViewHolder、BGAStickinessRefreshViewHolder、BGAMoocStyleRefreshViewHolder、BGAMeiTuanRefreshViewHolder
 */
public class BGARefreshViewHolder {
    // 手指移动距离与下拉刷新控件paddingTop移动距离的比值
    private static final float PULL_DISTANCE_SCALE = 1.8f;

    //  手指移动距离与下拉空间paddingTop移动距离的比值 1.8f
    private float mPullDistanceScale = PULL_DISTANCE_SCALE;

    // 下拉刷新控件paddingtop的弹簧距离与下拉控件高度的比值
    private static final float SPRING_DISTANCE_SCALE = 0.4f;

    //  下拉甩你控件paddingtop的弹簧距离与下拉刷新控件高度的比值
    private float mSpringDistanceScale = SPRING_DISTANCE_SCALE;

    private Context mContext;

    //下拉刷新上拉刷新更多控件
    protected BGARefreshLayout mRefreshLayout;

    //下拉刷新控件
    protected View mRefreshHeaderView;

    //上拉加载更多控件
    protected View mLoadMoreFooterView;

    // 底部加载更多提示控件
    protected TextView mFooterStatUSTv;

    //底部加载更多菊花控件

    protected ImageView mFooterChrysanthemumTv;

    //底部加载更多菊花drawable
    protected AnimationDrawable mFooterChrysanthemumAd;

    // 正在加载更多时的文本
    protected String mLoadingMoreText = "加载中...";

    //是否开启加载更多功能
    protected boolean mIsLodingMoreEnabled = true;

    //整个加载更多控件的背景颜色资源id

    public BGARefreshLayout getmRefreshLayout() {
        return mRefreshLayout;
    }

    public void setmRefreshLayout(BGARefreshLayout mRefreshLayout) {
        this.mRefreshLayout = mRefreshLayout;
    }

    public View getmRefreshHeaderView() {
        return mRefreshHeaderView;
    }

    public void setmRefreshHeaderView(View mRefreshHeaderView) {
        this.mRefreshHeaderView = mRefreshHeaderView;
    }

    public View getmLoadMoreFooterView() {
        return mLoadMoreFooterView;
    }

    public void setmLoadMoreFooterView(View mLoadMoreFooterView) {
        this.mLoadMoreFooterView = mLoadMoreFooterView;
    }

    public TextView getmFooterStatUSTv() {
        return mFooterStatUSTv;
    }

    public void setmFooterStatUSTv(TextView mFooterStatUSTv) {
        this.mFooterStatUSTv = mFooterStatUSTv;
    }

    public ImageView getmFooterChrysanthemumTv() {
        return mFooterChrysanthemumTv;
    }

    public void setmFooterChrysanthemumTv(ImageView mFooterChrysanthemumTv) {
        this.mFooterChrysanthemumTv = mFooterChrysanthemumTv;
    }

    public AnimationDrawable getmFooterChrysanthemumAd() {
        return mFooterChrysanthemumAd;
    }

    public void setmFooterChrysanthemumAd(AnimationDrawable mFooterChrysanthemumAd) {
        this.mFooterChrysanthemumAd = mFooterChrysanthemumAd;
    }

    public String getmLoadingMoreText() {
        return mLoadingMoreText;
    }

    public void setmLoadingMoreText(String mLoadingMoreText) {
        this.mLoadingMoreText = mLoadingMoreText;
    }

    public boolean ismIsLodingMoreEnabled() {
        return mIsLodingMoreEnabled;
    }

    public void setmIsLodingMoreEnabled(boolean mIsLodingMoreEnabled) {
        this.mIsLodingMoreEnabled = mIsLodingMoreEnabled;
    }

    public int getmLoadMoreBackgroundColorRes() {
        return mLoadMoreBackgroundColorRes;
    }

    public void setmLoadMoreBackgroundColorRes(int mLoadMoreBackgroundColorRes) {
        this.mLoadMoreBackgroundColorRes = mLoadMoreBackgroundColorRes;
    }

    public int getmLoadMoreBackgroundDrawableRes() {
        return mLoadMoreBackgroundDrawableRes;
    }

    public void setmLoadMoreBackgroundDrawableRes(int mLoadMoreBackgroundDrawableRes) {
        this.mLoadMoreBackgroundDrawableRes = mLoadMoreBackgroundDrawableRes;
    }

    private int mLoadMoreBackgroundColorRes = -1;

    //整个加载更多控件背景drawable资源id
    private int mLoadMoreBackgroundDrawableRes = -1;

    //下拉刷新控件的背景颜色资源id
    protected int mRefreshViewBackgroundColorRes = -1;

    //下拉刷新控件的背景drawable资源id

    protected int mRefreshViewBackgroundDrawableRes = -1;

    public int getmTopAnimDuration() {
        return mTopAnimDuration;
    }

    public void setmTopAnimDuration(int mTopAnimDuration) {
        this.mTopAnimDuration = mTopAnimDuration;
    }

    // 头部控件移动动画时长
    private int mTopAnimDuration = 500;

    /**
     * @param context
     * @param isLoadingMoreEnabled 是否开启加载更多功能
     */

    public BGARefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        mContext = context;
        mIsLodingMoreEnabled = isLoadingMoreEnabled;
    }

    /**
     * 设置加载更多时的文本
     */

    public void setLoadingMoreText(String loadingMoreText) {
        mLoadingMoreText = loadingMoreText;
    }

    /**
     * 设置整个加载控件的背景颜色id
     */

    public void setLoadMoreBackgroundColorRes(int loadMoreBackgroundColorRes) {
        mLoadMoreBackgroundColorRes = loadMoreBackgroundColorRes;
    }

    public View getLoadMoreFooterView() {
        if(!mIsLodingMoreEnabled){
            return null;
        }

        if(mLoadMoreFooterView == null){
        }
    }


}
