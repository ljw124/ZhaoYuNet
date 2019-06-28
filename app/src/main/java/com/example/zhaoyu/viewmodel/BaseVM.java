package com.example.zhaoyu.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.example.zhaoyu.common.VMEventListener;

/**
 * Created by Ljw on 2019/5/22.
 */
public abstract class BaseVM extends ViewModel {

    /********************** 添加VM事件监听者 ***************************/
    VMEventListener vmEventListener = new VMEventListener() {
        @Override
        public void onSuccess(int code) {

        }

        @Override
        public void onFailure(String errorMsg) {

        }
    };
    public void setVmEventListener(VMEventListener vmEventListener) {
        this.vmEventListener = vmEventListener;
    }
}
