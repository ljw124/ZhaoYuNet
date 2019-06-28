package com.example.zhaoyu.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zhaoyu.R;
import com.example.zhaoyu.base.BaseActivity;
import com.example.zhaoyu.common.StartFragmentEvent;
import com.example.zhaoyu.common.ViewModelProvider;
import com.example.zhaoyu.databinding.ActivityJobIntentionBinding;
import com.example.zhaoyu.view.fragment.login.LoginFragment;
import com.example.zhaoyu.view.fragment.position.ManagerJobIntentionFragment;
import com.example.zhaoyu.viewmodel.PositionVM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class JobIntentionActivity extends BaseActivity {

    ActivityJobIntentionBinding binding;
    private PositionVM vm;

    public static void launch(Activity context){
        Intent intent = new Intent(context, JobIntentionActivity.class);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_job_intention);
        binding.setVm(ViewModelProvider.getInstance().get(PositionVM.class));
        initView();
    }

    private void initView() {
        vm = binding.getVm();
        loadRootFragment(ManagerJobIntentionFragment.newInstance());
    }

    @Override
    public boolean isActionBarColorChange() {
        return false;
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
