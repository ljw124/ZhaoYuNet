package com.example.zhaoyu.viewmodel;

import android.app.Activity;

import com.example.zhaoyu.view.activity.CommunicatedActivity;
import com.example.zhaoyu.view.activity.DeliveredActivity;
import com.example.zhaoyu.view.activity.InterestedActivity;
import com.example.zhaoyu.view.activity.InterviewActivity;
import com.example.zhaoyu.view.activity.SettingActivity;

/**
 * Created by Ljw on 2019/5/22.
 */
public class MyVM extends BaseVM {

    Activity content;
    public void setActivity(Activity content){
        this.content = content;
    }

    public void goCommunicatedPage(){
        CommunicatedActivity.launch(content);
    }

    public void goInterviewPage(){
        InterviewActivity.launch(content);
    }

    public void goDeliveredPage(){
        DeliveredActivity.launch(content);
    }

    public void goInterestedPage(){
        InterestedActivity.launch(content);
    }

    public void goSettingPage(){
        SettingActivity.launch(content);
    }
}
