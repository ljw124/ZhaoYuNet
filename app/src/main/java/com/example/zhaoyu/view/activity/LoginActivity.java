package com.example.zhaoyu.view.activity;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.zhaoyu.MyApplication;
import com.example.zhaoyu.R;
import com.example.zhaoyu.base.BaseActivity;
import com.example.zhaoyu.common.ViewModelProvider;
import com.example.zhaoyu.databinding.ActivityLoginBinding;
import com.example.zhaoyu.common.StartFragmentEvent;
import com.example.zhaoyu.view.fragment.login.LoginFragment;
import com.example.zhaoyu.viewmodel.LoginVM;
import com.github.zackratos.ultimatebar.UltimateBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding;
    private LoginVM vm;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setVm(ViewModelProvider.getInstance().get(LoginVM.class));
        initView();
    }

    @Override
    public boolean isActionBarColorChange() {
        return true;
    }

    private void initView() {
        loadRootFragment(LoginFragment.newInstance());
        UltimateBar.Companion.with(this)
                .statusDark(false) // 状态栏灰色模式(Android 6.0+)，默认 flase
                .statusDrawable(getResources().getDrawable(R.drawable.action_bar_color)) // 状态栏背景，默认 null
                .applyNavigation(true) // 应用到导航栏，默认 flase
                .navigationDark(false) // 导航栏灰色模式(Android 8.0+)，默认 false
                .navigationDrawable(getResources().getDrawable(R.drawable.action_bar_color)) // 导航栏背景，默认 null
                .create() .drawableBar();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void startFragment(StartFragmentEvent event) {
        showHideFragment(event.fragmentClass, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
