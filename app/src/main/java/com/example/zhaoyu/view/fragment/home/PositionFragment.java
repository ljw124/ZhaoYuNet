package com.example.zhaoyu.view.fragment.home;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhaoyu.R;
import com.example.zhaoyu.adapter.DoubleListDropDownAdapter;
import com.example.zhaoyu.adapter.ListDropDownAdapter;
import com.example.zhaoyu.adapter.PositionCompanyRVAdapter;
import com.example.zhaoyu.adapter.PositionFiltrateRVAdapter;
import com.example.zhaoyu.adapter.PositionInterestedRVAdapter;
import com.example.zhaoyu.adapter.RequireDropDownAdapter;
import com.example.zhaoyu.base.BaseFragment;
import com.example.zhaoyu.bean.SubwayBean;
import com.example.zhaoyu.common.Constant;
import com.example.zhaoyu.common.ViewModelProvider;
import com.example.zhaoyu.databinding.FragmentPositionBinding;
import com.example.zhaoyu.view.activity.CitySwitchActivity;
import com.example.zhaoyu.view.activity.JobIntentionActivity;
import com.example.zhaoyu.view.activity.SearchActivity;
import com.example.zhaoyu.viewmodel.PositionVM;
import com.example.zhaoyu.widget.NoScrollListView;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionFragment extends BaseFragment{

    FragmentPositionBinding binding;
    private PositionVM vm;
    private View statusBarView;
    private PositionInterestedRVAdapter mAdapter;
    private PositionFiltrateRVAdapter mFiltrateAdapter;
    private PositionCompanyRVAdapter mCompanyAdapter;
    private RecyclerView rvInterested;
    private String headers[] = {"推荐", "城市", "要求"};

    private DropDownMenu mDropDownMenu;
    private RecyclerView rvFiltrateContentView;
    private List<View> popupViews = new ArrayList<>();
    private ListDropDownAdapter cityAdapter;
    private DoubleListDropDownAdapter doubleListAdapter;
    private RequireDropDownAdapter requireAdapter;

    private String recommend[] = {"推荐", "最新"};
    private String sexs[] = {"不限", "男", "女"};
    private Map<List<String>, List<List<String>>> city = new HashMap<>(); //城市数据
    private Map<List<String>, List<List<SubwayBean.LinesListBean.StepsListBean>>> citySubway = new HashMap<>(); //某个城市对应的地铁数据
    private List<List<String>> requireData = new ArrayList<>(); //要求数据
    private Button btnCompany;
    private Button btnPosition;
    private RecyclerView rvCompany;

    public static PositionFragment newInstance() {

        Bundle args = new Bundle();

        PositionFragment fragment = new PositionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        setActionBarColor();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_position, container, false);
        binding.setVm(ViewModelProvider.getInstance().get(PositionVM.class));
        initView();
        return binding.getRoot();
    }

    private void initView() {
        vm = binding.getVm();
        rvInterested = binding.rvInterested;
        mDropDownMenu = binding.dropDownMenu;
        rvFiltrateContentView = binding.rvPosition;
        btnCompany = binding.btnCompany;
        btnPosition = binding.btnPosition;
        rvCompany = binding.rvCompany;

        btnCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnCompany.setBackgroundResource(R.drawable.shape_switch_selected);
                binding.btnPosition.setBackgroundColor(Color.TRANSPARENT);
                vm.isCompany.set(true);
            }
        });
        btnPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnPosition.setBackgroundResource(R.drawable.shape_switch_selected);
                binding.btnCompany.setBackgroundColor(Color.TRANSPARENT);
                vm.isCompany.set(false);
            }
        });
        binding.ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.launch(getActivity());
            }
        });
        binding.ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobIntentionActivity.launch(getActivity());
            }
        });

        citySubway = vm.getSubwayJson();

        initRecyclerView();
        initSelect();
    }

    private void initRecyclerView() {
        List<String> data = new ArrayList<>();
        data.add("Android"); data.add("IOS"); data.add("Java");
        mAdapter = new PositionInterestedRVAdapter(R.layout.item_position_interested, data);
        rvInterested.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvInterested.setAdapter(mAdapter);
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
            statusBarView.setBackgroundResource(R.drawable.action_bar_color1);
        }
    }

    protected boolean isStatusBar() {
        return true;
    }

    private void initSelect() {
        city = vm.getCityAllData();

        List<String> shi = vm.getCityData();

        /************************要求数据**********************/
        List<String> experience = new ArrayList<>();
        experience.add("全部");
        experience.add("应届生");
        experience.add("1年以内");
        experience.add("1-3年");
        List<String> salary = new ArrayList<>();
        salary.add("全部");
        salary.add("5K-10K");
        salary.add("10K-15K");
        salary.add("15K-20K");
        salary.add("20K-25K");
        requireData.add(experience);
        requireData.add(salary);

        final NoScrollListView recommendView = new NoScrollListView(getContext());
        cityAdapter = new ListDropDownAdapter(getContext(), Arrays.asList(recommend));
        recommendView.setDividerHeight(0);
        recommendView.setAdapter(cityAdapter);

        final NoScrollListView cityView = new NoScrollListView(getContext());
        doubleListAdapter = new DoubleListDropDownAdapter(getContext(), city);
        doubleListAdapter.setSubwayData(citySubway);
        cityView.setFocusable(false);
        cityView.setDividerHeight(0);
        cityView.setAdapter(doubleListAdapter);
        doubleListAdapter.setAreaSelected(new DoubleListDropDownAdapter.IAreaSelected() {
            @Override
            public void areaIsSelected(int type, List<String> selectdeArea) {
                if (type == Constant.ENSURE_CITY){
                    if (selectdeArea != null && selectdeArea.size() > 0){
                        mDropDownMenu.setTabText(selectdeArea.get(0) + "(" + selectdeArea.size() + ")");
                    } else {
                        mDropDownMenu.setTabText(shi.get(0));
                    }
                } else if (type == Constant.RESET_CITY){
                    mDropDownMenu.setTabText(shi.get(0));
                } else if (type == Constant.SWITCH_CITY){
                    CitySwitchActivity.launch(getActivity());
                }
                mDropDownMenu.closeMenu();
            }

            @Override
            public void switchSubway(int type) {
                if (type == Constant.SWITCH_CIRCLE){

                } else if (type == Constant.SWITCH_SUBWAY){

                }
            }
        });

        final NoScrollListView requireView = new NoScrollListView(getContext());
        requireAdapter = new RequireDropDownAdapter(getContext(), requireData);
        requireView.setDividerHeight(0);
        requireView.setAdapter(requireAdapter);

        popupViews.clear();
        popupViews.add(recommendView);
        popupViews.add(cityView);
        popupViews.add(requireView);

        recommendView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : recommend[position]);
                mDropDownMenu.closeMenu();
            }
        });
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : city.get(position).get(0).get(0));
                mDropDownMenu.closeMenu();
            }
        });

        TextView contentView = new TextView(getContext());
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        contentView.setText("内容显示区域");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        if (rvFiltrateContentView != null) {
            ViewGroup parentViewGroup = (ViewGroup) rvFiltrateContentView.getParent();
            if (parentViewGroup != null ) {
                parentViewGroup.removeView(rvFiltrateContentView);
            }
        }
        List<String> data = new ArrayList<>();
        for (int i=0; i<20; i++){
            data.add("Android开发工程师");
        }
        //职位对应的布局
        mFiltrateAdapter = new PositionFiltrateRVAdapter(R.layout.item_position_rv, data);
        rvFiltrateContentView.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFiltrateContentView.setAdapter(mFiltrateAdapter);

        //企业对应的布局
        mCompanyAdapter = new PositionCompanyRVAdapter(R.layout.item_position_company, data);
        rvCompany.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCompany.setAdapter(mCompanyAdapter);

        //给DropDownMenu设置数据
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers),  popupViews, rvFiltrateContentView);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            if (mDropDownMenu.isShowing()) {
                mDropDownMenu.closeMenu();
            }
        }
    }
}
