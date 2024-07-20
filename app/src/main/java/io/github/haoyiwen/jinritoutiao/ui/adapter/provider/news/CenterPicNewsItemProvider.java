package io.github.haoyiwen.jinritoutiao.ui.adapter.provider.news;

import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.model.entity.News;
import io.github.haoyiwen.jinritoutiao.ui.adapter.NewsListAdapter;
import io.github.haoyiwen.jinritoutiao.utils.GlideUtils;
import io.github.haoyiwen.jinritoutiao.utils.TimeUtils;
import io.github.haoyiwen.jinritoutiao.utils.UIUtils;

public class CenterPicNewsItemProvider extends BaseNewsItemProvider {
    public CenterPicNewsItemProvider(String mChannelCode) {
        super(mChannelCode);
    }

    @Override
    protected void setData(BaseViewHolder baseViewHolder, News news) {
        // 中间大图布局 查看是否有视频
        TextView tvBottomRight = baseViewHolder.getView(R.id.tv_bottom_right);
        if (news.has_video) {
            baseViewHolder.setVisible(R.id.iv_play, true);
            tvBottomRight.setCompoundDrawables(null, null, null, null);
            baseViewHolder.setText(R.id.tv_bottom_right, TimeUtils.secToTime(news.video_duration));
            GlideUtils.load(mContext, news.video_detail_info.detail_video_large_image.url, baseViewHolder.getView(R.id.iv_img));
        } else {
            baseViewHolder.setVisible(R.id.iv_play, false);
            if (news.gallary_image_count == 1) {
                tvBottomRight.setCompoundDrawables(null, null, null, null);//去除TextView左侧图标
            } else {
                tvBottomRight.setCompoundDrawables(mContext.getResources().getDrawable(R.mipmap.icon_picture_group), null, null, null);//TextView增加左侧图标
                baseViewHolder.setText(R.id.tv_bottom_right, news.gallary_image_count + UIUtils.getString(R.string.img_unit));//设置图片数
            }
            GlideUtils.load(mContext, news.image_list.get(0).url.replace("list/300x196", "large"), baseViewHolder.getView(R.id.iv_img));//中间图片使用image_list第一张
        }
    }

    @Override
    public int viewType() {
        return NewsListAdapter.CENTER_SINGLE_PIC_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_center_pic_news;
    }
}
