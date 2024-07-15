package io.github.haoyiwen.jinritoutiao.presenter;

import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.presenter.view.INewsListView;
import io.github.haoyiwen.jinritoutiao.ui.fragment.NewsListFragment;

public class NewListPresenter extends BasePresenter<INewsListView> {

    private long lastTime;

    public NewListPresenter(INewsListView view) {
        super(view);
    }

    public void getNewsList(String channelCode){

    }
}
