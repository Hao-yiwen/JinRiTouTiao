package io.github.haoyiwen.jinritoutiao.ui.adapter.provider.news;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.app.TouTiaoApp;
import io.github.haoyiwen.jinritoutiao.constants.Constant;
import io.github.haoyiwen.jinritoutiao.model.entity.News;
import io.github.haoyiwen.jinritoutiao.utils.TimeUtils;
import io.github.haoyiwen.jinritoutiao.utils.UIUtils;

public abstract class BaseNewsItemProvider extends BaseItemProvider<News, BaseViewHolder> {

    private String mChannelCode;

    public BaseNewsItemProvider(String mChannelCode) {
        this.mChannelCode = mChannelCode;
    }

    @Override
    public void convert(BaseViewHolder baseViewHolder, News news, int i) {
        if (TextUtils.isEmpty(news.title)) {
            // 没有标题则直接跳过
            return;
        }

        baseViewHolder.setText(R.id.tv_title, news.title)
                .setText(R.id.tv_author, news.source)
                .setText(R.id.tv_comment_num, news.comment_count + TouTiaoApp.getmContext().getString(R.string.comment))
                .setText(R.id.tv_time, TimeUtils.getShortTime(news.behot_time * 1000));

        //根据情况显示置顶的广告 热点的标签
        int position = baseViewHolder.getAdapterPosition();
        String[] channelCodes = UIUtils.getStringArr(R.array.channel_code);
        boolean isTop = position == 0 && mChannelCode.equals(channelCodes[0]);
        boolean isHot = news.hot == 1;
        boolean isAd = !TextUtils.isEmpty(news.tag) ? news.tag.equals(Constant.ARTICLE_GENRE_AD) : false;
        boolean isMovie = !TextUtils.isEmpty(news.tag) ? news.tag.equals(Constant.TAG_MOVIDE) : false;
        baseViewHolder.setVisible(R.id.tv_tag, isTop || isHot || isAd);
        baseViewHolder.setVisible(R.id.tv_comment_num, !isAd);

        String tag = "";
        if(isTop){
            tag = UIUtils.getString(R.string.to_top);
            baseViewHolder.setTextColor(R.id.tv_tag, UIUtils.getColor(R.color.color_F96B6B));
        } else if(isAd){
            tag = UIUtils.getString(R.string.ad);
            baseViewHolder.setTextColor(R.id.tv_tag, UIUtils.getColor(R.color.color_3091D8));
        } else if(isHot){
            tag = UIUtils.getString(R.string.hot);
            baseViewHolder.setTextColor(R.id.tv_tag, UIUtils.getColor(R.color.color_F96B6B));
        } else if(isMovie){
            tag = UIUtils.getString(R.string.tag_movie);
            baseViewHolder.setTextColor(R.id.tv_tag, UIUtils.getColor(R.color.color_F96B6B));
        }
        baseViewHolder.setText(R.id.tv_tag, tag);
        setData(baseViewHolder, news);
    }

    protected abstract void setData(BaseViewHolder baseViewHolder, News news);
}
