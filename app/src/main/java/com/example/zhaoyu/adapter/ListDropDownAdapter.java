package com.example.zhaoyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhaoyu.R;

import java.util.List;

/**
 * Created by Ljw on 2019/5/30.
 */
public class ListDropDownAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private int checkItemPosition = 0;

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    public ListDropDownAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_default_drop_down, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.mText.setText(list.get(position));
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_selected));
                viewHolder.mText.setBackgroundResource(R.color.check_bg);
                viewHolder.mIv.setVisibility(View.VISIBLE);
            } else {
                viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                viewHolder.mText.setBackgroundResource(R.color.white);
                viewHolder.mIv.setVisibility(View.INVISIBLE);
            }
        }
    }

    static class ViewHolder {
        TextView mText;
        ImageView mIv;

        ViewHolder(View view) {
            mText = view.findViewById(R.id.tv_select_type);
            mIv = view.findViewById(R.id.iv_yes);
        }
    }
}
