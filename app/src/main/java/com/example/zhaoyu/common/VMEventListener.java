package com.example.zhaoyu.common;

/**
 * Created by Ljw on 2019/5/27.
 */
public interface VMEventListener {

    void onSuccess(int code);
    void onFailure(String errorMsg);

    int VM_EVENT_LOGIN_SUCCESS = 1001; //登录成功
    int VM_EVENT_HX_LOGIN_SUCCESS = 1002; //登录环信成功
}
