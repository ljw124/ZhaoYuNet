package com.example.zhaoyu.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.zhaoyu.R;
import com.example.zhaoyu.adapter.GeneralAdapter;
import com.example.zhaoyu.adapter.PositionInterestedRVAdapter;
import com.example.zhaoyu.base.BaseActivity;
import com.example.zhaoyu.common.Constant;
import com.example.zhaoyu.common.StartFragmentEvent;
import com.example.zhaoyu.databinding.ActivityCitySwitchBinding;
import com.example.zhaoyu.view.fragment.city.HotCityFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class CitySwitchActivity extends BaseActivity {

    private ActivityCitySwitchBinding binding;
    private RecyclerView rvCity;
    private GeneralAdapter mAdapter;

    public static void launch(Activity context){
        Intent intent = new Intent(context, CitySwitchActivity.class);
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_city_switch);
        initView();
    }

    private void initView() {
        rvCity = binding.rvCity;
        loadRootFragment(HotCityFragment.newInstance());

        rvCity.setLayoutManager(new LinearLayoutManager(this));
        List<String> data = new ArrayList<>();
        data.add("杭州");
        data.add("广州");
        data.add("深圳");
        data.add("北京");
        data.add("郑州");
        mAdapter = new GeneralAdapter(CitySwitchActivity.this);
        mAdapter.setGeneralData(data);
        rvCity.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new GeneralAdapter.IOnItemClickListener() {
            @Override
            public void itemClick(View v, int position) {

            }
        });

        binding.ibBack.setOnClickListener(new View.OnClickListener() {
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
