package io.github.haoyiwen.uikit.bottombar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.github.haoyiwen.uikit.R;
import io.github.haoyiwen.uikit.R.styleable;
import io.github.haoyiwen.uikit.utils.UIUtils;

import androidx.annotation.Nullable;

public class BottomBarItem extends LinearLayout {
    private Context mContext;

    private int mIconNormalResourceId;

    private int mIconSelectedResourceId;

    private String mText;

    private int mTextSize;

    private int mTextColorNormal;

    private int mTextColorSelected;

    private int mMarginTop;

    private boolean mOpenTouchBg;

    private Drawable mTouchDrawable;

    public TextView mTextView;

    private ImageView mImageView;


    public BottomBarItem(Context context) {
        this(context, null);
    }

    public BottomBarItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBarItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mTextSize = 12;
        this.mTextColorNormal = -6710887;
        this.mTextColorSelected = -12140518;
        this.mMarginTop = 0;
        this.mOpenTouchBg = false;
        this.mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, styleable.BottomBarItem);
        this.mIconNormalResourceId = ta.getResourceId(styleable.BottomBarItem_iconNormal, -1);
        this.mIconSelectedResourceId = ta.getResourceId(styleable.BottomBarItem_iconSelected, -1);
        this.mText = ta.getString(styleable.BottomBarItem_itemText);
        this.mTextSize = ta.getDimensionPixelSize(styleable.BottomBarItem_itemTextSize, UIUtils.sp2px(this.mContext, (float) this.mTextSize));
        this.mTextColorSelected = ta.getColor(styleable.BottomBarItem_textColorSelected, this.mTextColorSelected);
        this.mTextColorNormal = ta.getColor(styleable.BottomBarItem_textColorNormal, this.mTextColorNormal);
        this.mMarginTop = ta.getDimensionPixelSize(styleable.BottomBarItem_itemMarginTop, UIUtils.dip2px(this.mContext, (float) this.mMarginTop));
        this.mOpenTouchBg = ta.getBoolean(styleable.BottomBarItem_openTouchBg, this.mOpenTouchBg);
        this.mTouchDrawable = ta.getDrawable(styleable.BottomBarItem_touchDrawable);
        ta.recycle();
        this.checkValue();
        this.init();
    }

    private void init() {
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setGravity(Gravity.CENTER);
        View view = View.inflate(this.mContext, R.layout.item_bottom_bar, null);
        this.mImageView = view.findViewById(R.id.iv_icon);
        this.mTextView = view.findViewById(R.id.tv_text);
        this.mImageView.setImageResource(this.mIconNormalResourceId);
        this.mTextView.setText(this.mText);
        this.mTextView.getPaint().setTextSize((float) this.mTextSize);
        this.mTextView.setTextColor(this.mTextColorNormal);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mTextView.getLayoutParams();
        layoutParams.topMargin = this.mMarginTop;
        this.mTextView.setLayoutParams(layoutParams);
        if (this.mOpenTouchBg) {
            this.setBackground(this.mTouchDrawable);
        }

        this.addView(view);
    }

    private void checkValue() {
        if (this.mIconNormalResourceId == -1) {
            throw new IllegalStateException("请设置iconNormal属性");
        } else if (this.mIconSelectedResourceId == -1) {
            throw new IllegalStateException("请设置iconSelected属性");
        } else if (this.mOpenTouchBg && this.mTouchDrawable == null) {
            throw new IllegalStateException("开启了触摸效果，但是没有指定触摸效果");
        }
    }

    public ImageView getImageView() {
        return this.mImageView;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setIconNormalResourceId(int mIconNormalResourceId) {
        this.mIconNormalResourceId = mIconNormalResourceId;
    }

    public void setIconSelectedResourceId(int mIconSelectedResourceId) {
        this.mIconSelectedResourceId = mIconSelectedResourceId;
    }

    public void setStatus(boolean isSelected) {
        this.mImageView.setImageResource(isSelected ? this.mIconSelectedResourceId : this.mIconNormalResourceId);
        this.mTextView.setTextColor(isSelected ? this.mTextColorSelected : this.mTextColorNormal);
    }
}
