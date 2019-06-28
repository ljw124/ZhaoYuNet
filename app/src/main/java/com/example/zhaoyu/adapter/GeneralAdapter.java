package com.example.zhaoyu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhaoyu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ljw on 2019/6/1.
 */
public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.MyViewHolder> {

    private Context context;
    protected LayoutInflater inflater;
    private List<String> data;
    private List<Boolean> isClicks;
    public GeneralAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setGeneralData(List<String> data){
        this.data = data;
        isClicks = new ArrayList<>();
        for(int i = 0; i < data.size(); i++){
            isClicks.add(false);
        }
    }

    private IOnItemClickListener itemClickListener;
    public void setOnItemClickListener(IOnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = inflater.inflate(R.layout.item_city_switch, viewGroup, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv.setText(data.get(position));
        holder.itemView.setTag(position);
        if (itemClickListener != null){
            holder.llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    for(int i = 0; i <isClicks.size();i++){
                        isClicks.set(i, false);
                    }
                    isClicks.set(position, true);
                    notifyDataSetChanged();
                    itemClickListener.itemClick(v, position);
                }
            });
            holder.itemView.setTag(holder.tv);
            if(isClicks.get(position)){
                holder.llRoot.setBackgroundColor(Color.parseColor("#F4F5FA"));
            }else{
                holder.llRoot.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv;
        LinearLayout llRoot;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_interested);
            llRoot = itemView.findViewById(R.id.ll_root);
        }
    }

    public interface IOnItemClickListener{
        void itemClick(View v, int position);
    }
}
