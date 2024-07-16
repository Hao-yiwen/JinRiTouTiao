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
