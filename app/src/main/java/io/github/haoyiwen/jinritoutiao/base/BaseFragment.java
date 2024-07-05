package io.github.haoyiwen.jinritoutiao.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.github.nukc.stateview.StateView;

import io.github.haoyiwen.jinritoutiao.R;

public abstract class BaseFragment<T extends BasePresenter, VB extends ViewBinding> extends LazyLoadFragment{
    protected T mPresenter;
    protected VB binding;

    private View rootView;

    protected StateView mStateView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            binding = initViewBinding(inflater, container);
            rootView = binding.getRoot();

            mStateView = StateView.inject(getStateViewRoot());

            if(mStateView != null){
//                mStateView.setLoadingResource(R.layout);
            }
        } else {

        }
        return rootView;
    }

    protected View getStateViewRoot(){
        return rootView;
    }

    protected abstract VB initViewBinding(LayoutInflater inflater, ViewGroup container);

    protected abstract T createPresenter();

    protected abstract int provideContentViewId();
}
