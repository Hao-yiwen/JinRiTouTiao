package io.github.haoyiwen.jinritoutiao.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.MultipleItemRvAdapter;

import org.w3c.dom.Text;

import java.util.List;

import io.github.haoyiwen.jinritoutiao.model.entity.News;
import io.github.haoyiwen.jinritoutiao.ui.adapter.provider.news.CenterPicNewsItemProvider;
import io.github.haoyiwen.jinritoutiao.ui.adapter.provider.news.RightPicNewsItemProvider;
import io.github.haoyiwen.jinritoutiao.ui.adapter.provider.news.TextNewsItemProvider;
import io.github.haoyiwen.jinritoutiao.ui.adapter.provider.news.ThreePicNewsItemProvider;
import io.github.haoyiwen.jinritoutiao.utils.ListUitis;

public class NewsListAdapter extends MultipleItemRvAdapter<News, BaseViewHolder> {

    // 纯文字布局
    public static final int TEXT_NEWS = 100;

    // 居中大图布局
    public static final int CENTER_SINGLE_PIC_NEWS = 200;

    // 右侧小图布局
    public static final int RIGHT_PIC_VIDEO_NEWS = 300;

    // 三张图片布局
    public static final int THREE_PICS_NEWS = 400;

    private String mChannelCode;

    public NewsListAdapter(String mChnnelCode, List<News> mNewsList) {
        super(mNewsList);
        mChannelCode = mChnnelCode;
        finishInitialize();
    }

    @Override
    protected int getViewType(News news) {
        if (news.has_video) {
            if (news.video_style == 0) {
                if (news.middle_image == null || TextUtils.isEmpty(news.middle_image.uri)) {
                    return TEXT_NEWS;
                }
                return RIGHT_PIC_VIDEO_NEWS;
            } else if (news.video_style == 2) {
                return CENTER_SINGLE_PIC_NEWS;
            }
        } else {
            if (!news.has_image) {
                return TEXT_NEWS;
            } else {
                if (ListUitis.isEmpty(news.image_list)) {
                    return RIGHT_PIC_VIDEO_NEWS;
                }

                if (news.gallary_image_count == 3) {
                    return THREE_PICS_NEWS;
                }

                return CENTER_SINGLE_PIC_NEWS;
            }
        }
        return TEXT_NEWS;
    }

    @Override
    public void registerItemProvider() {
        mProviderDelegate.registerProvider(new TextNewsItemProvider(mChannelCode));
        mProviderDelegate.registerProvider(new CenterPicNewsItemProvider(mChannelCode));
        mProviderDelegate.registerProvider(new RightPicNewsItemProvider(mChannelCode));
        mProviderDelegate.registerProvider(new ThreePicNewsItemProvider(mChannelCode));
    }
}
