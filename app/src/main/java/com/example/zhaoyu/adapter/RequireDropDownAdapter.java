package com.example.zhaoyu.adapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.zhaoyu.R;
import com.example.zhaoyu.common.Constant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Ljw on 2019/5/30.
 */
public class RequireDropDownAdapter extends BaseAdapter {

    private Context context;
    private List<List<String>> requireData;

    public RequireDropDownAdapter(Context context, List<List<String>> requireData) {
        this.context = context;
        this.requireData = requireData;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_require_drop_down, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, final ViewHolder viewHolder) {
       viewHolder.rvExperience.setLayoutManager(new GridLayoutManager(context, 4));
       viewHolder.rvSalary.setLayoutManager(new GridLayoutManager(context, 4));
       RequireAdapter adapter = new RequireAdapter(context);
       adapter.setGeneralData(requireData.get(0));
       viewHolder.rvExperience.setAdapter(adapter);

       adapter.setGeneralData(requireData.get(1));
       viewHolder.rvSalary.setAdapter(adapter);
    }

    static class ViewHolder {
        RecyclerView rvExperience;
        RecyclerView rvSalary;
        Button btnReset;
        Button btnEnsure;

        ViewHolder(View view) {
            rvExperience = view.findViewById(R.id.rv_experience);
            rvSalary = view.findViewById(R.id.rv_salary);
            btnReset = view.findViewById(R.id.bt_reset);
            btnEnsure = view.findViewById(R.id.bt_confirm);
        }
    }

}
