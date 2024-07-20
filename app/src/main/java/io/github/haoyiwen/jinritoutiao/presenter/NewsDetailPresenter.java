package io.github.haoyiwen.jinritoutiao.presenter;

import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.presenter.view.INewsListView;

public class NewsDetailPresenter extends BasePresenter<INewsListView> {
    public NewsDetailPresenter(INewsListView view) {
        super(view);
    }

    public void getNewsDetail(String mDetailUrl) {
    }

    public void getComment(String mGroupId, String mItemId, int i) {

    }
}
