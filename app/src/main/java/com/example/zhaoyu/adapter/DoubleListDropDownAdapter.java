package com.example.zhaoyu.adapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.zhaoyu.R;
import com.example.zhaoyu.bean.SubwayBean;
import com.example.zhaoyu.common.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Ljw on 2019/5/30.
 */
public class DoubleListDropDownAdapter extends BaseAdapter {

    private Context context;
    private Map<List<String>, List<List<String>>> city;
    private Map<List<String>, List<List<SubwayBean.LinesListBean.StepsListBean>>> citySubway;
    private int cityID = 0; //选中的市
    private int subwayID = 0; //选中的地铁
    private List<String> selectedArea = new ArrayList<>(); //选中的区
    private AreaDropDownAdapter rightAdapter;
    private AreaDropDownAdapter rightAdapter2;
    private AreaDropDownAdapter rightSubwayAdapter;
    private AreaDropDownAdapter rightSubwayAdapter2;
    private boolean isFirst = true;

    public DoubleListDropDownAdapter(Context context, Map<List<String>, List<List<String>>> city) {
        this.context = context;
        this.city = city;
        selectedArea.clear();
    }

    public void setSubwayData(Map<List<String>, List<List<SubwayBean.LinesListBean.StepsListBean>>> citySubway){
        this.citySubway = citySubway;
    }

    private IAreaSelected areaSelected;
    public void setAreaSelected(IAreaSelected areaSelected){
        this.areaSelected = areaSelected;
    }

    @Override
    public int getCount() {
        return city.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_double_list_drop_down, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, final ViewHolder viewHolder) {
        //市级城市
        final List<String> shi = new ArrayList<>();
        Iterator<List<String>> iterator = city.keySet().iterator();
        if (iterator.hasNext()){
            shi.addAll(iterator.next());
        }
        ArrayAdapter<String> leftAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, shi);
        viewHolder.lvLeft.setAdapter(leftAdapter);

        //地铁
        final List<String> cityForSubway = new ArrayList<>();
        Iterator<List<String>> subwayIterator = citySubway.keySet().iterator();
        if (subwayIterator.hasNext()){
            cityForSubway.addAll(subwayIterator.next());
        }
        ArrayAdapter<String> leftSubwayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, cityForSubway);
        viewHolder.lvLeftSubway.setAdapter(leftSubwayAdapter);

        //刚进来时默认显示全市
        if (isFirst){
            isFirst = false;
            rightAdapter2 = new AreaDropDownAdapter(context, city.get(shi).get(0));
            viewHolder.lvRight.setAdapter(rightAdapter2);
            rightAdapter2.setCheckBoxSelected(new AreaDropDownAdapter.ICheckBoxSelected() {
                @Override
                public void checkBoxSelected(boolean isChecked, int position) {
                    String area = city.get(shi).get(cityID).get(position); //获取点击的区
                    if (isChecked){
                        selectedArea.add(area);
                    } else {
                        selectedArea.remove(area);
                    }
                }
            });

            //默认显示一号线
            List<SubwayBean.LinesListBean.StepsListBean> stepsListBeans = citySubway.get(cityForSubway).get(0);
            List<String> steps = new ArrayList<>();
            for (int i=0; i<stepsListBeans.size(); i++){
                steps.add(stepsListBeans.get(i).getName());
            }
            rightSubwayAdapter2 = new AreaDropDownAdapter(context, steps);
            viewHolder.lvRightSubway.setAdapter(rightSubwayAdapter2);
            rightSubwayAdapter2.setCheckBoxSelected(new AreaDropDownAdapter.ICheckBoxSelected() {
                @Override
                public void checkBoxSelected(boolean isChecked, int position) {
                    SubwayBean.LinesListBean.StepsListBean stepsListBean = citySubway.get(cityForSubway).get(subwayID).get(position);
                    if (isChecked){
                        selectedArea.add(stepsListBean.getName());
                    } else {
                        selectedArea.remove(stepsListBean.getName());
                    }
                }
            });
        }

        //点击市级城市后，切换对应的区级城市
        viewHolder.lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                selectedArea.clear();
                cityID = position;

                //二级列表赋值
                List<List<String>> lists = city.get(shi);
                List<String> qu = new ArrayList<>();
                qu = lists.get(position);
                rightAdapter = new AreaDropDownAdapter(context, qu);
                viewHolder.lvRight.setAdapter(rightAdapter);
                notifyDataSetChanged();

                //二级列表，点击对应的区的回调
                rightAdapter.setCheckBoxSelected(new AreaDropDownAdapter.ICheckBoxSelected() {
                    @Override
                    public void checkBoxSelected(boolean isChecked, int position) {
                        String area = city.get(shi).get(cityID).get(position); //获取点击的区
                        if (isChecked){
                            selectedArea.add(area);
                        } else {
                            selectedArea.remove(area);
                        }
                    }
                });
            }
        });
        //点击几号线，显示对应的地铁站点
        viewHolder.lvLeftSubway.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subwayID = position;
                selectedArea.clear();

                List<List<SubwayBean.LinesListBean.StepsListBean>> stepsList = citySubway.get(cityForSubway);
                List<SubwayBean.LinesListBean.StepsListBean> steps = new ArrayList<>();
                steps = stepsList.get(position);
                List<String> stepsName = new ArrayList<>();
                for (int i=0; i<steps.size(); i++){
                    stepsName.add(steps.get(i).getName());
                }
                rightSubwayAdapter = new AreaDropDownAdapter(context, stepsName);
                viewHolder.lvRightSubway.setAdapter(rightSubwayAdapter);
                notifyDataSetChanged();
                rightSubwayAdapter.setCheckBoxSelected(new AreaDropDownAdapter.ICheckBoxSelected() {
                    @Override
                    public void checkBoxSelected(boolean isChecked, int position) {
                        SubwayBean.LinesListBean.StepsListBean stepsListBean = citySubway.get(cityForSubway).get(subwayID).get(position);
                        if (isChecked){
                            selectedArea.add(stepsListBean.getName());
                        } else {
                            selectedArea.remove(stepsListBean.getName());
                        }
                    }
                });
            }
        });

        viewHolder.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0: //商圈
                        selectedArea.clear();
                        viewHolder.llBusinessCircle.setVisibility(View.VISIBLE);
                        viewHolder.llSubway.setVisibility(View.GONE);
                        viewHolder.btnSwitchCity.setVisibility(View.VISIBLE);
                        areaSelected.switchSubway(Constant.SWITCH_CIRCLE);

                        leftAdapter.notifyDataSetChanged();
                        break;
                    case 1: //地铁
                        selectedArea.clear();
                        viewHolder.llBusinessCircle.setVisibility(View.GONE);
                        viewHolder.llSubway.setVisibility(View.VISIBLE);
                        viewHolder.btnSwitchCity.setVisibility(View.GONE);
                        areaSelected.switchSubway(Constant.SWITCH_SUBWAY);

                        viewHolder.lvLeftSubway.setItemChecked(0, true);
                        leftSubwayAdapter.notifyDataSetChanged();
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

        viewHolder.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedArea.clear();
                areaSelected.areaIsSelected(Constant.RESET_CITY,null);
                if (rightAdapter != null){
                    rightAdapter.initCheckData();
                    rightAdapter.notifyDataSetChanged();
                }
                if (rightSubwayAdapter != null){
                    rightSubwayAdapter.initCheckData();
                    rightSubwayAdapter.notifyDataSetChanged();
                }
            }
        });
        viewHolder.btnEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areaSelected.areaIsSelected(Constant.ENSURE_CITY , selectedArea);
            }
        });
        viewHolder.btnSwitchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areaSelected.areaIsSelected(Constant.SWITCH_CITY ,null);
            }
        });
    }

    static class ViewHolder {
        ListView lvRight;
        ListView lvLeft;
        Button btnReset;
        Button btnEnsure;
        Button btnSwitchCity;
        TabLayout tab;
        LinearLayout llBusinessCircle;
        LinearLayout llSubway;
        ListView lvRightSubway;
        ListView lvLeftSubway;

        ViewHolder(View view) {
            lvRight = view.findViewById(R.id.lv_right);
            lvLeft = view.findViewById(R.id.lv_left);
            btnReset = view.findViewById(R.id.bt_reset);
            btnEnsure = view.findViewById(R.id.bt_confirm);
            btnSwitchCity = view.findViewById(R.id.btn_switch_city);
            tab = view.findViewById(R.id.tab);
            llBusinessCircle = view.findViewById(R.id.ll_business_circle);
            llSubway = view.findViewById(R.id.ll_subway);
            lvRightSubway = view.findViewById(R.id.lv_right_subway);
            lvLeftSubway = view.findViewById(R.id.lv_left_subway);
        }
    }

    public interface IAreaSelected{
        void areaIsSelected(int type, List<String> selectdeArea);
        void switchSubway(int type);
    }

}
