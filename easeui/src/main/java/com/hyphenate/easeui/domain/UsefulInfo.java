package com.hyphenate.easeui.domain;

import org.litepal.crud.LitePalSupport;

/**
 * Created by Ljw on 2019/6/10.
 */
public class UsefulInfo extends LitePalSupport {

    private String useful;

    public String getUseful() {
        return useful;
    }

    public void setUseful(String useful) {
        this.useful = useful;
    }
}
