package io.github.haoyiwen.jinritoutiao.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.listener.VideoStateListener;
import io.github.haoyiwen.jinritoutiao.model.entity.News;
import io.github.haoyiwen.jinritoutiao.utils.GlideUtils;
import io.github.haoyiwen.jinritoutiao.utils.MyJZVideoPlayerStandard;
import io.github.haoyiwen.jinritoutiao.utils.TimeUtils;
import io.github.haoyiwen.jinritoutiao.utils.UIUtils;
import io.github.haoyiwen.jinritoutiao.utils.VideoPathDecoder;

public class VideoListAdapter extends BaseQuickAdapter<News, BaseViewHolder> {
    public VideoListAdapter(List<News> mNewsList) {
        super(R.layout.item_video_list, mNewsList);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, News news) {
        if (TextUtils.isEmpty(news.title)) {
            return;
        }

        baseViewHolder.setVisible(R.id.ll_title, true);
        baseViewHolder.setText(R.id.tv_title, news.title);

        MyJZVideoPlayerStandard videoplayer = baseViewHolder.getView(R.id.video_player);
        if (news.video_detail_info != null) {
            String format = UIUtils.getString(R.string.video_play_count);
            int watchCount = news.video_detail_info.video_watch_count;
            String countUnit = "";
            if (watchCount > 10000) {
                watchCount = watchCount / 10000;
                countUnit = "万";
            }
            baseViewHolder.setText(R.id.tv_watch_count, String.format(format, watchCount + countUnit));

            GlideUtils.load(mContext, news.video_detail_info.detail_video_large_image.url, videoplayer.posterImageView, R.color.color_d8d8d8);
        }

        GlideUtils.loadRound(mContext, news.user_info.avatar_url, baseViewHolder.getView(R.id.iv_avatar));

        baseViewHolder.setVisible(R.id.ll_duration, true)
                .setText(R.id.tv_duration, TimeUtils.secToTime(news.video_duration));

        baseViewHolder.setText(R.id.tv_author, news.user_info.name)
                .setText(R.id.tv_comment_count, String.valueOf(news.comment_count));

        videoplayer.setAllControlsVisiblity(View.GONE, View.GONE, View.VISIBLE, View.GONE, View.VISIBLE, View.GONE, View.GONE);
        videoplayer.tinyBackImageView.setVisibility(View.GONE);

        videoplayer.titleTextView.setText("");

        videoplayer.setVideoStateListener(new VideoStateListener() {
            boolean isVideoParsing = false;

            @Override
            public void onStateNormal() {

            }

            @Override
            public void onPreparing() {

            }

            @Override
            public void onStartClick() {
                Logger.e("onStateClick");
                String videoUrl = "";
                if (news.video_detail_info != null) {
                    videoUrl = news.video_detail_info.parse_video_url;
                }

                if (!TextUtils.isEmpty(videoUrl)) {
                    Logger.e("对应的视频地址" + videoUrl);
                    videoplayer.setUp(videoUrl, news.title, Jzvd.SCREEN_FULLSCREEN);
                    videoplayer.startVideo();
                    return;
                }

                parseVideo();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onPlaying() {

            }

            @Override
            public void onPause() {

            }

            @Override
            public void onProgressChanged(int progress) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onTouch() {

            }

            @Override
            public void onStartDismissControlViewTimer() {

            }

            private void parseVideo() {
                Logger.e("title" + news.title);

                if (isVideoParsing) {
                    Logger.e("视频正在解析...");
                    return;
                } else {
                    isVideoParsing = true;
                }

                videoplayer.setAllControlsVisiblity(View.GONE, View.GONE, View.GONE, View.VISIBLE, View.VISIBLE, View.GONE, View.GONE);
                baseViewHolder.setVisible(R.id.ll_duration, false);
                baseViewHolder.setVisible(R.id.ll_title, false);

                VideoPathDecoder decoder = new VideoPathDecoder() {
                    @Override
                    public void onSuccess(String url) {
                        Logger.i("Video url:" + url);
                        UIUtils.postTaskDelay(new Runnable() {
                            @Override
                            public void run() {
                                //更改视频是否在解析的标识
                                isVideoParsing = false;

                                //准备播放
                                videoplayer.setUp(url, news.title, JzvdStd.SCREEN_FULLSCREEN);

                                if (news.video_detail_info != null) {
                                    news.video_detail_info.parse_video_url = url; //赋值解析后的视频地址
                                    videoplayer.seekToInAdvance = news.video_detail_info.progress; //设置播放进度
                                }

                                //开始播放
                                videoplayer.startVideo();
                            }
                        });
                    }

                    @Override
                    public void onDecodeError(String errMsg) {

                    }
                };
                decoder.decodePath(news.url);
            }
        });
    }
}
