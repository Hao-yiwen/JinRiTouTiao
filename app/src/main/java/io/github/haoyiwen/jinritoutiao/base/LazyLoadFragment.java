package io.github.haoyiwen.jinritoutiao.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class LazyLoadFragment extends Fragment {

    private static final String TAG = LazyLoadFragment.class.getSimpleName();

    private View rootView;

    private boolean isFirstEnter = true;
    private boolean isFragmentVisible;

    private boolean isReuseView = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        if (isFirstEnter && isVisibleToUser) {
            onFragmentFirstVisible();
            isFirstEnter = false;
        }
        if (isVisibleToUser) {
            isFragmentVisible = true;
            onFragmentVisibleChange(isFragmentVisible);
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onFragmentVisibleChange(isFragmentVisible);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void onFragmentFirstVisible() {
    }

    protected void onFragmentVisibleChange(boolean isVisible) {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = view;
            if (getUserVisibleHint()) {
                if (isFirstEnter) {
                    onFragmentFirstVisible();
                    isFirstEnter = false;
                }
                onFragmentVisibleChange(true);
                isFragmentVisible = true;
            }
        }
        super.onViewCreated(isReuseView ? rootView : view, savedInstanceState);
    }

    public void setReuseView(boolean reuseView) {
        isReuseView = reuseView;
    }

    public void setFragmentVisible(boolean fragmentVisible) {
        isFragmentVisible = fragmentVisible;
    }

    public boolean isFragmentVisible() {
        return isFragmentVisible;
    }

    private void resetVariable(){
        isFirstEnter = true;
        isReuseView = true;
        isFragmentVisible = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        resetVariable();
    }
}
