package io.github.haoyiwen.uikit;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoadingFlashView extends FrameLayout {

    private View mFlashView;

    private ImageView mload1;

    private ImageView mload2;

    private ImageView mload3;

    private ImageView mload4;

    private AnimatorSet mAnimatorSet;

    public LoadingFlashView(@NonNull Context context) {
        super(context);
    }

    public LoadingFlashView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingFlashView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LoadingFlashView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void initView(Context context,AttributeSet attrs,int defStyleAttr){
        mFlashView = LayoutInflater.from(context).inflate(R.layout.loading_flush_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mload1 = findViewById(R.id.load1);
        mload2 = findViewById(R.id.load2);
        mload3 = findViewById(R.id.load3);
        mload4 = findViewById(R.id.load4);
        showLoading();
    }

    private void showLoading() {
        if(getVisibility() != View.VISIBLE){
            return;
        }
        if(mAnimatorSet == null){
            initAnimation();
        }
        if(mAnimatorSet.isRunning() || mAnimatorSet.isStarted()){
            return;
        }
        mAnimatorSet.start();
    }

    private void initAnimation() {
        mAnimatorSet = new AnimatorSet();

        List<ImageView> imageViewList = Arrays.asList(mload1, mload2, mload3, mload4);
        List<Animator> animatorList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ObjectAnimator loadAnimator = ObjectAnimator.ofFloat(imageViewList.get(i), "alpha", 1.0f, 0.4f);
            loadAnimator.setDuration(100 * i);
            loadAnimator.setRepeatMode(ObjectAnimator.REVERSE);
            loadAnimator.setRepeatCount(-1);
            animatorList.add(loadAnimator);
        }
        mAnimatorSet.playTogether(animatorList);
    }

    private void hideLoading() {
        if(mAnimatorSet == null){
            return;
        }
        if((!mAnimatorSet.isRunning()) && (!mAnimatorSet.isStarted())){
            return;
        }
        mAnimatorSet.removeAllListeners();
        mAnimatorSet.cancel();
        mAnimatorSet.end();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if(visibility!=VISIBLE){
            hideLoading();
        }
    }
}
