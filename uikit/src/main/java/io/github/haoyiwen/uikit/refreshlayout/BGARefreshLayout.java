package io.github.haoyiwen.uikit.refreshlayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author haoyiwen email:yiwenlemo@gmail.com
 * @createTime 2024/07/16
 * 下拉刷新，上拉刷新，可添加自定义(固定，可滑动)头部空控件 例如慕课网app的顶部广告位
 */
public class BGARefreshLayout extends LinearLayout {
    public static final String TAG = BGARefreshLayout.class.getSimpleName();

    private BGARefreshViewHolder mRefreshViewHolder;

    // 头部控件
    private LinearLayout mWholeHeaderView;

    // 下拉刷新组件
    private View mRefreshHeaderView;

    // 下拉刷新控件下方的自定义控件
    private View mCustomHeaderView;

    // 下拉刷新控件下方的自定义控件是否可滚动 默认不可滚动
    private boolean mIsCustomHeaderViewScrollable = false;

    //下拉刷新控件的高度
    private int mRefreshHeaderViewHeight;

    // 当前刷新状态
    private RefreshStatus mCurrentRefreshStatus = RefreshStatus.IDLE;

    //上拉刷新更多控件
    private View mLoadMoreFooterView;

    // 上拉刷新更多控件的高度
    private int mLoadMoreFooterViewHeight;

    //下拉刷新和上拉刷新更多代理
    private BGARefreshLayoutDelegate mDelegate;

    // 手指按下时 y轴方向的偏移量
    private int mWholeHeaderDrownY = -1;

    //整个头部控件最小的paddingtop
    private int mMinWholeHeaderViewPaddingTop;

    //整个头部最大的paddingTop
    private int mMaxWholeHeaderViewPaddingTop;

    //是否处于加载更多状态
    private boolean mIsLoadingMore = false;

    private View mContentView;
    private AbsListView mAbsListView;
    private RecyclerView mRecyclerView;
    private ScrollView mScrollview;
    private WebView mWebView;
    private BGAStickyNavLayout mStickyNavLayout;
    private View mNormalView;

    private float mInterceptTouchDownX = -1;
    private float mInterceptTouchDownY = -1;

    // 按下时整个头部控件的paddingtop
    private int mWholeHeaderViewDownPaddingTop = 0;

    //记录开始下啦刷新时的downy
    private int mRefreshDownY = -1;

    // 是否已经设置内容控件滚动监听器
    private boolean mIsInitedContentViewScrollListener = false;

    // 触发上啦刷新加载更多时是否显示加载更多控件
    private boolean mIsShowLoadingMoreView = true;

    // 下拉刷新是否可用
    private boolean mPullDownRefreshEnable = true;

    Handler mHandler;

    private BGARefreshScaleDelegate mRefreshScaleDelegate;

    private int mTouchSlop;

    public BGARefreshLayout(Context context) {
        this(context, null);
    }

    public BGARefreshLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setOrientation(VERTICAL);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mHandler = new Handler(Looper.getMainLooper());
        inittWholeHeaderView();
    }

    /**
     * 初始化整个头部区域
     */
    private void inittWholeHeaderView() {
        mWholeHeaderView = new LinearLayout(getContext());
        mWholeHeaderView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mWholeHeaderView.setOrientation(VERTICAL);
        addView(mWholeHeaderView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() != 2) {
            throw new RuntimeException(BGARefreshLayout.class.getSimpleName() + "必须有且只有一个直接子控件");
        }

        mContentView = getChildAt(1);
        if (mContentView instanceof AbsListView) {
            mAbsListView = (AbsListView) mContentView;
        } else if (mContentView instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) mContentView;
        } else if (mContentView instanceof ScrollView) {
            mScrollview = (ScrollView) mContentView;
        } else if (mContentView instanceof WebView) {
            mWebView = (WebView) mContentView;
        } else if (mContentView instanceof BGAStickyNavLayout) {
            mStickyNavLayout = (BGAStickyNavLayout) mContentView;
//            mStickyNavLayout.setNa
        } else if (mContentView instanceof FrameLayout) {
            FrameLayout frameLayout = (FrameLayout) mContentView;
            View childView = frameLayout.getChildAt(0);
            if (childView instanceof RecyclerView) {
                mRecyclerView = (RecyclerView) childView;
            }
        } else {
            mNormalView = mContentView;
            //设置为可点击 否则在空白区域无法拖动
            mNormalView.setClickable(true);
        }
    }

    public void setRefreshViewHolder(BGARefreshViewHolder refreshViewHolder) {
        mRefreshViewHolder = refreshViewHolder;
        mRefreshViewHolder.setRefreshLayout(this);
        initRefreshHeaderView();
        initLoadMoreFooterView();
    }

    public void setChangeWholeHeaderViewPaddingTop(int distance) {
        ValueAnimator animator = ValueAnimator.ofInt(mWholeHeaderView.getPaddingTop(), mWholeHeaderView.getPaddingTop() - distance);
        animator.setDuration(mRefreshViewHolder.getmTopAnimDuration());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int paddingTop = (int) animation.getAnimatedValue();
                mWholeHeaderView.setPadding(0, paddingTop, 0, 0);
            }
        });
        animator.start();
    }

    // 初始化下拉刷新组件
    private void initRefreshHeaderView() {
        mRefreshHeaderView = mRefreshViewHolder.getRefreshHeaderView();
        if (mRefreshHeaderView != null) {
            mRefreshHeaderView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            mRefreshHeaderViewHeight = mRefreshViewHolder.getRefreshHeaderViewHeight();
            mMinWholeHeaderViewPaddingTop = -mRefreshHeaderViewHeight;
            mMaxWholeHeaderViewPaddingTop = (int) (mRefreshHeaderViewHeight * mRefreshViewHolder.getSpringDistanceScale());

            mWholeHeaderView.setPadding(0, mMinWholeHeaderViewPaddingTop, 0, 0);
            mWholeHeaderView.addView(mRefreshHeaderView, 0);
        }
    }

    //设置下啦刷新控件下方的自定义组件
    public void setmCustomHeaderView(View customHeaderView, boolean scrollable) {
        if (mCustomHeaderView != null && mCustomHeaderView.getParent() != null) {
            ViewGroup parent = (ViewGroup) mCustomHeaderView.getParent();
            parent.removeView(mCustomHeaderView);
        }
        mCustomHeaderView = customHeaderView;
        if (mCustomHeaderView != null) {
            mCustomHeaderView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            mWholeHeaderView.addView(mContentView);
            mIsCustomHeaderViewScrollable = scrollable;
        }
    }

    // 初始化上拉刷新更多组件
    private void initLoadMoreFooterView() {
        mLoadMoreFooterView = mRefreshViewHolder.getmLoadMoreFooterView();
        if (mLoadMoreFooterView != null) {
            //测量上拉加载的更多控件高度
            mLoadMoreFooterView.measure(0, 0);
            mLoadMoreFooterViewHeight = mLoadMoreFooterView.getMeasuredHeight();
            mLoadMoreFooterView.setVisibility(GONE);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public enum RefreshStatus {
        IDLE, PULL_DOWN, RELEASE_REFRESH, REFRESHING
    }

    public interface BGARefreshScaleDelegate {
        /**
         * 下啦刷新控件可见时 处理上啦进度
         *
         * @param scale         下啦过程0到1 回弹过程1到0 没有加上弹簧距离移动时的比例
         * @param moveYDistance 整个下啦刷新控件paddingtop变化的值 如果有弹簧距离 会大于整个下啦刷新控件的高度
         */
        void onRefreshScaleChanged(float scale, int moveYDistance);
    }

    public static int dp2px(Context context, int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }
}
