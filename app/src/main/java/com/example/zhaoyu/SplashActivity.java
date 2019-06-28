package com.example.zhaoyu;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.aliyun.common.utils.StorageUtils;
import com.example.zhaoyu.databinding.ActivitySplashBinding;
import com.example.zhaoyu.utils.ImageLoaderUtil;
import com.example.zhaoyu.view.activity.LoginActivity;

import java.io.File;
import java.io.IOException;

/**
 * Created by Ljw on 2019/5/21.
 */
public class SplashActivity extends AppCompatActivity{

    ActivitySplashBinding binding;
    //渐变动画(作用：1.背景图从透明到不透明的过渡。2.动画完成后跳转到主页面)
    AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        initView();

        String dir = StorageUtils.getCacheDirectory(SplashActivity.this).getAbsolutePath() + File.separator + "AliyunDemo" + File.separator;
        String dir2 = StorageUtils.getCacheDirectory(SplashActivity.this).getAbsolutePath() + File.separator + "AliyunEditorDemo" + File.separator;
        chmod("777", dir);
        chmod("777", dir2);
    }

    public static void chmod(String permission, String path) {
        try {
            String command = "chmod " + permission + " " + path;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        ImageView ivAd = binding.ivAd;
        //加载网络图片到相应的视图
        //每日壁纸 来源于 https://www.dujin.org/fenxiang/jiaocheng/3618.html.
        ImageLoaderUtil.LoadImage(this, "http://api.dujin.org/bing/1920.php", ivAd);

        //设置动画时长和监听
        alphaAnimation.setDuration(2000);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) { //动画完成后跳转到主页面
                toMain();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        ivAd.startAnimation(alphaAnimation);
    }

    private void toMain() {
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
