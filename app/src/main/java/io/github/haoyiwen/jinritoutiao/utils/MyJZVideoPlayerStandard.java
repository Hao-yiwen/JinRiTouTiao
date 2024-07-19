package io.github.haoyiwen.jinritoutiao.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.orhanobut.logger.Logger;

import cn.jzvd.JzvdStd;
import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.listener.VideoStateListener;

public class MyJZVideoPlayerStandard extends JzvdStd {
    private VideoStateListener mListener;

    public VideoStateListener getListener() {
        return mListener;
    }

    public void setVideoStateListener(VideoStateListener listener) {
        mListener = listener;
    }

    public MyJZVideoPlayerStandard(Context context) {
        super(context);
    }

    public MyJZVideoPlayerStandard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
    }

    @Override
    public void onClick(View v) {
        // 新版本jzvd没有thumb
        if (v.getId() == cn.jzvd.R.id.start) {
            Logger.e("state: " + state);
            if (state == STATE_IDLE || state == STATE_NORMAL) {
                // 如果是闲置状态 点击后回调点击播放的事件
                if (mListener != null) {
                    mListener.onStartClick();
                    return;
                }
            }
        } else if (v.getId() == cn.jzvd.R.id.fullscreen) {
            if (state == SCREEN_FULLSCREEN) {
                // 点击退出全屏
            } else {
                // 点击进入全屏
            }
        }
        super.onClick(v);
    }

    @Override
    public int getLayoutId() {
        return cn.jzvd.R.layout.jz_layout_std;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mListener != null) {
            mListener.onTouch();
        }
        return super.onTouch(v, event);
    }

    @Override
    public void startVideo() {
        super.startVideo();
        Logger.i(TAG, "startVideo...");
        if (mListener != null) {
            mListener.onStart();
        }
    }

    @Override
    public void onStateNormal() {
        super.onStateNormal();
        if (mListener != null) {
            mListener.onStateNormal();
        }
    }

    @Override
    public void onStatePreparing() {
        super.onStatePreparing();
        Logger.i(TAG, "onStatePreparing...");
        if (mListener != null) {
            mListener.onPreparing();
        }
    }

    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
        Logger.i(TAG, "onStatePlaying...");
        if (mListener != null) {
            mListener.onPlaying();
        }
    }

    @Override
    public void onStatePause() {
        super.onStatePause();
        Logger.i(TAG, "onStatePause...");
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    public void onStateError() {
        super.onStateError();
    }

    @Override
    public void onStateAutoComplete() {
        super.onStateAutoComplete();
        if (mListener != null) {
            mListener.onComplete();
        }
    }

    @Override
    public void onInfo(int what, int extra) {
        super.onInfo(what, extra);
        Logger.i(TAG, "onInfo...");
    }

    @Override
    public void onError(int what, int extra) {
        super.onError(what, extra);
    }

    @Override
    public void startDismissControlViewTimer() {
        super.startDismissControlViewTimer();
        Logger.i(TAG, "startDismissControlViewTimer...");
        if(mListener != null){
            mListener.onStartDismissControlViewTimer();
        }
    }
}
