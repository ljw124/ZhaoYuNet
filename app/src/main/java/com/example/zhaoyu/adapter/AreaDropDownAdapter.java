package com.example.zhaoyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.zhaoyu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ljw on 2019/5/30.
 */
public class AreaDropDownAdapter extends BaseAdapter{

    private Context context;
    private List<String> list;
    private Map<Integer, Boolean> isCheckedMap= new HashMap<>(); //记录条目是否被选中

    public AreaDropDownAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        initCheckData();
        notifyDataSetChanged();
    }

    public void initCheckData(){
        for (int i = 0; i < list.size(); i++){
            isCheckedMap.put(i, false);
        }
    }

    private ICheckBoxSelected checkBoxSelected;
    public void setCheckBoxSelected(ICheckBoxSelected checkBoxSelected){
        this.checkBoxSelected = checkBoxSelected;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AreaDropDownAdapter.ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (AreaDropDownAdapter.ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_area_drop_down, null);
            viewHolder = new AreaDropDownAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(final int position, final AreaDropDownAdapter.ViewHolder viewHolder) {
        viewHolder.cbArea.setText(list.get(position));
        viewHolder.cbArea.setButtonDrawable(R.mipmap.ic_circle_no);
        viewHolder.cbArea.setPadding(12,12,12,12);
        viewHolder.cbArea.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
        viewHolder.cbArea.setOnCheckedChangeListener(null);
        if (isCheckedMap.get(position)){
            viewHolder.cbArea.setTextColor(context.getResources().getColor(R.color.un_press_color));
            viewHolder.cbArea.setButtonDrawable(R.mipmap.ic_circle_yes);
            viewHolder.cbArea.setChecked(true);
        }else {
            viewHolder.cbArea.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
            viewHolder.cbArea.setButtonDrawable(R.mipmap.ic_circle_no);
            viewHolder.cbArea.setChecked(false);
        }

        viewHolder.cbArea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isCheckedMap.put(position, true);
                    viewHolder.cbArea.setTextColor(context.getResources().getColor(R.color.un_press_color));
                    viewHolder.cbArea.setButtonDrawable(R.mipmap.ic_circle_yes);
                    checkBoxSelected.checkBoxSelected(true, position);
                } else {
                    isCheckedMap.put(position, false);
                    viewHolder.cbArea.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                    viewHolder.cbArea.setButtonDrawable(R.mipmap.ic_circle_no);
                    checkBoxSelected.checkBoxSelected(false, position);
                }
            }
        });
    }

    static class ViewHolder {
        CheckBox cbArea;

        ViewHolder(View view) {
            cbArea = view.findViewById(R.id.cb_area);
        }
    }

    interface ICheckBoxSelected{
        void checkBoxSelected(boolean isChecked, int position);
    }
}
