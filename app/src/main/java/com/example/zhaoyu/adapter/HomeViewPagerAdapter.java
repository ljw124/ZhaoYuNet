package com.example.zhaoyu.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.zhaoyu.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ljw on 2019/5/22.
 * 因为加载的每个页面的数据量都很多，不适合缓存到本地，所以使用FragmentStatePagerAdapter
 */
public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> fragments;
    private ArrayList<String> titles;

    public HomeViewPagerAdapter(FragmentManager fm,  List<BaseFragment> fragments, ArrayList<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
