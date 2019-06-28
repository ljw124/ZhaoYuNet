package com.example.zhaoyu.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by Ljw on 2019/5/31.
 */
public class SearchHistoryInfo extends LitePalSupport {

    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
