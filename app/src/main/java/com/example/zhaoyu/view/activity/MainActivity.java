package com.example.zhaoyu.view.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhaoyu.R;
import com.example.zhaoyu.adapter.HomeViewPagerAdapter;
import com.example.zhaoyu.base.BaseActivity;
import com.example.zhaoyu.base.BaseFragment;
import com.example.zhaoyu.common.ViewModelProvider;
import com.example.zhaoyu.databinding.ActivityMainBinding;
import com.example.zhaoyu.view.fragment.home.HomeFragment;
import com.example.zhaoyu.view.fragment.home.MessageFragment;
import com.example.zhaoyu.view.fragment.home.MyFragment;
import com.example.zhaoyu.view.fragment.home.PondFragment;
import com.example.zhaoyu.view.fragment.home.PositionFragment;
import com.example.zhaoyu.viewmodel.HomeVM;
import com.example.zhaoyu.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    private List<BaseFragment> mFragments;
    private HomeViewPagerAdapter mAdapter;
    private CustomViewPager vpHome;

    //底部导航栏的文字和图片
    private String[] mTitles = {"首页", "职位", "消息", "娱塘", "我的"};
    private int[] mImages = {R.drawable.home_bar_image_select, R.drawable.position_bar_image_select,
            R.drawable.message_bar_image_selectl, R.drawable.pond_bar_image_select, R.drawable.my_bar_image_select};
    private TabLayout tab;
    private TextView tvDivide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setVm(ViewModelProvider.getInstance().get(HomeVM.class));
        initView();
    }

    @Override
    public boolean isActionBarColorChange() {
        return false;
    }

    private void initView() {
        vpHome = binding.vpHome;
        tab = binding.tab;
        tvDivide = binding.tvDivide;
        //设置tab的文字
        ArrayList<String> titles = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            titles.add(mTitles[i]);
        }

        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(PositionFragment.newInstance());
        mFragments.add(MessageFragment.newInstance());
        mFragments.add(PondFragment.newInstance());
        mFragments.add(MyFragment.newInstance());

        mAdapter = new HomeViewPagerAdapter(getSupportFragmentManager(), mFragments, titles);

        vpHome.setAdapter(mAdapter);
        vpHome.setCurrentItem(0);
        vpHome.setOffscreenPageLimit(1);
        tab.setupWithViewPager(vpHome);
        tab.getTabAt(0).isSelected();
        tab.setSelectedTabIndicatorHeight(0);
        //自定义tab样式
        for (int i = 0; i < mTitles.length; i++) {
            TabLayout.Tab tabAt = tab.getTabAt(i);
            View inflate = View.inflate(this, R.layout.layout_tab, null);
            ImageView mTabCustomImage = inflate.findViewById(R.id.tab_custom_image);
            TextView mTabCustomText = inflate.findViewById(R.id.tab_custom_text);
            mTabCustomText.setText(mTitles[i]);
            mTabCustomImage.setImageResource(mImages[i]);
            tabAt.setCustomView(inflate);
        }

        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab t) {
                if (t.getPosition() == 3){
                    tab.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    tab.setBackgroundColor(Color.WHITE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
