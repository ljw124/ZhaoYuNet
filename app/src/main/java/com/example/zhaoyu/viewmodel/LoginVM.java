package com.example.zhaoyu.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.example.zhaoyu.MyApplication;
import com.example.zhaoyu.common.StartFragmentEvent;
import com.example.zhaoyu.common.VMEventListener;
import com.example.zhaoyu.utils.StringUtil;
import com.example.zhaoyu.view.fragment.login.LoginFragment;
import com.example.zhaoyu.view.fragment.login.ResetPwdFragment;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Ljw on 2019/5/22.
 */
public class LoginVM extends BaseVM {

//    public ObservableBoolean isRegister = new ObservableBoolean();
    public ObservableBoolean isCodeLogin = new ObservableBoolean();
    public ObservableField<String> userName = new ObservableField<>();
    public ObservableField<String> userPwd = new ObservableField<>();
    public ObservableField<String> securityCode = new ObservableField<>();
    public ObservableField<String> tips = new ObservableField<>();
    public ObservableBoolean isShowTips = new ObservableBoolean();

    // 跳转到忘记密码页面
    public void toResetPwdPage(){
        userName.set("");
        userPwd.set("");
        EventBus.getDefault().post(new StartFragmentEvent(ResetPwdFragment.class));
    }

    // 返回到登录界面界面
    public void toLoginPage(){
        userName.set("");
        userPwd.set("");
        securityCode.set("");
        EventBus.getDefault().post(new StartFragmentEvent(LoginFragment.class));
    }

    // 登录/注册
    public void login(){
        loginHX("zhaoyutest", "123456");
        vmEventListener.onSuccess(VMEventListener.VM_EVENT_LOGIN_SUCCESS);

        String name = userName.get();
        String pwd = userPwd.get();
        if (StringUtil.isEmpty(name)){
            tips.set("用户名不能为空！");
            isShowTips.set(true);
            return;
        }
        if (StringUtil.isEmpty(pwd)){
            tips.set("密码不能为空！");
            isShowTips.set(true);
            return;
        }
    }

    // 找回密码
    public void resetPwd(){
        String name = userName.get();
        String pwd = userPwd.get();
        String securityCode = this.securityCode.get();
        if (StringUtil.isEmpty(name)){
            tips.set("用户名不能为空！");
            isShowTips.set(true);
            return;
        }
        if (StringUtil.isEmpty(pwd)){
            tips.set("密码不能为空！");
            isShowTips.set(true);
            return;
        }
        if (StringUtil.isEmpty(securityCode)){
            tips.set("验证码不能为空！");
            isShowTips.set(true);
            return;
        }
    }

    // 登录环信
    public void loginHX(String name, String pwd){
        if(EMClient.getInstance().isLoggedInBefore()){
            vmEventListener.onSuccess(VMEventListener.VM_EVENT_HX_LOGIN_SUCCESS);
            return;
        }
        EMClient.getInstance().login(name, pwd, new EMCallBack() {

            @Override
            public void onSuccess() {
                vmEventListener.onSuccess(VMEventListener.VM_EVENT_HX_LOGIN_SUCCESS);
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String error) {
                vmEventListener.onFailure(error);
            }
        });
    }
}
