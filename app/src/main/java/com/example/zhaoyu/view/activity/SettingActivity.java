package com.example.zhaoyu.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.zhaoyu.R;
import com.example.zhaoyu.base.BaseActivity;
import com.example.zhaoyu.common.ViewModelProvider;
import com.example.zhaoyu.databinding.ActivitySettingBinding;
import com.example.zhaoyu.viewmodel.MyVM;

public class SettingActivity extends BaseActivity {

    ActivitySettingBinding binding;

    public static void launch(Activity context){
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting, null);
        binding.setVm(ViewModelProvider.getInstance().get(MyVM.class));
        initView();
    }

    private void initView() {
        binding.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();            }
        });
    }

    @Override
    public boolean isActionBarColorChange() {
        return true;
    }
}
