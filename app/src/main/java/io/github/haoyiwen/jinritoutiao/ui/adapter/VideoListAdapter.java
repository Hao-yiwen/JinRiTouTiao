package io.github.haoyiwen.jinritoutiao.ui.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.model.entity.News;
import io.github.haoyiwen.jinritoutiao.utils.GlideUtils;
import io.github.haoyiwen.jinritoutiao.utils.MyJZVideoPlayerStandard;
import io.github.haoyiwen.jinritoutiao.utils.UIUtils;

public class VideoListAdapter extends BaseQuickAdapter<News, BaseViewHolder> {
    public VideoListAdapter(List<News> mNewsList) {
        super(R.layout.item_video_list);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, News news) {
        if (TextUtils.isEmpty(news.title)) {
            return;
        }

        baseViewHolder.setVisible(R.id.ll_title, true);
        baseViewHolder.setText(R.id.tv_title, news.title);

        MyJZVideoPlayerStandard videoplayer = baseViewHolder.getView(R.id.video_player);
        if (news.video_detail_info != null) {
            String format = UIUtils.getString(R.string.video_play_count);
            int watchCount = news.video_detail_info.video_watch_count;
            String countUnit = "";
            if (watchCount > 10000) {
                watchCount = watchCount / 10000;
                countUnit = "万";
            }
            baseViewHolder.setText(R.id.tv_watch_count, String.format(format, watchCount + countUnit));

//            GlideUtils.load(mContext, news.video_detail_info.detail_video_large_image.url, videoplayer.thum, R.color.color_d8d8d8);
        }
    }
}
