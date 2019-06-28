package com.example.zhaoyu.view.fragment.home;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zhaoyu.R;
import com.example.zhaoyu.adapter.HomeFoundRVAdapter;
import com.example.zhaoyu.base.BaseFragment;
import com.example.zhaoyu.common.ViewModelProvider;
import com.example.zhaoyu.databinding.FragmentHomeBinding;
import com.example.zhaoyu.view.activity.SearchActivity;
import com.example.zhaoyu.viewmodel.HomeVM;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    FragmentHomeBinding binding;
    private HomeVM vm;
    private HomeFoundRVAdapter mAdapter;
    private View statusBarView;

    public static HomeFragment newInstance() {
        
        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        setActionBarColor();
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        binding.setVm(ViewModelProvider.getInstance().get(HomeVM.class));
        initView();
        return binding.getRoot();
    }

    private void initView() {
        vm = binding.getVm();
        binding.etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.launch(getActivity());
            }
        });

        startBanner();
        initRecyclerView();
    }

    private void startBanner() {
        List<String> bannerList = new ArrayList<>();
        bannerList.add("android.resource://" + getContext().getPackageName() + "/" + R.raw.banner);
        bannerList.add("android.resource://" + getContext().getPackageName() + "/" + R.raw.banner);
        Banner banner = binding.banner;
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new MyLoader());
        banner.setImages(bannerList);
        banner.setBannerAnimation(Transformer.Default);
        banner.setDelayTime(3000);
        banner.isAutoPlay(true);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Picasso.with(context).load((String)path).into(imageView);
        }
    }

    private void initRecyclerView() {
        List<String> data = new ArrayList<>();
        for (int i=0; i<20; i++){
            data.add("Android开发工程师");
        }
        RecyclerView rv = binding.rv;
        mAdapter = new HomeFoundRVAdapter(R.layout.item_home_found, data);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
//        rv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rv.setAdapter(mAdapter);
    }

    private void setActionBarColor() {
        if (isStatusBar()) {
            initStatusBar();
            getActivity().getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    initStatusBar();
                }
            });
        }
    }

    private void initStatusBar() {
        if (statusBarView == null) {
            int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
            statusBarView =  getActivity().getWindow().findViewById(identifier);
        }
        if (statusBarView != null) {
            statusBarView.setBackgroundResource(R.drawable.action_bar_color);
        }
    }

    protected boolean isStatusBar() {
        return true;
    }
}
