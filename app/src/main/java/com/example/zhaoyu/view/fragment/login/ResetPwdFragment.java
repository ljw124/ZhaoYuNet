package com.example.zhaoyu.view.fragment.login;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhaoyu.R;
import com.example.zhaoyu.base.BaseFragment;
import com.example.zhaoyu.common.ViewModelProvider;
import com.example.zhaoyu.databinding.FragmentResetPwdBinding;
import com.example.zhaoyu.viewmodel.LoginVM;

public class ResetPwdFragment extends BaseFragment implements TextWatcher {

    FragmentResetPwdBinding binding;
    private LoginVM vm;

    public static ResetPwdFragment newInstance() {

        Bundle args = new Bundle();

        ResetPwdFragment fragment = new ResetPwdFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reset_pwd, container, false);
        binding.setVm(ViewModelProvider.getInstance().get(LoginVM.class));
        initView();
        return binding.getRoot();
    }

    private void initView() {
        vm = binding.getVm();
        binding.etPhone.addTextChangedListener(this);
        binding.etPwd.addTextChangedListener(this);
        binding.etCode.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        vm.isShowTips.set(false);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
