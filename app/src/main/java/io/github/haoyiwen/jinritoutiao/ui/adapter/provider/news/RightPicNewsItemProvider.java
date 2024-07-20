package io.github.haoyiwen.jinritoutiao.ui.adapter.provider.news;

import com.chad.library.adapter.base.BaseViewHolder;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.model.entity.News;
import io.github.haoyiwen.jinritoutiao.ui.adapter.NewsListAdapter;
import io.github.haoyiwen.jinritoutiao.utils.GlideUtils;
import io.github.haoyiwen.jinritoutiao.utils.TimeUtils;

public class RightPicNewsItemProvider extends BaseNewsItemProvider {
    public RightPicNewsItemProvider(String mChannelCode) {
        super(mChannelCode);
    }

    @Override
    protected void setData(BaseViewHolder baseViewHolder, News news) {
        if (news.has_video) {
            baseViewHolder.setVisible(R.id.ll_duration, true);
            baseViewHolder.setText(R.id.tv_duration, TimeUtils.secToTime(news.video_duration));
        } else {
            baseViewHolder.setVisible(R.id.ll_duration, false);
        }
        GlideUtils.load(mContext, news.middle_image.url, baseViewHolder.getView(R.id.iv_img));
    }

    @Override
    public int viewType() {
        return NewsListAdapter.RIGHT_PIC_VIDEO_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_pic_video_news;
    }
}
