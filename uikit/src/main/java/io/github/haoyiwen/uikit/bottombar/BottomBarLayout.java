package io.github.haoyiwen.uikit.bottombar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class BottomBarLayout extends LinearLayout {

    private static final String STATE_INSTANCE = "state_instance";

    private static final String STATE_ITEM = "state_item";

    private ViewPager mViewPager;

    private int mChildCount;

    private List<BottomBarItem> mItemViews;

    private int mCurrentItem;

    private boolean mSmoothScroll;

    public BottomBarLayout(Context context) {
        super(context);
    }

    public BottomBarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomBarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
