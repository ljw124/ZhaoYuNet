package com.example.zhaoyu.common;

/**
 * Created by Ljw on 2019/5/24.
 */
public class Constant {

    //是否内网开发环境
    public static final boolean INTRANET_MODE = false;
    //baseUrl
    public final static String URL = Constant.INTRANET_MODE ? "http://192.168.102.59:5005/" : "http://api.iclient.ifeng.com/";

    public final static int ENSURE_CITY = 1, RESET_CITY = 2, SWITCH_CITY = 3, SWITCH_CIRCLE = 4, SWITCH_SUBWAY = 5;
    public final static int CITY_SWITCH_FRAGMENT = 1, HOT_CITY_FRAGMENT = 2, POSITION_FRAGMENT = 3;
}
