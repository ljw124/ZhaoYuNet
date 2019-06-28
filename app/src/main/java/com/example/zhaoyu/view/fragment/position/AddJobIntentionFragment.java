package com.example.zhaoyu.view.fragment.position;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhaoyu.R;
import com.example.zhaoyu.base.BaseFragment;
import com.example.zhaoyu.common.ViewModelProvider;
import com.example.zhaoyu.databinding.FragmentAddJobIntentionBinding;
import com.example.zhaoyu.viewmodel.PositionVM;

public class AddJobIntentionFragment extends BaseFragment {

    FragmentAddJobIntentionBinding binding;

    public static AddJobIntentionFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AddJobIntentionFragment fragment = new AddJobIntentionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_job_intention, container, false);
        binding.setVm(ViewModelProvider.getInstance().get(PositionVM.class));
        initView();
        return binding.getRoot();
    }

    private void initView() {

    }

}
