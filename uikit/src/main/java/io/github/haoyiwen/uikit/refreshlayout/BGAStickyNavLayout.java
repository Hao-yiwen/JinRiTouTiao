package io.github.haoyiwen.uikit.refreshlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * 继承该抽象类实现响应的抽象方法，做出各种下拉刷新效果。参考BGANormalRefreshViewHolder、BGAStickinessRefreshViewHolder、BGAMoocStyleRefreshViewHolder、BGAMeiTuanRefreshViewHolder
 */
public class BGAStickyNavLayout extends LinearLayout {
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

    public BGAStickyNavLayout(Context context) {
        super(context);
    }

    public BGAStickyNavLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BGAStickyNavLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
