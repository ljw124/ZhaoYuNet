package com.example.zhaoyu.base;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.zhaoyu.R;
import com.example.zhaoyu.common.NetBroadcastReceiver;
import com.example.zhaoyu.utils.ActivityContainerUtil;
import com.example.zhaoyu.utils.NetUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.lang.ref.SoftReference;

/**
 * Created by Ljw on 2019/5/21.
 */
public abstract class BaseActivity extends AppCompatActivity implements NetBroadcastReceiver.NetEvevt {

    ActivityDelegate delegate = new ActivityDelegate(this);
    protected static Handler mHandler;
    public static SoftReference<NetBroadcastReceiver.NetEvevt> evevt;
    //网络类型
    private int netMobile;
    private View statusBarView;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        ActivityContainerUtil.getInstance().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        evevt = new SoftReference<>(this);
        inspectNet();
        mHandler = new Handler();
        //状态栏颜色渐变
        if (isActionBarColorChange()){
            setActionBarColor();
        }
//        //状态栏透明、隐藏导航栏
        setActionbarTranslation();
    }

    public abstract boolean isActionBarColorChange();

    private void setActionbarTranslation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
    }

    public void showHideFragment(Class<? extends Fragment> fragmentClass, Bundle args) {
        Fragment fragment = delegate.findFragmentByTag(fragmentClass.getName());
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
                delegate.addFragment(fragment);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else
            delegate.showHideFragment(fragment);

        if (args != null) {
            fragment.setArguments(args);
        }
    }

    public void loadRootFragment(Fragment fragment) {
        if (R.id.fl_root <= 0) {
            return;
        }
        if (delegate.findFragmentByTag(fragment.getClass().getName()) == null) {
            delegate.loadRootFragment(R.id.fl_root, fragment);
        } else {
            fragment = delegate.findFragmentByTag(fragment.getClass().getName());
            delegate.showHideFragment(fragment);
        }
    }

    public void startBrotherActivity(Class<? extends BaseActivity> activityClass) {
        delegate.startOtherActivity(activityClass, false);
    }

    /***************************监听网络****************************/
    /**
     * 初始化时判断有没有网络
     */
    public boolean inspectNet() {
        this.netMobile = NetUtil.getNetWorkState(BaseActivity.this);
        return isNetConnect();
    }

    /**
     * 网络变化之后的类型
     */
    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();
    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == 1) {
            return true;
        } else if (netMobile == 0) {
            return true;
        } else if (netMobile == -1) {
            return false;
        }
        return false;
    }

    /***************************双击退出APP****************************/
    boolean isExit = false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit){
                EMClient.getInstance().logout(true, new EMCallBack() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String error) {

                    }
                });
                ActivityContainerUtil.getInstance().finishAllActivity();
                return true;
            } else {
                isExit = true;
                Toast.makeText(BaseActivity.this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 1000);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    /***************************** 状态栏渐变 ********************************/
    private void setActionBarColor() {
        //延时加载
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                if (isStatusBar()) {
                    initStatusBar();
                    getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            initStatusBar();
                        }
                    });
                }
                //只走一次
                return false;
            }
        });
    }
    private void initStatusBar() {
        if (statusBarView == null) {
            int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
            statusBarView = getWindow().findViewById(identifier);
        }
        if (statusBarView != null) {
            statusBarView.setBackgroundResource(R.drawable.action_bar_color);
        }
    }
    protected boolean isStatusBar() {
        return true;
    }
}
