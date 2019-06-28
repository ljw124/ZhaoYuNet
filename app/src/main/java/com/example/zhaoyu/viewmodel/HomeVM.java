package com.example.zhaoyu.viewmodel;

import android.content.Context;
import android.widget.Toast;

import com.example.zhaoyu.MyApplication;

/**
 * Created by Ljw on 2019/5/22.
 */
public class HomeVM extends BaseVM {

    private Context context = MyApplication.getContext();

    //获取轮播图数据
    public void getBannerData(){

    }

    //兼职中心
    public void partTime(){
        Toast.makeText(context, "兼职中心", Toast.LENGTH_SHORT).show();
    }

    //事实接单
    public void receiving(){
        Toast.makeText(context, "事实接单", Toast.LENGTH_SHORT).show();
    }
}
