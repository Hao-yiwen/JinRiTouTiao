package io.github.haoyiwen.jinritoutiao.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import com.orhanobut.logger.Logger;

import io.github.haoyiwen.jinritoutiao.base.BaseFragment;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.databinding.FragmentMicroBinding;

public class MicroFragment extends BaseFragment<BasePresenter, FragmentMicroBinding> {
    @Override
    protected FragmentMicroBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMicroBinding.inflate(inflater, container, false);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void loadData() {
        Logger.e("loadData");
    }

    @Override
    protected void initView(View rootView) {
        Logger.e("initView");
    }

    @Override
    public void initData() {
        Logger.e("initData");
    }

    @Override
    public void initListener() {
        Logger.e("initListener");
    }
}
