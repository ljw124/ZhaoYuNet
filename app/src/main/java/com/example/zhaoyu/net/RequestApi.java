package com.example.zhaoyu.net;

import com.example.zhaoyu.bean.VideoChannelBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ljw on 2019/5/24.
 */
public interface RequestApi {

    @GET("ifengvideoList")
    Observable<List<VideoChannelBean>> getVideoChannel(@Query("page") int page);
}
