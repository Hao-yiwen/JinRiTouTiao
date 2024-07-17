package io.github.haoyiwen.uikit.powerfulrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import io.github.haoyiwen.uikit.R;
import io.github.haoyiwen.uikit.UIUtils;

/**
 * 对recycleview进行封装
 */
public class PowerfulRecyclerView extends RecyclerView {
    private Context context;

    // 分割线颜色
    private int mDividerColor;

    //分割线大小
    private int mDividerSize = 1;

    //分割线的drawable
    private Drawable mDividerDrawable;

    //是否使用瀑布流布局 默认不是
    private boolean mUseStaggerLayout;

    //列数 默认是1
    private int mNumColumns = 1;

    // RecycleView的方向 默认垂直
    private int mOrientation = LinearLayoutManager.VERTICAL;

    private int mMarginLeft;

    private int mMarginRight;

    private LayoutManager mLayoutManager;

    private DividerDecoration mDividerDecoration;

    private Drawable mItemDrawable;

    public PowerfulRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public PowerfulRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PowerfulRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PowerfulRecyclerView);

        mDividerColor = ta.getColor(R.styleable.PowerfulRecyclerView_dividerColor, Color.parseColor("#ffd8d8d8"));
        mDividerSize = ta.getDimensionPixelSize(R.styleable.PowerfulRecyclerView_dividerSize, UIUtils.dip2Px(context, 1));

        mDividerDrawable = ta.getDrawable(R.styleable.PowerfulRecyclerView_dividerDrawable);

        mUseStaggerLayout = ta.getBoolean(R.styleable.PowerfulRecyclerView_useStaggerLayout, mUseStaggerLayout);
        mNumColumns = ta.getInt(R.styleable.PowerfulRecyclerView_numColumns, mNumColumns);

        mOrientation = ta.getInt(R.styleable.PowerfulRecyclerView_rvOrientation, mOrientation);

        mMarginLeft = ta.getDimensionPixelSize(R.styleable.PowerfulRecyclerView_dividerMarginLeft, 0);
        mMarginRight = ta.getDimensionPixelSize(R.styleable.PowerfulRecyclerView_dividerMarginRight, 0);

        ta.recycle();

        setLayoutManager();
        setDivider();
    }

    private void setDivider() {
        if (mDividerDrawable == null) {
            mDividerDecoration = new DividerDecoration(context, mOrientation, mDividerColor, mDividerSize, mMarginLeft, mMarginRight);
        } else {
            //绘制图片分割线
            mDividerDecoration = new DividerDecoration(context, mOrientation, mDividerDrawable, mDividerSize, mMarginLeft, mMarginRight);
        }
        this.addItemDecoration(mDividerDecoration);
    }

    private void setLayoutManager() {
        if (!mUseStaggerLayout) {
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                mLayoutManager = new GridLayoutManager(context, mNumColumns);
            } else {
                mLayoutManager = new GridLayoutManager(context, mNumColumns, mOrientation, false);
            }
        } else {
            mLayoutManager = new StaggeredGridLayoutManager(mNumColumns, mOrientation);
        }

        setLayoutManager(mLayoutManager);
    }
}
