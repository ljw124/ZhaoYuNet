package com.example.zhaoyu.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import com.example.zhaoyu.base.BaseActivity;
import com.example.zhaoyu.utils.NetUtil;

/**
 * Created by LJW on 2018/3/15.
 * 自定义检查手机网络状态是否切换的广播接受器
 */
public class NetBroadcastReceiver extends BroadcastReceiver {

    public NetEvevt evevt = (NetEvevt) BaseActivity.evevt;

    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果相等的话就说明网络状态发生了变化
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetUtil.getNetWorkState(context);
            // 接口回调传过去状态的类型
            if (evevt != null) {
                evevt.onNetChange(netWorkState);
            }
        }
    }

    // 自定义接口
    public interface NetEvevt {
        void onNetChange(int netMobile);
    }
}
