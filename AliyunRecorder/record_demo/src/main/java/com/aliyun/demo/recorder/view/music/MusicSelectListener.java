package com.aliyun.demo.recorder.view.music;

import com.aliyun.svideo.base.http.MusicFileBean;

public interface MusicSelectListener {
    void onMusicSelect(MusicFileBean musicFileBean, long startTime);
}
