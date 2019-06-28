package com.example.zhaoyu.view.fragment.city;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhaoyu.R;
import com.example.zhaoyu.adapter.PositionInterestedRVAdapter;
import com.example.zhaoyu.base.BaseFragment;
import com.example.zhaoyu.databinding.FragmentHotCityBinding;

import java.util.ArrayList;
import java.util.List;

public class HotCityFragment extends BaseFragment {

    FragmentHotCityBinding binding;
    private RecyclerView rvHot;
    private RecyclerView rvOften;
    private PositionInterestedRVAdapter mAdapter;

    public static HotCityFragment newInstance() {

        Bundle args = new Bundle();

        HotCityFragment fragment = new HotCityFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hot_city, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        rvHot = binding.rvHot;
        rvOften = binding.rvOften;

        rvHot.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvOften.setLayoutManager(new GridLayoutManager(getContext(), 2));

        List<String> data = new ArrayList<>();
        data.add("杭州");
        data.add("广州");
        data.add("深圳");
        data.add("北京");
        data.add("郑州");
        mAdapter = new PositionInterestedRVAdapter(R.layout.item_city_switch_right, data);
        rvHot.setAdapter(mAdapter);
        rvOften.setAdapter(mAdapter);
    }

}
