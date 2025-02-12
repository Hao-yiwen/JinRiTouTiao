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
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.github.haoyiwen.jinritoutiao.R;
import io.github.haoyiwen.jinritoutiao.model.event.TabRefreshEvent;

public abstract class BaseFragment<T extends BasePresenter, VB extends ViewBinding> extends LazyLoadFragment {
    protected T mPresenter;
    protected VB binding;

    private View rootView;

    protected StateView mStateView;

    protected Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            binding = initViewBinding(inflater, container);
            rootView = binding.getRoot();
            initBinding();

            Logger.i("onCreateView: " + getClass().getSimpleName());
            if (rootView != null) {

                View stateViewRoot = getStateViewRoot();
                if (stateViewRoot.getLayoutParams() == null) {
                    stateViewRoot.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    ));
                }
                mStateView = StateView.inject(stateViewRoot);
            }

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

    public void initBinding() {
    }

    public void initListener() {
    }

    public void initData() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    protected void initView(View rootView) {
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
