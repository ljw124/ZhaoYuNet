package com.example.zhaoyu.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.SupportActivity;

/**
 * Created by Ljw on 2019/5/22.
 */
public class BaseFragment extends Fragment {

    public BaseActivity baseActivity;
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected static Handler mHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SupportActivity) {
            baseActivity = (BaseActivity) context;
        }
    }

    public void startBrotherActivity(Class<? extends BaseActivity> activityClass) {
        baseActivity.startBrotherActivity(activityClass);
    }

    public void finishActivity() {
        baseActivity.finish();
    }

    public void startBrotherFragment(Class<? extends Fragment> fragmentClass) {
        baseActivity.showHideFragment(fragmentClass, null);
    }

    public void startFragmentWithParam(Class<? extends Fragment> fragmentClass, Bundle args) {
        baseActivity.showHideFragment(fragmentClass, args);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

}
