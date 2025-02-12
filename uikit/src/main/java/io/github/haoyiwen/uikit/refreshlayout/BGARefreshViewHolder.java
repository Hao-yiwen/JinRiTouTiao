package io.github.haoyiwen.uikit.refreshlayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

import org.w3c.dom.Text;

import io.github.haoyiwen.uikit.R;

/**
 * 实现各种下拉刷新效果
 * 继承该抽象类实现响应的抽象方法，做出各种下拉刷新效果。参考BGANormalRefreshViewHolder、BGAStickinessRefreshViewHolder、BGAMoocStyleRefreshViewHolder、BGAMeiTuanRefreshViewHolder
 */
/**
 * 1.	mPullDistanceScale 控制手指移动和控件移动的比率:
 *         •	当用户下拉时，手指在屏幕上移动的距离与控件 paddingTop 移动的距离有一个比值关系，这个比值就是 mPullDistanceScale。它控制了手指移动和控件移动之间的直接关系。
 *         •	例如，如果 mPullDistanceScale 是 0.5，那么手指每移动 2 像素，控件的 paddingTop 只移动 1 像素。这意味着控件的移动比手指移动要慢，这可以用于创建一种阻尼效果。
 * 2.	mSpringDistanceScale 控制弹簧效果的距离:
 *         •	当用户松开手指时，下拉刷新控件会有一个弹簧回弹的效果。这个效果的距离相对于控件高度的比例就是 mSpringDistanceScale。
 *         •	例如，如果 mSpringDistanceScale 是 0.3，并且控件高度是 100 像素，那么控件的 paddingTop 在回弹时将移动 30 像素。这种弹簧效果增加了用户交互的灵活性和自然感。
 */
public abstract class BGARefreshViewHolder {
    /**
     * 手指移动距离与下拉刷新控件paddingTop移动距离的比值
     */
    private static final float PULL_DISTANCE_SCALE = 1.8f;
    /**
     * 手指移动距离与下拉刷新控件paddingTop移动距离的比值，默认1.8f
     */
    private float mPullDistanceScale = PULL_DISTANCE_SCALE;
    /**
     * 下拉刷新控件paddingTop的弹簧距离与下拉刷新控件高度的比值
     */
    private static final float SPRING_DISTANCE_SCALE = 0.4f;
    /**
     * 下拉刷新控件paddingTop的弹簧距离与下拉刷新控件高度的比值，默认0.4f
     */
    private float mSpringDistanceScale = SPRING_DISTANCE_SCALE;

    protected Context mContext;
    /**
     * 下拉刷新上拉加载更多控件
     */
    protected BGARefreshLayout mRefreshLayout;
    /**
     * 下拉刷新控件
     */
    protected View mRefreshHeaderView;
    /**
     * 上拉加载更多控件
     */
    protected View mLoadMoreFooterView;
    /**
     * 底部加载更多提示控件
     */
    protected TextView mFooterStatusTv;
    /**
     * 底部加载更多菊花控件
     */
    protected ImageView mFooterChrysanthemumIv;
    /**
     * 底部加载更多菊花drawable
     */
    protected AnimationDrawable mFooterChrysanthemumAd;
    /**
     * 正在加载更多时的文本
     */
    protected String mLodingMoreText = "加载中...";
    /**
     * 是否开启加载更多功能
     */
    private boolean mIsLoadingMoreEnabled = true;
    /**
     * 整个加载更多控件的背景颜色资源id
     */
    private int mLoadMoreBackgroundColorRes = -1;
    /**
     * 整个加载更多控件的背景drawable资源id
     */
    private int mLoadMoreBackgroundDrawableRes = -1;
    /**
     * 下拉刷新控件的背景颜色资源id
     */
    protected int mRefreshViewBackgroundColorRes = -1;
    /**
     * 下拉刷新控件的背景drawable资源id
     */
    protected int mRefreshViewBackgroundDrawableRes = -1;
    /**
     * 头部控件移动动画时常
     */
    private int mTopAnimDuration = 500;

    /**
     * @param context
     * @param isLoadingMoreEnabled 上拉加载更多是否可用
     */
    public BGARefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        mContext = context;
        mIsLoadingMoreEnabled = isLoadingMoreEnabled;
    }

    /**
     * 设置正在加载更多时的文本
     *
     * @param loadingMoreText
     */
    public void setLoadingMoreText(String loadingMoreText) {
        mLodingMoreText = loadingMoreText;
    }

    /**
     * 设置整个加载更多控件的背景颜色资源id
     *
     * @param loadMoreBackgroundColorRes
     */
    public void setLoadMoreBackgroundColorRes(@ColorRes int loadMoreBackgroundColorRes) {
        mLoadMoreBackgroundColorRes = loadMoreBackgroundColorRes;
    }

    /**
     * 设置整个加载更多控件的背景drawable资源id
     *
     * @param loadMoreBackgroundDrawableRes
     */
    public void setLoadMoreBackgroundDrawableRes(@DrawableRes int loadMoreBackgroundDrawableRes) {
        mLoadMoreBackgroundDrawableRes = loadMoreBackgroundDrawableRes;
    }

    /**
     * 设置下拉刷新控件的背景颜色资源id
     *
     * @param refreshViewBackgroundColorRes
     */
    public void setRefreshViewBackgroundColorRes(@ColorRes int refreshViewBackgroundColorRes) {
        mRefreshViewBackgroundColorRes = refreshViewBackgroundColorRes;
    }

    /**
     * 设置下拉刷新控件的背景drawable资源id
     *
     * @param refreshViewBackgroundDrawableRes
     */
    public void setRefreshViewBackgroundDrawableRes(@DrawableRes int refreshViewBackgroundDrawableRes) {
        mRefreshViewBackgroundDrawableRes = refreshViewBackgroundDrawableRes;
    }

    /**
     * 获取顶部未满足下拉刷新条件时回弹到初始状态、满足刷新条件时回弹到正在刷新状态、刷新完毕后回弹到初始状态的动画时间，默认为500毫秒
     *
     * @return
     */
    public int getTopAnimDuration() {
        return mTopAnimDuration;
    }

    /**
     * 设置顶部未满足下拉刷新条件时回弹到初始状态、满足刷新条件时回弹到正在刷新状态、刷新完毕后回弹到初始状态的动画时间，默认为300毫秒
     *
     * @param topAnimDuration
     */
    public void setTopAnimDuration(int topAnimDuration) {
        mTopAnimDuration = topAnimDuration;
    }

    /**
     * 获取上拉加载更多控件，如果不喜欢这种上拉刷新风格可重写该方法实现自定义LoadMoreFooterView
     *
     * @return
     */
    public View getLoadMoreFooterView() {
        if (!mIsLoadingMoreEnabled) {
            return null;
        }
        if (mLoadMoreFooterView == null) {
            mLoadMoreFooterView = View.inflate(mContext, R.layout.view_noremal_refresh_footer, null);
            mLoadMoreFooterView.setBackgroundColor(Color.TRANSPARENT);
            if (mLoadMoreBackgroundColorRes != -1) {
                mLoadMoreFooterView.setBackgroundResource(mLoadMoreBackgroundColorRes);
            }
            if (mLoadMoreBackgroundDrawableRes != -1) {
                mLoadMoreFooterView.setBackgroundResource(mLoadMoreBackgroundDrawableRes);
            }
            mFooterStatusTv = (TextView) mLoadMoreFooterView.findViewById(R.id.tv_normal_refresh_footer_status);
            mFooterChrysanthemumIv = (ImageView) mLoadMoreFooterView.findViewById(R.id.iv_normal_refresh_footer_chrysanthemum);
            mFooterChrysanthemumAd = (AnimationDrawable) mFooterChrysanthemumIv.getDrawable();
            mFooterStatusTv.setText(mLodingMoreText);
        }
        return mLoadMoreFooterView;
    }

    /**
     * 获取头部下拉刷新控件
     *
     * @return
     */
    public abstract View getRefreshHeaderView();

    /**
     * 下拉刷新控件可见时，处理上下拉进度
     *
     * @param scale         下拉过程0 到 1，回弹过程1 到 0，没有加上弹簧距离移动时的比例
     * @param moveYDistance 整个下拉刷新控件paddingTop变化的值，如果有弹簧距离，会大于整个下拉刷新控件的高度
     */
    public abstract void handleScale(float scale, int moveYDistance);

    /**
     * 进入到未处理下拉刷新状态
     */
    public abstract void changeToIdle();

    /**
     * 进入下拉状态
     */
    public abstract void changeToPullDown();

    /**
     * 进入释放刷新状态
     */
    public abstract void changeToReleaseRefresh();

    /**
     * 进入正在刷新状态
     */
    public abstract void changeToRefreshing();

    /**
     * 结束下拉刷新
     */
    public abstract void onEndRefreshing();

    /**
     * 手指移动距离与下拉刷新控件paddingTop移动距离的比值
     *
     * @return
     */
    public float getPaddingTopScale() {
        return mPullDistanceScale;
    }

    /**
     * 设置手指移动距离与下拉刷新控件paddingTop移动距离的比值
     *
     * @param pullDistanceScale
     */
    public void setPullDistanceScale(float pullDistanceScale) {
        mPullDistanceScale = pullDistanceScale;
    }

    /**
     * 下拉刷新控件paddingTop的弹簧距离与下拉刷新控件高度的比值
     *
     * @return
     */
    public float getSpringDistanceScale() {
        return mSpringDistanceScale;
    }

    /**
     * 设置下拉刷新控件paddingTop的弹簧距离与下拉刷新控件高度的比值，不能小于0，如果刷新控件比较高，建议将该值设置小一些
     *
     * @param springDistanceScale
     */
    public void setSpringDistanceScale(float springDistanceScale) {
        if (springDistanceScale < 0) {
            throw new RuntimeException("下拉刷新控件paddingTop的弹簧距离与下拉刷新控件高度的比值springDistanceScale不能小于0");
        }
        mSpringDistanceScale = springDistanceScale;
    }

    /**
     * 是处于能够进入刷新状态
     *
     * @return
     */
    public boolean canChangeToRefreshingStatus() {
        return false;
    }

    /**
     * 进入加载更多状态
     */
    public void changeToLoadingMore() {
        if (mIsLoadingMoreEnabled && mFooterChrysanthemumAd != null) {
            mFooterChrysanthemumAd.start();
        }
    }

    /**
     * 结束上拉加载更多
     */
    public void onEndLoadingMore() {
        if (mIsLoadingMoreEnabled && mFooterChrysanthemumAd != null) {
            mFooterChrysanthemumAd.stop();
        }
    }

    /**
     * 获取下拉刷新控件的高度，如果初始化时的高度和最后展开的最大高度不一致，需重写该方法返回最大高度
     *
     * @return
     */
    public int getRefreshHeaderViewHeight() {
        if (mRefreshHeaderView != null) {
            // 测量下拉刷新控件的高度
            mRefreshHeaderView.measure(0, 0);
            return mRefreshHeaderView.getMeasuredHeight();
        }
        return 0;
    }

    /**
     * 改变整个下拉刷新头部控件移动一定的距离（带动画），自定义刷新控件进入刷新状态前后的高度有变化时可以使用该方法（参考BGAStickinessRefreshView）
     *
     * @param distance
     */
    public void startChangeWholeHeaderViewPaddingTop(int distance) {
        mRefreshLayout.startChangeWholeHeaderViewPaddingTop(distance);
    }

    /**
     * 设置下拉刷新上拉加载更多控件，该方法是设置BGARefreshViewHolder给BGARefreshLayout时由BGARefreshLayout调用
     *
     * @param refreshLayout
     */
    public void setRefreshLayout(BGARefreshLayout refreshLayout) {
        mRefreshLayout = refreshLayout;
    }

}
