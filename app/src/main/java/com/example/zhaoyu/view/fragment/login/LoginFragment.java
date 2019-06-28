package com.example.zhaoyu.view.fragment.login;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zhaoyu.R;
import com.example.zhaoyu.base.BaseFragment;
import com.example.zhaoyu.common.VMEventListener;
import com.example.zhaoyu.common.ViewModelProvider;
import com.example.zhaoyu.databinding.FragmentLoginBinding;
import com.example.zhaoyu.view.activity.MainActivity;
import com.example.zhaoyu.viewmodel.LoginVM;

public class LoginFragment extends BaseFragment implements TextWatcher {

    FragmentLoginBinding binding;
    private LoginVM vm;

    public static LoginFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.setVm(ViewModelProvider.getInstance().get(LoginVM.class));
        initView();
        return binding.getRoot();
    }

    private void initView() {
        vm = binding.getVm();
        vm.isCodeLogin.set(true);
        binding.btnCodeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vm.isCodeLogin.get()){
                    vm.isCodeLogin.set(false);
                } else {
                    vm.isCodeLogin.set(true);
                }
            }
        });
        binding.etPhone.addTextChangedListener(this);
        binding.etPwd.addTextChangedListener(this);

        vm.setVmEventListener(new VMEventListener() {
            @Override
            public void onSuccess(int code) {
                switch (code){
                    case VM_EVENT_LOGIN_SUCCESS:
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        break;
                    case VM_EVENT_HX_LOGIN_SUCCESS:
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "环信登录成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
