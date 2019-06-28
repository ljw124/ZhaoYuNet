package com.example.zhaoyu.view.fragment.home;


import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.aliyun.demo.recorder.activity.AlivcSvideoRecordActivity;
import com.aliyun.svideo.sdk.external.struct.snap.AliyunSnapVideoParam;
import com.example.zhaoyu.R;
import com.example.zhaoyu.adapter.PondRVAdapter;
import com.example.zhaoyu.base.BaseFragment;
import com.example.zhaoyu.common.OnViewPagerListener;
import com.example.zhaoyu.common.ViewModelProvider;
import com.example.zhaoyu.common.ViewPagerLayoutManager;
import com.example.zhaoyu.databinding.FragmentPondBinding;
import com.example.zhaoyu.view.activity.PondDetailActivity;
import com.example.zhaoyu.view.activity.RecorderActivity;
import com.example.zhaoyu.viewmodel.PondVM;

public class PondFragment extends BaseFragment {

    private static final String TAG = "PondFragment";
    FragmentPondBinding binding;
    private PondVM vm;
    private RecyclerView recycler;
    private PondRVAdapter mAdapter;
    private ViewPagerLayoutManager mLayoutManager;

    public static PondFragment newInstance() {
        
        Bundle args = new Bundle();
        
        PondFragment fragment = new PondFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pond, container, false);
        binding.setVm(ViewModelProvider.getInstance().get(PondVM.class));
        initView();
        return binding.getRoot();
    }

    private void initView() {
        vm = binding.getVm();
        recycler = binding.recycler;

        mAdapter = new PondRVAdapter(getActivity());
        mLayoutManager = new ViewPagerLayoutManager(getActivity(), OrientationHelper.VERTICAL);
        recycler.setLayoutManager(mLayoutManager);
        recycler.setAdapter(mAdapter);
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int scrollX = 0;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && scrollX > 0){
                    PondDetailActivity.launch(getActivity());
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
                scrollX = dx;
            }
        });

        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {

            @Override
            public void onInitComplete() {
                Log.e(TAG,"onInitComplete");
                playVideo(0);
            }

            @Override
            public void onPageRelease(boolean isNext,int position) {
                Log.e(TAG,"释放位置:" + position + " 下一页:" + isNext);
                int index = 0;
                if (isNext){
                    index = 0;
                }else {
                    index = 1;
                }
                releaseVideo(index);
            }

            @Override
            public void onPageSelected(int position,boolean isBottom) {
                Log.e(TAG,"选中位置:"+position+"  是否是滑动到底部:"+isBottom);
                playVideo(0);
            }
        });

        binding.tvSnapshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AliyunSnapVideoParam recordParam = new AliyunSnapVideoParam.Builder().build();
                AlivcSvideoRecordActivity.startRecord(getActivity(), recordParam,"community");
            }
        });
    }

    private void playVideo(int position) {
        View itemView = recycler.getChildAt(0);
        final RelativeLayout rlRoot = itemView.findViewById(R.id.root_view);
        final VideoView videoView = itemView.findViewById(R.id.video_view);
        final ImageView imgPlay = itemView.findViewById(R.id.img_play);
        final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
        final RelativeLayout rlDetail = itemView.findViewById(R.id.rl_detail);
        rlDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PondDetailActivity.launch(getActivity());
            }
        });
        itemView.findViewById(R.id.tv_heart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecorderActivity.launch(getActivity());
            }
        });
        final MediaPlayer[] mediaPlayer = new MediaPlayer[1];
        videoView.start();
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                mediaPlayer[0] = mp;
                mp.setLooping(true);
                imgThumb.animate().alpha(0).setDuration(200).start();
                return false;
            }
        });
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });

        rlRoot.setOnClickListener(new View.OnClickListener() {
            boolean isPlaying = true;
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()){
                    imgPlay.animate().alpha(1f).start();
                    videoView.pause();
                    isPlaying = false;
                }else {
                    imgPlay.animate().alpha(0f).start();
                    videoView.start();
                    isPlaying = true;
                }
            }
        });
    }

    private void releaseVideo(int index){
        View itemView = recycler.getChildAt(index);
        final VideoView videoView = itemView.findViewById(R.id.video_view);
        final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
        final ImageView imgPlay = itemView.findViewById(R.id.img_play);
        videoView.stopPlayback();
        imgThumb.animate().alpha(1).start();
        imgPlay.animate().alpha(0f).start();
    }
}
