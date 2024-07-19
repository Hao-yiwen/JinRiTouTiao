package io.github.haoyiwen.jinritoutiao.utils;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.reactivestreams.Subscriber;

import java.lang.annotation.Annotation;
import java.security.PublicKey;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.api.ApiRetrofit;
import io.github.haoyiwen.jinritoutiao.model.entity.Video;
import io.github.haoyiwen.jinritoutiao.model.entity.VideoModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.subscribers.SubscriberResourceWrapper;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


public abstract class VideoPathDecoder{
    public static final String TAG = VideoPathDecoder.class.getSimpleName();

    public void decodePath(String srcUrl) {
        Logger.e(TAG, "srcUrl:" + srcUrl);
        ApiRetrofit.getInstance().getmApiservice().getVideoHtml("https://pv.vlogdownloader.com")
                .flatMap(new Function<String, Observable<VideoModel>>() {

                    @Override
                    public Observable<VideoModel> apply(String s) throws Throwable {
                        Pattern pattern = Pattern.compile("var hash = \"(.+)\"");
                        Matcher matcher = pattern.matcher(s);
                        Logger.e(TAG, "OnResponse" + s);
                        if (matcher.find()) {
                            String hash = matcher.group(1);
                            String url = String.format("http://pv.vlogdownloader.com/api.php?url=%s&hash=%s", srcUrl, hash);
                            Logger.e(TAG, "url:" + url);
                            return ApiRetrofit.getInstance().getmApiservice().getVideoData(url);
                        }
                        return Observable.empty();
                    }
                })
                .map(new Function<VideoModel, Video>() {
                    @Override
                    public Video apply(VideoModel videoModel) throws Throwable {
                        List<Video> videos = videoModel.video;
                        if (!ListUitis.isEmpty(videos)) {
                            return videos.get(videos.size() - 1);
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Video>() {
                    @Override
                    public void onNext(Video video) {
                        if (video != null) {
                            Logger.e(TAG, "onNext:" + video.url);
                            onSuccess(video.url);
                        } else {
                            onDecodeError(UIUtils.getString(R.string.video_parse_error));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        onDecodeError(UIUtils.getString(R.string.video_parse_error));
                    }

                    @Override
                    public void onComplete() {
                        // 可以在这里做一些完成后的操作
                    }
                });
    }

    public abstract void onSuccess(String url);

    public abstract void onDecodeError(String errMsg);
}
