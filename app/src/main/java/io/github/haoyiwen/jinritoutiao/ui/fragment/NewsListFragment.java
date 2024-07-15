package io.github.haoyiwen.jinritoutiao.ui.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import io.github.haoyiwen.jinritoutiao.base.BaseFragment;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.databinding.FragmentNewsListBinding;

public class NewsListFragment extends BaseFragment {
    @Override
    protected ViewBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentNewsListBinding.inflate(inflater, container, false);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return 0;
    }

    @Override
    protected void loadData() {

    }
}
