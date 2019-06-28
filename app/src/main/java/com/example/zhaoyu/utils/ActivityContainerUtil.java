package com.example.zhaoyu.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ljw on 2019/5/31.
 */
public class ActivityContainerUtil {
    public ActivityContainerUtil() {
    }

    private static ActivityContainerUtil instance = new ActivityContainerUtil();
    private static List<Activity> activityStack = new ArrayList<Activity>();
    public static ActivityContainerUtil getInstance() {
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new ArrayList<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity = null;
        }
    }
    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
}
