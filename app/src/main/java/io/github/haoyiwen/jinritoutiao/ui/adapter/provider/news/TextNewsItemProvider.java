package io.github.haoyiwen.jinritoutiao.ui.adapter.provider.news;

import com.chad.library.adapter.base.BaseViewHolder;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.model.entity.News;
import io.github.haoyiwen.jinritoutiao.ui.adapter.NewsListAdapter;

public class TextNewsItemProvider extends BaseNewsItemProvider {
    public TextNewsItemProvider(String mChannelCode) {
        super(mChannelCode);
    }

    @Override
    protected void setData(BaseViewHolder baseViewHolder, News news) {

    }

    @Override
    public int viewType() {
        return NewsListAdapter.TEXT_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_text_news;
    }
}
