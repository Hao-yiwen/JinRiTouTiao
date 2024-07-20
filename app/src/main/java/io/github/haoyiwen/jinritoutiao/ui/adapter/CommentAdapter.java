package io.github.haoyiwen.jinritoutiao.ui.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.github.haoyiwen.jinritoutiao.model.entity.CommentData;

public class CommentAdapter extends BaseQuickAdapter<CommentData, BaseViewHolder> {
    public CommentAdapter(int layoutResId, @Nullable List<CommentData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CommentData commentDatam) {

    }
}
