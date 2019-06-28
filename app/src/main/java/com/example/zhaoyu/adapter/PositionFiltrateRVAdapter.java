package com.example.zhaoyu.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.zhaoyu.R;

import java.util.List;

/**
 * Created by Ljw on 2019/5/28.
 */
public class PositionFiltrateRVAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements BaseQuickAdapter.OnItemChildClickListener {

    public PositionFiltrateRVAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
