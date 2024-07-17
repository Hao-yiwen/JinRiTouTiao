package io.github.haoyiwen.uikit.refreshlayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import io.github.haoyiwen.uikit.R;

/**
 * 实现各种下拉刷新效果
 * 继承该抽象类实现响应的抽象方法，做出各种下拉刷新效果。参考BGANormalRefreshViewHolder、BGAStickinessRefreshViewHolder、BGAMoocStyleRefreshViewHolder、BGAMeiTuanRefreshViewHolder
 */
public abstract class BGARefreshViewHolder {
    /**
     * 1.	mPullDistanceScale 控制手指移动和控件移动的比率:
     *         •	当用户下拉时，手指在屏幕上移动的距离与控件 paddingTop 移动的距离有一个比值关系，这个比值就是 mPullDistanceScale。它控制了手指移动和控件移动之间的直接关系。
     *         •	例如，如果 mPullDistanceScale 是 0.5，那么手指每移动 2 像素，控件的 paddingTop 只移动 1 像素。这意味着控件的移动比手指移动要慢，这可以用于创建一种阻尼效果。
     * 2.	mSpringDistanceScale 控制弹簧效果的距离:
     *         •	当用户松开手指时，下拉刷新控件会有一个弹簧回弹的效果。这个效果的距离相对于控件高度的比例就是 mSpringDistanceScale。
     *         •	例如，如果 mSpringDistanceScale 是 0.3，并且控件高度是 100 像素，那么控件的 paddingTop 在回弹时将移动 30 像素。这种弹簧效果增加了用户交互的灵活性和自然感。
     */

    // 手指移动距离与下拉刷新控件paddingTop移动距离的比值
    private static final float PULL_DISTANCE_SCALE = 1.8f;

    //  手指移动距离与下拉空间paddingTop移动距离的比值 1.8f
    private float mPullDistanceScale = PULL_DISTANCE_SCALE;

    // 下拉刷新控件paddingtop的弹簧距离与下拉控件高度的比值
    private static final float SPRING_DISTANCE_SCALE = 0.4f;

    //  下拉刷新控件paddingtop的弹簧距离与下拉刷新控件高度的比值
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
            mLoadMoreFooterView = View.inflate(mContext, R.layout.view_noremal_refresh_footer, null);
            mLoadMoreFooterView.setBackgroundColor(Color.TRANSPARENT);
            if(mLoadMoreBackgroundColorRes != -1){
                mLoadMoreFooterView.setBackgroundColor(mLoadMoreBackgroundColorRes);
            }
            if(mLoadMoreBackgroundDrawableRes != -1){
                mLoadMoreFooterView.setBackgroundResource(mLoadMoreBackgroundDrawableRes);
            }
            mFooterStatUSTv = (TextView) mLoadMoreFooterView.findViewById(R.id.tv_normal_refresh_footer_status);
            mFooterChrysanthemumTv = (ImageView) mLoadMoreFooterView.findViewById(R.id.iv_normal_refresh_footer_chrysanthemum);
            mFooterChrysanthemumAd = (AnimationDrawable) mFooterChrysanthemumTv.getDrawable();
            mFooterStatUSTv.setText(mLoadingMoreText);
        }
        return mLoadMoreFooterView;
    }


    // 获取头部下啦刷新组建
    public abstract View getRefreshHeaderView();

    // 下啦刷新组件可见时 处理上下拉进度
    public abstract void handleScale(float scale, int moveYDistance);

    // 进入未处理下啦刷新状态
    public abstract void changeToIdle();

    // 进入下拉状态
    public abstract void changeToPullDown();

    // 进入释放刷新状态
    public abstract void changeToReleaseRefresh();

    // 进入正在刷新状态
    public abstract void changeToRefreshing();

    // 结束下拉刷新状态
    public abstract void onEndRefreshing();

    // 设置手指移动距离与下啦刷新空间paddingTop移动距离的比值
    public float getPaddingTopScale() {
        return mPullDistanceScale;
    }

    // 设置手指移动距离与下啦刷新组件paddingtop移动距离的比值
    public void setPullDistanceScale(float pullDistanceScale) {
        mPullDistanceScale = pullDistanceScale;
    }

    // 下拉刷新控件paddingtop的弹簧距离与下啦刷新控件高度的比值
    public float getSpringDistanceScale() {
        return mSpringDistanceScale;
    }

    // 设置下拉刷新控件paddingtop的弹簧距离与下拉刷新控件高度的比值
    public void setSpringDistanceScale(float springDistanceScale) {
        if(springDistanceScale < 0){
            throw new RuntimeException("下拉刷新控件paddingtop的弹簧距离与下啦刷新控件高度比值不能小于0");
        }
        mSpringDistanceScale = springDistanceScale;
    }

    //是否能进入刷新状态
    public boolean canChangeToRefreshing(){
        return false;
    }

    //进入加载更多状态
    public void changeToLoadingMore(){
        if(mIsLodingMoreEnabled && mFooterChrysanthemumAd != null){
            mFooterChrysanthemumAd.start();
        }
    }

    //结束上啦加载更多
    public void onEndLoadingMore(){
        if(mIsLodingMoreEnabled && mFooterChrysanthemumAd != null){
            mFooterChrysanthemumAd.stop();
        }
    }

    //获取下拉刷新控件的高度 如果初始化的高度和最后展开的最大高度不一致 需要重写该方法返回最大高度
    public int getRefreshHeaderViewHeight(){
        if(mRefreshHeaderView != null){
            mRefreshHeaderView.measure(0, 0);
            return mRefreshHeaderView.getMeasuredHeight();
        }
        return 0;
    }

    //改变整个下啦刷新头部控件移动一定的距离 自定义刷新控件进入刷新状态前后的高度有变化时可以使用该方法
    public void startChangeWholeHeaderViewPaddingTop(int distance){
        mRefreshLayout.startChangeWholeHeaderViewPaddingTop(distance);
    }

    //设置下拉刷新上拉加载更多控件
    public void setRefreshLayout(BGARefreshLayout refreshLayout){
        mRefreshLayout = refreshLayout;
    }
}
