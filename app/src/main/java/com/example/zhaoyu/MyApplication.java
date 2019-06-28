package com.example.zhaoyu;

import android.app.Application;
import android.content.Context;

import com.aliyun.common.httpfinal.QupaiHttpFinal;
import com.aliyun.downloader.DownloaderManager;
import com.example.zhaoyu.common.LocationService;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.litepal.LitePal;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Ljw on 2019/5/21.
 */
public class MyApplication extends Application {

    private static Context context;
    //内存检测
    private RefWatcher refWatcher;
    public LocationService locationService;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //初始化LeakCanary
//        refWatcher= setupLeakCanary();

        //初始化数据库LitePal
        LitePal.initialize(this);

        //初始化环信
        EMOptions options = new EMOptions();
        options.setAutoLogin(false);
        EaseUI.getInstance().init(this, options);
//        EMClient.getInstance().setDebugMode(true);

        //初始化定位服务
        locationService = new LocationService(getApplicationContext());

        //初始化极光推送
        JPushInterface.setDebugMode(true); //设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        //设置别名
        JPushInterface.setAlias(this, 11111, "test");

        //初始化阿里云短视频
//        AliVcMediaPlayer.init(getApplicationContext());
        QupaiHttpFinal.getInstance().initOkHttpFinal();
        DownloaderManager.getInstance().init(this);
    }

    public static Context getContext() {
        return context;
    }

    /************************ 内存泄漏检测 ************************/
    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }
    //向外暴露的方法
    public static RefWatcher getRefWatcher(Context context) {
        MyApplication leakApplication = (MyApplication) context.getApplicationContext();
        return leakApplication.refWatcher;
    }
}
