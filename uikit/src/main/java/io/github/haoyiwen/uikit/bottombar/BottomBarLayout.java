package io.github.haoyiwen.uikit.bottombar;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class BottomBarLayout extends LinearLayout implements ViewPager.OnPageChangeListener {

    private static final String STATE_INSTANCE = "state_instance";

    private static final String STATE_ITEM = "state_item";

    private ViewPager mViewPager;

    private int mChildCount;

    private List<BottomBarItem> mItemViews;

    private int mCurrentItem;

    private boolean mSmoothScroll;

    private OnItemSelectedListener onItemSelectedListener;

    public BottomBarLayout(Context context) {
        this(context, null);
    }

    public BottomBarLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mItemViews = new ArrayList<>();
        this.setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    public void setOrientation(int orientation) {
        if (orientation == LinearLayout.VERTICAL) {
            try {
                throw new IllegalAccessException("BottomBarLayout only supports horizontal orientation");
            } catch (IllegalAccessException e) {
                // 处理异常，打印错误信息
                e.printStackTrace();
            }
        } else {
            super.setOrientation(orientation);
        }
    }

    public void setViewPager(ViewPager mViewPager) throws IllegalAccessException {
        this.mViewPager = mViewPager;
        this.init();
    }

    private void init() throws IllegalAccessException {
        if (this.mViewPager == null) {
            throw new IllegalAccessException("mViewPager参数不能为空");
        } else {
            this.mChildCount = this.getChildCount();
            if (this.mViewPager.getAdapter().getCount() != this.mChildCount) {
                throw new IllegalAccessException("LinearLayout的子View数量必须和ViewPager条目数量一致");
            } else {
                for (int i = 0; i < this.mChildCount; i++) {
                    if (!(this.getChildAt(i) instanceof BottomBarItem)) {
                        throw new IllegalAccessException("BottomBarLayout的子View必须是BottomBarItem");
                    }

                    BottomBarItem bottomBarItem = (BottomBarItem) this.getChildAt(i);
                    this.mItemViews.add(bottomBarItem);
                    bottomBarItem.setOnClickListener(new MyOnClickListener(i));
                }
            }
        }
    }

    public BottomBarItem getBottomItem(int index) {
        return this.mItemViews.get(index);
    }

    public void resetState() {
        for (int i = 0; i < this.mChildCount; i++) {
            this.mItemViews.get(i).setStatus(false);
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    public void onPageSelected(int position) {
        this.mCurrentItem = position;
        this.resetState();
        this.mItemViews.get(position).setStatus(true);
        this.mViewPager.setCurrentItem(position, this.mSmoothScroll);
    }

    public void onPageScrollStateChanged(int state) {
    }

    public void setCurrentItem(int mCurrentItem) {
        this.mCurrentItem = mCurrentItem;
        this.mViewPager.setCurrentItem(this.mCurrentItem, this.mSmoothScroll);
    }

    public int getCurrentItem() {
        return this.mCurrentItem;
    }

    public void setSmoothScroll(boolean mSmoothScroll) {
        this.mSmoothScroll = mSmoothScroll;
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_ITEM, this.mCurrentItem);
        return bundle;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.mCurrentItem = bundle.getInt(STATE_ITEM);
            this.resetState();
            this.mItemViews.get(this.mCurrentItem).setStatus(true);
            super.onRestoreInstanceState(bundle.getParcelable(STATE_INSTANCE));
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(BottomBarItem var1, int var2);
    }

    private class MyOnClickListener implements OnClickListener {
        private int currentIndex;

        public MyOnClickListener(int i) {
            this.currentIndex = i;
        }

        @Override
        public void onClick(View v) {
            if (BottomBarLayout.this.onItemSelectedListener != null) {
                BottomBarLayout.this.onItemSelectedListener.onItemSelected(BottomBarLayout.this.getBottomItem(this.currentIndex), this.currentIndex);
            }

            BottomBarLayout.this.resetState();
            BottomBarLayout.this.mItemViews.get(this.currentIndex).setStatus(true);
            BottomBarLayout.this.mViewPager.setCurrentItem(this.currentIndex, BottomBarLayout.this.mSmoothScroll);
            BottomBarLayout.this.mCurrentItem = this.currentIndex;
        }
    }
}
