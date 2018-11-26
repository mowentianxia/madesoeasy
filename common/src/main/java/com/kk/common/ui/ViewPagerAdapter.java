package com.kk.common.ui;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter<T extends Fragment> extends FragmentPagerAdapter {

    private List<T> fragments;
    private List<String> titles;

    public ViewPagerAdapter(FragmentManager fm, List<T> fragments) {
        this(fm, fragments, null);
    }

    public ViewPagerAdapter(FragmentManager fm, List<T> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public T getItem(int position) {
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null) {
            return titles.get(position);
        }
        return super.getPageTitle(position);
    }
}
