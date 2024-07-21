package io.github.haoyiwen.jinritoutiao.ui.activities;

import static android.view.View.GONE;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.viewbinding.ViewBinding;

import com.orhanobut.logger.Logger;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.github.haoyiwen.jinritoutiao.databinding.ActivityVideoDetailBinding;
import io.github.haoyiwen.jinritoutiao.model.entity.News;
import io.github.haoyiwen.jinritoutiao.model.response.NewDetail;
import io.github.haoyiwen.jinritoutiao.ui.widget.NewsDetailHeaderView;
import io.github.haoyiwen.jinritoutiao.utils.MyJZVideoPlayerStandard;
import io.github.haoyiwen.jinritoutiao.utils.UIUtils;
import io.github.haoyiwen.jinritoutiao.utils.VideoPathDecoder;
import io.github.haoyiwen.uikit.statusbar.Eyes;

public class VideoDetailActivity extends NewsDetailBaseActivity<ActivityVideoDetailBinding> {

    MyJZVideoPlayerStandard mVideoPlayer;

    ImageView mBack;

    private SensorManager sensorManager;

    private Jzvd.JZAutoFullscreenListener mSensorEventListener;

    private long mProgress;

    private String mVideoUrl;

    @Override
    public void initView() {
        super.initView();
        Eyes.setStatusBarColor(this, UIUtils.getColor(android.R.color.black));

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postVideoEvent(true);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        mProgress = getIntent().getLongExtra(PROGRESS, 0);
        mVideoUrl = getIntent().getStringExtra(VIDEO_URL);
    }

    @Override
    public void initListener() {
        super.initListener();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorEventListener = new Jzvd.JZAutoFullscreenListener();

        mVideoPlayer.setAllControlsVisiblity(View.GONE, View.GONE, View.VISIBLE, View.GONE, View.VISIBLE, View.VISIBLE, View.GONE);
        mVideoPlayer.titleTextView.setVisibility(GONE);
    }

    @Override
    protected Class<ActivityVideoDetailBinding> getBindingClass() {
        return ActivityVideoDetailBinding.class;
    }

    @Override
    public void onGetNewsDetailSuccess(NewDetail newDetail) {
        Logger.e("onGetNewsDetailSuccess", newDetail.url);
        newDetail.content = "";

        mHeaderView.setDetail(newDetail, new NewsDetailHeaderView.LoadWebListener() {
            @Override
            public void onLoadFinished() {
                //加载完成后，显示内容布局
                mStateView.showContent();
            }
        });

        if (TextUtils.isEmpty(mVideoUrl)) {
            Logger.e("没有视频地址，无法解析");
            // 列表页没有解析url 则详情页面需要解析
            VideoPathDecoder decoder = new VideoPathDecoder() {
                @Override
                public void onSuccess(String url) {
                    Logger.e("onGetNewsDetailSuccess", url);
                    UIUtils.postTaskDelay(new Runnable() {
                        @Override
                        public void run() {
                            playVideo(url, newDetail);
                        }
                    });
                }

                @Override
                public void onDecodeError(String errMsg) {
                    UIUtils.showToast(errMsg);
                }
            };
            decoder.decodePath(newDetail.url);
        } else {
            // 列表页面已经解析 直接播放
            playVideo(mVideoUrl, newDetail);
        }
    }

    public void playVideo(String url, NewDetail detail) {
        mVideoPlayer.setUp(url, detail.title, Jzvd.SCREEN_FULLSCREEN);
        mVideoPlayer.seekToInAdvance = mProgress;
        mVideoPlayer.startVideo();
    }

    @Override
    protected void initBinding() {
        mFlContent = mBinding.flContent;
        mRvComment = mBinding.rvComment;
        mTvCommentCount = mBinding.included.tvCommentCount;
        mVideoPlayer = mBinding.videoPlayer;
        mBack = mBinding.ivBack;
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(mSensorEventListener);
        JzvdStd.releaseAllVideos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(mSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        postVideoEvent(true);
    }
}
