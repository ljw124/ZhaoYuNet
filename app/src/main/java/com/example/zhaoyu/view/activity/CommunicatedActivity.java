package com.example.zhaoyu.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.zhaoyu.R;
import com.example.zhaoyu.adapter.PositionFiltrateRVAdapter;
import com.example.zhaoyu.base.BaseActivity;
import com.example.zhaoyu.common.ViewModelProvider;
import com.example.zhaoyu.databinding.ActivityCommunicatedBinding;
import com.example.zhaoyu.viewmodel.MyVM;

import java.util.ArrayList;
import java.util.List;

public class CommunicatedActivity extends BaseActivity {

    ActivityCommunicatedBinding binding;
    private RecyclerView rvCommunicated;
    private PositionFiltrateRVAdapter mAdapter;

    public static void launch(Activity context){
        Intent intent = new Intent(context, CommunicatedActivity.class);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_communicated, null);
        binding.setVm(ViewModelProvider.getInstance().get(MyVM.class));
        initView();
    }

    private void initView() {
        binding.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rvCommunicated = binding.rvCommunicated;

        List<String> data = new ArrayList<>();
        for (int i=0; i<20; i++){
            data.add("Android开发工程师");
        }
        mAdapter = new PositionFiltrateRVAdapter(R.layout.item_position_rv, data);
        rvCommunicated.setLayoutManager(new LinearLayoutManager(this));
        rvCommunicated.setAdapter(mAdapter);
    }

    @Override
    public boolean isActionBarColorChange() {
        return true;
    }
}
