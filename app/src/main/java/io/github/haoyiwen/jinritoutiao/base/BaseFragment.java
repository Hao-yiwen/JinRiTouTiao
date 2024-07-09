package io.github.haoyiwen.jinritoutiao.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.github.nukc.stateview.StateView;

import org.greenrobot.eventbus.EventBus;

import io.github.haoyiwen.jinritoutiao.R;

public abstract class BaseFragment<T extends BasePresenter, VB extends ViewBinding> extends LazyLoadFragment {
    protected T mPresenter;
    protected VB binding;

    private View rootView;

    protected StateView mStateView;

    private Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            binding = initViewBinding(inflater, container);
            if (binding == null) {
                throw new IllegalStateException("Binding cannot be null");
            }
            rootView = binding.getRoot();

            mStateView = StateView.inject(getStateViewRoot());

            if (mStateView != null) {
                mStateView.setLoadingResource(io.github.haoyiwen.uikit.R.layout.page_loading);
                mStateView.setEmptyResource(io.github.haoyiwen.uikit.R.layout.page_net_error);
            }

            initView(rootView);
            initData();
            initListener();

        } else {
            ViewGroup group = (ViewGroup) rootView.getParent();
            if (group != null) {
                group.removeView(rootView);
            }
        }
        return rootView;
    }

    private void initListener() {
    }

    private void initData() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    private void initView(View rootView) {
    }

    protected View getStateViewRoot() {
        return rootView;
    }

    protected abstract VB initViewBinding(LayoutInflater inflater, ViewGroup container);

    @Override
    protected void onFragmentFirstVisible() {
        loadData();
    }

    protected abstract T createPresenter();

    protected abstract int provideContentViewId();

    protected abstract void loadData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        rootView = null;
    }

    public boolean isEventBusRegisted(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }

    public void registerEventBus(Object subscribe) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe);
        }
    }

    public void unregisterEventBus(Object subscribe) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe);
        }
    }
}
