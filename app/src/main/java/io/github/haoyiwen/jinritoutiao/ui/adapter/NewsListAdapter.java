package io.github.haoyiwen.jinritoutiao.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.MultipleItemRvAdapter;

import java.util.List;

import io.github.haoyiwen.jinritoutiao.model.entity.News;

public class NewsListAdapter extends MultipleItemRvAdapter<News, BaseViewHolder> {
    public NewsListAdapter(String mChnnelCode, List<News> mNewsList) {
        super(mNewsList);
    }

    @Override
    protected int getViewType(News news) {
        return 0;
    }

    @Override
    public void registerItemProvider() {

    }
}
