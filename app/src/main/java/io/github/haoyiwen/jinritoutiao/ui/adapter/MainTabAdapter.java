package io.github.haoyiwen.jinritoutiao.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import io.github.haoyiwen.jinritoutiao.base.BaseFragment;

public class MainTabAdapter extends FragmentStateAdapter {
    private List<BaseFragment> mFragments = new ArrayList<>();

    public MainTabAdapter(List<BaseFragment> fragmentList, FragmentActivity fm) {
        super(fm);
        if (fragmentList != null) {
            mFragments = fragmentList;
        }
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }
}
