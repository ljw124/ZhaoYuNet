package com.example.zhaoyu.view.fragment.position;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhaoyu.R;
import com.example.zhaoyu.base.BaseFragment;
import com.example.zhaoyu.common.ViewModelProvider;
import com.example.zhaoyu.databinding.FragmentModifyJobIntentionBinding;
import com.example.zhaoyu.viewmodel.PositionVM;

public class ModifyJobIntentionFragment extends BaseFragment {

    FragmentModifyJobIntentionBinding binding;

    public static ModifyJobIntentionFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ModifyJobIntentionFragment fragment = new ModifyJobIntentionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_modify_job_intention, container, false);
        binding.setVm(ViewModelProvider.getInstance().get(PositionVM.class));
        initView();
        return binding.getRoot();
    }

    private void initView() {

    }

}
