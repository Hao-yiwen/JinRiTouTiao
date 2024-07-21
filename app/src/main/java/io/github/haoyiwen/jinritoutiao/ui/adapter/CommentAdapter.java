package io.github.haoyiwen.jinritoutiao.ui.adapter;

import android.content.Context;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.model.entity.CommentData;
import io.github.haoyiwen.jinritoutiao.utils.GlideUtils;
import io.github.haoyiwen.jinritoutiao.utils.TimeUtils;

public class CommentAdapter extends BaseQuickAdapter<CommentData, BaseViewHolder> {
    private Context mContext;

    public CommentAdapter(Context mContext, int layoutResId, @Nullable List<CommentData> data) {
        super(layoutResId, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CommentData commentDatam) {
        GlideUtils.loadRound(mContext, commentDatam.comment.user_profile_image_url, baseViewHolder.getView(R.id.iv_avatar));
        baseViewHolder.setText(R.id.tv_name, commentDatam.comment.user_name)
                .setText(R.id.tv_like_count, commentDatam.comment.digg_count)
                .setText(R.id.tv_content, commentDatam.comment.text)
                .setText(R.id.tv_time, TimeUtils.getShortTime(commentDatam.comment.create_time * 1000));
    }
}
