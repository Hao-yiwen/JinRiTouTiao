package io.github.haoyiwen.jinritoutiao.ui.activities;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.databinding.ActivityNewsDetailBinding;
import io.github.haoyiwen.jinritoutiao.model.response.NewDetail;
import io.github.haoyiwen.jinritoutiao.utils.UIUtils;
import io.github.haoyiwen.uikit.statusbar.Eyes;

public class NewsDetailActivity extends NewsDetailBaseActivity<ActivityNewsDetailBinding> {
    @Override
    protected Class getBindingClass() {
        return ActivityNewsDetailBinding.class;
    }

    @Override
    public void onGetNewsDetailSuccess(NewDetail newDetail) {

    }

    @Override
    public void initView() {
        super.initView();
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_BDBDBD));
    }

    @Override
    protected void initBinding() {

    }
}
