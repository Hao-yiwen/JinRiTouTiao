package io.github.haoyiwen.uikit.refreshlayout;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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
    private BGARefreshLayoutDelegate mRefreshScaleDelegate;

    private int mTouchSlop;

    Handler mHandler;
    private View mContentView;
    private AbsListView mAbsListView;
    private RecyclerView mRecyclerView;
    private ScrollView mScrollview;
    private WebView mWebView;
    private BGAStickyNavLayout mStickyNavLayout;
    private View mNormalView;


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
        } else if(mContentView instanceof BGAStickyNavLayout){
            mStickyNavLayout = (BGAStickyNavLayout) mContentView;
//            mStickyNavLayout.setNa
        } else if(mContentView instanceof FrameLayout){
            FrameLayout frameLayout = (FrameLayout) mContentView;
            View childView = frameLayout.getChildAt(0);
            if(childView instanceof RecyclerView){
                mRecyclerView = (RecyclerView) childView;
            }
        } else {
            mNormalView = mContentView;
            //设置为可点击 否则在空白区域无法拖动
            mNormalView.setClickable(true);
        }
    }

    public void setRefreshViewHolder(BGARefreshViewHolder refreshViewHolder){
        mRefreshViewHolder = refreshViewHolder;
        mRefreshViewHolder.setRefreshLayout(this);
    }

    public enum RefreshStatus {
        IDLE, PULL_DOWN, RELEASE_REFRESH, REFRESHING
    }
}
