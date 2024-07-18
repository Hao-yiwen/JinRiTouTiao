package io.github.haoyiwen.jinritoutiao.ui.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.model.entity.News;

public class VideoListAdapter extends BaseQuickAdapter<News, BaseViewHolder> {
    public VideoListAdapter(List<News> mNewsList) {
        super(R.layout.item_video_list);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, News news) {

    }
}
