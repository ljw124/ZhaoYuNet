package com.example.zhaoyu.common;

import com.example.zhaoyu.base.BaseFragment;

/**
 * Created by LJW on 2019/5/27.
 */
public class StartFragmentEvent {
    public Class<? extends BaseFragment> fragmentClass;
    public boolean isAddStack = false;

    public StartFragmentEvent(Class<? extends BaseFragment> fragmentClass) {
        this.fragmentClass = fragmentClass;
    }
}
