package io.github.haoyiwen.jinritoutiao.ui.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import io.github.haoyiwen.jinritoutiao.base.BaseFragment;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.databinding.FragmentVideoBinding;

public class VideoFragment extends BaseFragment {
    @Override
    protected ViewBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentVideoBinding.inflate(inflater, container, false);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void loadData() {

    }
}
