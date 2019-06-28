package com.example.zhaoyu.view.activity;

import android.app.Activity;
import android.app.SearchableInfo;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.zhaoyu.MyApplication;
import com.example.zhaoyu.R;
import com.example.zhaoyu.adapter.PositionCompanyRVAdapter;
import com.example.zhaoyu.adapter.PositionFiltrateRVAdapter;
import com.example.zhaoyu.adapter.SearchHistoryRVAdapter;
import com.example.zhaoyu.base.BaseActivity;
import com.example.zhaoyu.bean.SearchHistoryInfo;
import com.example.zhaoyu.common.LocationService;
import com.example.zhaoyu.common.ViewModelProvider;
import com.example.zhaoyu.databinding.ActivitySearchBinding;
import com.example.zhaoyu.utils.KeyBoardUtil;
import com.example.zhaoyu.utils.StringUtil;
import com.example.zhaoyu.viewmodel.SearchVM;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    ActivitySearchBinding binding;
    private SearchVM vm;
    private RecyclerView rvHistory;
    private RecyclerView rvSearch;
    private SearchHistoryRVAdapter mAdapter;
    private List<String> keywords;
    private TabLayout tabType;
    private PositionFiltrateRVAdapter mFiltrateAdapter;
    private PositionCompanyRVAdapter mCompanyAdapter;
    private LocationService locationService; //定位服务

    public static void launch(Activity context){
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.setVm(ViewModelProvider.getInstance().get(SearchVM.class));
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationService = ((MyApplication) getApplication()).locationService;
        locationService.registerListener(myLocationListener);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();
    }

    private void initView() {
        vm = binding.getVm();
        rvHistory = binding.rvHistory;
        rvSearch = binding.rvSearch;
        tabType = binding.tabType;
        keywords = new ArrayList<>();

        rvHistory.setLayoutManager(new GridLayoutManager(this, 4));
        rvSearch.setLayoutManager(new LinearLayoutManager(this));

        binding.btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CitySwitchActivity.launch(SearchActivity.this);
            }
        });

        tabType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        initSearchData();
                        break;
                    case 1:
                        initCompanyData();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String keyword = binding.etSearch.getText().toString().trim();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (StringUtil.isNotEmpty(keyword)){
                        //搜索的关键词添加到数据库
                        addKeywordToDatabase(keyword);
                        //展示搜索结果
                        KeyBoardUtil.hideKeyboard(SearchActivity.this);
                        rvHistory.setVisibility(View.GONE);
                        binding.rlHistory.setVisibility(View.GONE);
                        rvSearch.setVisibility(View.VISIBLE);
                        tabType.setVisibility(View.VISIBLE);
                        initSearchData();
                    }
                }
                return false;
            }
        });
        binding.ibClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.deleteAll(SearchHistoryInfo.class);
                keywords.clear();
                mAdapter = new SearchHistoryRVAdapter(R.layout.item_search_history, keywords);
                rvHistory.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        });

        initHistoryData();
    }

    public void addKeywordToDatabase(String keyword){
        boolean isContains = false;
        List<SearchHistoryInfo> historyInfo = LitePal.findAll(SearchHistoryInfo.class);
        for (int i=0; i<historyInfo.size(); i++){
            if (historyInfo.get(i).getKeyword().equals(keyword)){
                isContains = true;
                return;
            }
        }
        if (!isContains){
            SearchHistoryInfo historyKeyword = new SearchHistoryInfo();
            historyKeyword.setKeyword(keyword);
            historyKeyword.save();
        }
    }

    public void initHistoryData(){
        List<SearchHistoryInfo> historyInfo = LitePal.findAll(SearchHistoryInfo.class);
        for (int i=0; i<historyInfo.size(); i++){
            keywords.add(historyInfo.get(i).getKeyword());
        }
        mAdapter = new SearchHistoryRVAdapter(R.layout.item_search_history, keywords);
        rvHistory.setAdapter(mAdapter);
    }

    public void initSearchData(){
        List<String> data = new ArrayList<>();
        for (int i=0; i<20; i++){
            data.add("Android开发工程师");
        }
        mFiltrateAdapter = new PositionFiltrateRVAdapter(R.layout.item_position_rv, data);
        rvSearch.setAdapter(mFiltrateAdapter);
        mFiltrateAdapter.notifyDataSetChanged();
    }

    public void initCompanyData(){
        List<String> data = new ArrayList<>();
        for (int i=0; i<20; i++){
            data.add("Android开发工程师");
        }
        mCompanyAdapter = new PositionCompanyRVAdapter(R.layout.item_position_company, data);
        rvSearch.setAdapter(mCompanyAdapter);
        mCompanyAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isActionBarColorChange() {
        return true;
    }

    private BDAbstractLocationListener myLocationListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            StringBuilder sb = new StringBuilder();
            sb.append(location.getProvince());
            sb.append(location.getCity());
            sb.append(location.getDistrict());
            sb.append(location.getStreet());
            sb.append(location.getStreetNumber());
            Log.e("================", sb.toString());

            String city = location.getCity();
            binding.tvCity.setText(StringUtil.isEmpty(city) ? "杭州" : city );
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        locationService.unregisterListener(myLocationListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }
}
