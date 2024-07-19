package io.github.haoyiwen.jinritoutiao.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.MultipleItemRvAdapter;

import java.util.List;

import io.github.haoyiwen.jinritoutiao.model.entity.News;

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
        return 0;
    }

    @Override
    public void registerItemProvider() {

    }
}
