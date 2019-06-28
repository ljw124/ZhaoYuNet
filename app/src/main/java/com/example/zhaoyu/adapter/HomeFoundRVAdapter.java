package com.example.zhaoyu.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zhaoyu.R;

import java.util.List;

/**
 * Created by Ljw on 2019/5/23.
 */
public class HomeFoundRVAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements BaseQuickAdapter.OnItemChildClickListener {

    public HomeFoundRVAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_position_name, item);
//        helper.setText(R.id.tv_salary, item);
//        helper.setText(R.id.tv_company_name, item);
//        helper.setText(R.id.tv_recruiter_name, item);
//        helper.setText(R.id.tv_location, item);
//        helper.setText(R.id.tv_experience, item);
//        helper.setText(R.id.tv_education, item);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
