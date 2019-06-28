package com.example.zhaoyu.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 软键盘工具
 */
public class KeyBoardUtil {

    private KeyBoardUtil() {

    }

    /**
     * 隐藏软键盘
     */
    public static void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 判断软键盘是否弹出
     */
    public static boolean isKeyboardShow(Activity activity) {
        return activity.getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED;
    }
}
