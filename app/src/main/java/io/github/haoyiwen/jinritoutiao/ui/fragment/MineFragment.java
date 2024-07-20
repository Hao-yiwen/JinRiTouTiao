package io.github.haoyiwen.jinritoutiao.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import com.orhanobut.logger.Logger;

import io.github.haoyiwen.jinritoutiao.base.BaseFragment;
import io.github.haoyiwen.jinritoutiao.base.BasePresenter;
import io.github.haoyiwen.jinritoutiao.databinding.FragmentMineBinding;

public class MineFragment extends BaseFragment<BasePresenter, FragmentMineBinding> {
    @Override
    protected FragmentMineBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMineBinding.inflate(inflater, container, false);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initView(View rootView) {
        Logger.i("initView");
    }

    @Override
    public void initData() {
        Logger.i("initData");
    }

    @Override
    public void initListener() {
        Logger.i("initListener");
    }

    @Override
    public void loadData() {
        Logger.i("loadData");
    }
}
