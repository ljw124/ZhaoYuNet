package com.example.zhaoyu.net;

import com.example.zhaoyu.MyApplication;
import com.example.zhaoyu.common.Constant;
import com.example.zhaoyu.utils.DeviceUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {

    private static HttpManager instance;

    private static Retrofit retrofit;
    private static Retrofit retrofitLong;
    private static Retrofit retrofitProgress;

    private HttpManager() {
        // 日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        // TODO: 2019/5/24
                    }
                })
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        // 请求头拦截器
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("devId", DeviceUtil.getDeviceID(MyApplication.getContext()))
                        .addHeader("time", System.currentTimeMillis() + "")
                        .build();
                return chain.proceed(request);
            }
        };

        // 进度监听拦截器
        Interceptor progressInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body()))
                        .build();
            }
        };

        /**
         * 接口请求使用
         */
        OkHttpClient mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS) // 设置超时时间
                .readTimeout(10, TimeUnit.SECONDS) // 设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS) // 设置写入超时时间
                .cache(new Cache(MyApplication.getContext().getCacheDir(), 10 * 1024 * 1024)) // 10MB缓存
//                .addInterceptor(headerInterceptor) // 添加header信息
                .addInterceptor(loggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        /**
         * 下载文件使用
         */

        OkHttpClient progressOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS) // 设置超时时间
                .addInterceptor(progressInterceptor)
                .build();

        retrofitProgress = new Retrofit.Builder()
                .baseUrl(Constant.URL)
                .client(progressOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static HttpManager getInstance() {
        if (null == instance) {
            synchronized (HttpManager.class) {
                if (null == instance) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    public static RequestApi getHttpImpl() {
        getInstance();
        return retrofit.create(RequestApi.class);
    }

    public static RequestApi getDownLoadImpl() {
        getInstance();
        return retrofitProgress.create(RequestApi.class);
    }

}
