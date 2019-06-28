package com.example.zhaoyu.view.fragment.city;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhaoyu.R;
import com.example.zhaoyu.base.BaseFragment;
import com.example.zhaoyu.databinding.FragmentSelectCityBinding;

public class SelectCityFragment extends BaseFragment {

    FragmentSelectCityBinding binding;

    public static SelectCityFragment newInstance() {

        Bundle args = new Bundle();

        SelectCityFragment fragment = new SelectCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_city, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {

    }

}
