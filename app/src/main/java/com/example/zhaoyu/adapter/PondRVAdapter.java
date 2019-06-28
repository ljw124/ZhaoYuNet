package com.example.zhaoyu.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.zhaoyu.R;
import com.example.zhaoyu.view.activity.RecorderActivity;

/**
 * Created by Ljw on 2019/6/18.
 */
public class PondRVAdapter extends RecyclerView.Adapter<PondRVAdapter.ViewHolder>{

    private int[] imgs = {R.mipmap.img_video_1, R.mipmap.img_video_2};
    private int[] videos = {R.raw.video_1, R.raw.video_2};

    Context context;
    public PondRVAdapter(Context context){
        this.context = context;
    }

    @Override
    public PondRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pond_vp, parent,false);
        return new PondRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PondRVAdapter.ViewHolder holder, int position) {
        holder.img_thumb.setImageResource(imgs[position%2]);
        holder.videoView.setVideoURI(Uri.parse("android.resource://" + context.getPackageName() + "/"+ videos[position%2]));
        holder.tvMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "评论", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_thumb;
        VideoView videoView;
        ImageView img_play;
        RelativeLayout rootView;
        TextView tvHeart;
        TextView tvMsg;
        TextView tvShare;
        public ViewHolder(View itemView) {
            super(itemView);
            img_thumb = itemView.findViewById(R.id.img_thumb);
            videoView = itemView.findViewById(R.id.video_view);
            img_play = itemView.findViewById(R.id.img_play);
            rootView = itemView.findViewById(R.id.root_view);
            tvHeart = itemView.findViewById(R.id.tv_heart);
            tvMsg = itemView.findViewById(R.id.tv_msg);
            tvShare = itemView.findViewById(R.id.tv_share);
        }
    }
}