package com.example.zhaoyu.base;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.zhaoyu.R;

/**
 * Created by LJW on 2019/5/23.
 */
public class ActivityDelegate {
    static final int TYPE_SHOW = 10;
    static final int TYPE_REPLACE = 0;
    static final int TYPE_ADD = 1;
    Fragment fromFragment = null;

    AppCompatActivity mActivity;
    int mContainerId;

    public ActivityDelegate(AppCompatActivity compatActivity) {
        if (!(compatActivity instanceof Activity))
            throw new RuntimeException("Must extends FragmentActivity/AppCompatActivity");
        this.mActivity = compatActivity;
    }

    /**
     * 加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
     */
    public void loadRootFragment(int containerId, Fragment toFragment) {
        mContainerId = containerId;
        start(null, toFragment, toFragment.getClass().getName(), true, TYPE_REPLACE);
    }

    public void showHideFragment(Fragment showFragment) {
        if (showFragment == fromFragment)
            return;
        start(fromFragment, showFragment, showFragment.getClass().getName(), true, TYPE_SHOW);
    }

    public void addFragment(Fragment fragment) {
        start(fromFragment, fragment, fragment.getClass().getName(), true, TYPE_ADD);
    }

    /**
     * 启动Activity
     *
     * @param activity
     * @param finishOld
     */
    public void startOtherActivity(Class<? extends AppCompatActivity> activity, boolean finishOld) {
        Intent intent = new Intent(mActivity.getBaseContext(), activity);
        mActivity.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out);
        if (finishOld) {
            mActivity.finish();
        }
    }

    public Fragment findFragmentByTag(String fragmentTag) {
        return getFragmentManager().findFragmentByTag(fragmentTag);
    }

    private void start(final Fragment from, Fragment to, String toFragmentTag,
                       boolean dontAddToBackStack, int type) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (from == null) {
            ft.replace(getContainerId(), to, toFragmentTag);
        } else {
            if (type == TYPE_ADD) {
                ft.add(getContainerId(), to, toFragmentTag);
                ft.hide(from);
            } else if (type == TYPE_SHOW) {
                ft.show(to).hide(from);
            } else {
                ft.replace(getContainerId(), to, toFragmentTag);
            }
        }
        ft.commitAllowingStateLoss();
        fromFragment = to;
    }

    private FragmentManager getFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    public void setContainerId(@StringRes int containerId) {
        this.mContainerId = containerId;
    }

    public int getContainerId() {
        if (mContainerId == 0) {
            mContainerId = R.id.fl_root;
        }
        return mContainerId;
    }

}