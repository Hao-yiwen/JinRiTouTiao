package io.github.haoyiwen.jinritoutiao.presenter;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.model.entity.News;
import io.github.haoyiwen.jinritoutiao.model.entity.NewsData;
import io.github.haoyiwen.jinritoutiao.model.response.NewsResponse;
import io.github.haoyiwen.jinritoutiao.presenter.view.INewsListView;
import io.github.haoyiwen.jinritoutiao.ui.fragment.NewsListFragment;
import io.github.haoyiwen.jinritoutiao.utils.ListUitis;
import io.github.haoyiwen.jinritoutiao.utils.PreUtils;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class NewListPresenter extends BasePresenter<INewsListView> {

    private long lastTime;

    public NewListPresenter(INewsListView view) {
        super(view);
    }

    public void getNewsList(String channelCode) {
        lastTime = PreUtils.getLong(channelCode, 0);
        if (lastTime == 0) {
            //如果为0，则是从来没有刷新过 使用当前时间戳
            lastTime = System.currentTimeMillis() / 1000;
        }

        addSubscription(mApiService.getNewsList(channelCode, lastTime, System.currentTimeMillis() / 1000), new DisposableObserver<NewsResponse>() {
            @Override
            public void onNext(@NonNull NewsResponse newsResponse) {
                lastTime = System.currentTimeMillis() / 1000;
                PreUtils.putLong(channelCode, lastTime);

                List<NewsData> data = newsResponse.data;
                List<News> newsList = new ArrayList<>();
                if (!ListUitis.isEmpty(data)) {
                    for (NewsData info : data) {
                        News news = new Gson().fromJson(info.content, News.class);
                        newsList.add(news);
                    }
                }
                Logger.e("newList: " + newsList);
                mView.onGetNewsListSuccess(newsList, newsResponse.tips.display_info);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Logger.e(e.getLocalizedMessage());
                mView.onError();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
