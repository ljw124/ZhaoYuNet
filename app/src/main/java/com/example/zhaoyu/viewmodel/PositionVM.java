package com.example.zhaoyu.viewmodel;

import android.content.res.AssetManager;
import android.databinding.ObservableBoolean;

import com.example.zhaoyu.MyApplication;
import com.example.zhaoyu.bean.SubwayBean;
import com.example.zhaoyu.common.StartFragmentEvent;
import com.example.zhaoyu.view.fragment.login.LoginFragment;
import com.example.zhaoyu.view.fragment.position.AddJobIntentionFragment;
import com.example.zhaoyu.view.fragment.position.ManagerJobIntentionFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Ljw on 2019/5/22.
 */
public class PositionVM extends BaseVM {

    private Map<List<String>, List<List<String>>> city = new HashMap<>(); //城市数据
    private Map<List<String>, List<List<SubwayBean.LinesListBean>>> subway = new HashMap<>(); //地铁数据
    private Map<List<String>, List<List<SubwayBean.LinesListBean.StepsListBean>>> citySubway = new HashMap<>(); //某个城市对应的地铁数据

    public ObservableBoolean isCompany = new ObservableBoolean();

    /**
     * 地铁数据
     */
    public Map<List<String>, List<List<SubwayBean.LinesListBean.StepsListBean>>> getSubwayJson(){
        StringBuilder sb = new StringBuilder();
        AssetManager assets = MyApplication.getContext().getAssets();
        try {
            InputStream is = assets.open("subway.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                sb.append(jsonLine);
            }
            reader.close();
            isr.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Type listType = new TypeToken<List<SubwayBean>>() {}.getType();
        List<SubwayBean> list = new Gson().fromJson(sb.toString(), listType );
        List<String> city = new ArrayList<>();
        List<List<SubwayBean.LinesListBean>> linesList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            city.add(list.get(i).getCnName());
            linesList.add(list.get(i).getLinesList());
        }
        subway.put(city, linesList);

        //获取某个城市的地铁数据
        Iterator<List<String>> iterator = subway.keySet().iterator();
        List<String> cityData= new ArrayList<>();
        if (iterator.hasNext()){
            cityData.addAll(iterator.next());
        }
        List<SubwayBean.LinesListBean> linesListBeans = subway.get(cityData).get(10);
        List<String> lines = new ArrayList<>();
        List<List<SubwayBean.LinesListBean.StepsListBean>> steps = new ArrayList<>();
        for (int i= 0; i< linesListBeans.size(); i++){
            lines.add(linesListBeans.get(i).getName());
            steps.add(linesListBeans.get(i).getStepsList());
        }
        citySubway.put(lines, steps);
        return citySubway;
    }

    /**
     * 城市数据
     */
    public List<String> getCityData(){
        List<String> shi = new ArrayList<>();
        shi.add("深圳市");
        shi.add("南山区");
        shi.add("福田区");

        return shi;
    }
    public Map<List<String>, List<List<String>>> getCityAllData(){
        List<String> shi = getCityData();

        List<String> childList0 = new ArrayList<>();
        childList0.add("全深圳市");

        List<String> childList = new ArrayList<>();
        childList.add("全南山区");
        childList.add("科技园");
        childList.add("西丽");
        childList.add("南头");
        childList.add("蛇口");
        childList.add("深圳湾");
        childList.add("华侨城");
        childList.add("南油");
        childList.add("前海");
        childList.add("后海");
        childList.add("新安");
        childList.add("白石洲");
        childList.add("大冲");
        childList.add("海上世界");
        childList.add("海王大厦");
        childList.add("桃源村");

        List<String> childList1 = new ArrayList<>();
        childList1.add("全福田区");
        childList1.add("车公庙");
        childList1.add("华强北");
        childList1.add("梅林");
        childList1.add("岗厦");
        childList1.add("皇岗");
        childList1.add("石厦");
        childList1.add("购物公园");
        childList1.add("八卦岭");
        childList1.add("景田");
        childList1.add("香蜜湖");
        childList1.add("竹子林");
        childList1.add("新洲");
        childList1.add("上步");
        childList1.add("下沙");
        childList1.add("圆岭");
        childList1.add("上沙");
        childList1.add("华侨城");

        List<List<String>> qu = new ArrayList<>();
        qu.add(childList0);
        qu.add(childList);
        qu.add(childList1);

        city.put(shi, qu);
        return city;
    }

    public void toAddJobIntentionPage(){
        EventBus.getDefault().post(new StartFragmentEvent(AddJobIntentionFragment.class));
    }

    public void toManagerJobIntentionPage(){
        EventBus.getDefault().post(new StartFragmentEvent(ManagerJobIntentionFragment.class));
    }
}
