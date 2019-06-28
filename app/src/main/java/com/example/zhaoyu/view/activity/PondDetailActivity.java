package com.example.zhaoyu.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.example.zhaoyu.R;
import com.example.zhaoyu.base.BaseActivity;
import com.example.zhaoyu.databinding.ActivityPondDetailBinding;
import com.example.zhaoyu.viewmodel.PondVM;

public class PondDetailActivity extends BaseActivity {

    ActivityPondDetailBinding binding;
    private PondVM vm;

    public static void launch(Activity context){
        Intent intent = new Intent(context, PondDetailActivity.class);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pond_detail);
        initView();
    }

    private void initView() {
        vm = binding.getVm();

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean isActionBarColorChange() {
        return true;
    }
}
