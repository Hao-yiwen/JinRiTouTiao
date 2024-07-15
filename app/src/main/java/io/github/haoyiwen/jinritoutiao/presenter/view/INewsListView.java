package io.github.haoyiwen.jinritoutiao.presenter.view;

import java.util.List;

import io.github.haoyiwen.jinritoutiao.model.entity.News;

public interface INewsListView {
    void onGetNewsListSuccess(List<News> newsList, String tipInfo);

    void onError();
}
