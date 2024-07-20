package io.github.haoyiwen.jinritoutiao.ui.adapter.provider.news;

import com.chad.library.adapter.base.BaseViewHolder;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.model.entity.News;
import io.github.haoyiwen.jinritoutiao.ui.adapter.NewsListAdapter;
import io.github.haoyiwen.jinritoutiao.utils.GlideUtils;

public class ThreePicNewsItemProvider extends BaseNewsItemProvider{
    public ThreePicNewsItemProvider(String mChannelCode) {
        super(mChannelCode);
    }

    @Override
    protected void setData(BaseViewHolder baseViewHolder, News news) {
        GlideUtils.load(mContext, news.image_list.get(0).url, baseViewHolder.getView(R.id.iv_img1));
        GlideUtils.load(mContext, news.image_list.get(1).url, baseViewHolder.getView(R.id.iv_img2));
        GlideUtils.load(mContext, news.image_list.get(2).url, baseViewHolder.getView(R.id.iv_img3));
    }

    @Override
    public int viewType() {
        return NewsListAdapter.THREE_PICS_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_three_pics_news;
    }
}
