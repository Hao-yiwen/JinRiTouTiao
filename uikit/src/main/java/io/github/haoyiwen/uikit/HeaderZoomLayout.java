package io.github.haoyiwen.uikit;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;

public class HeaderZoomLayout extends ScrollView {
    private View mHeaderView;

    private int mHeaderWidth;

    private int mHeaderHeight;

    private boolean mIsPulling;

    private int mLastY;

    private float mScaleRatio = 0.4f;

    private float mScaleTimes = 2f;

    private float mReplyRatio = 0.5f;

    public HeaderZoomLayout(Context context) {
        this(context, null);
    }

    public HeaderZoomLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderZoomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        mHeaderWidth = mHeaderView.getMeasuredWidth();
        mHeaderHeight = mHeaderView.getMeasuredHeight();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOverScrollMode(OVER_SCROLL_NEVER);
        View child = getChildAt(0);
        if (child != null && child instanceof ViewGroup) {
            mHeaderView = ((ViewGroup) child).getChildAt(0);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeaderWidth = mHeaderView.getMeasuredWidth();
        mHeaderHeight = mHeaderView.getMeasuredHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mHeaderView == null)
            return super.onTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!mIsPulling) {
                    if (getScrollY() == 0) {
                        mLastY = (int) ev.getY();
                    } else {
                        break;
                    }
                }

                if (ev.getY() - mLastY < 0) {
                    return super.onTouchEvent(ev);
                }
                int distance = (int) ((ev.getY() - mLastY) * mScaleRatio);
                mIsPulling = true;
                setZoom(distance);
                return true;
            case MotionEvent.ACTION_UP:
                mIsPulling = false;
                replyView();
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void replyView() {
        final float distance = mHeaderView.getMeasuredWidth() - mHeaderWidth;
        ValueAnimator anim = ObjectAnimator.ofFloat(distance, 0.0F).setDuration((long) (distance * mReplyRatio));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                setZoom((Float) animation.getAnimatedValue());
            }
        });
        anim.start();
    }

    private void setZoom(float s) {
        float scaleTimes = (float) ((mHeaderWidth + s) / (mHeaderWidth * 1.0));
        if (scaleTimes > mScaleTimes) return;

        ViewGroup.LayoutParams layoutParams = mHeaderView.getLayoutParams();
        layoutParams.width = (int) (mHeaderWidth + s);
        layoutParams.height = (int) (mHeaderHeight * ((mHeaderWidth + s) / mHeaderWidth));

        ((MarginLayoutParams) layoutParams).setMargins(0, 0, -(layoutParams.width - mHeaderWidth) / 2, 0);
        mHeaderView.setLayoutParams(layoutParams);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) onScrollListener.onScroll(l, t, oldl, oldt);
    }

    private OnScrollListener onScrollListener;

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public interface OnScrollListener {
        void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }
}
