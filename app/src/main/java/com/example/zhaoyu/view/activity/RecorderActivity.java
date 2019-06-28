package com.example.zhaoyu.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.aliyun.demo.recorder.activity.AlivcSvideoRecordActivity;
import com.aliyun.svideo.sdk.external.struct.snap.AliyunSnapVideoParam;
import com.example.zhaoyu.R;
import com.example.zhaoyu.base.BaseActivity;
import com.example.zhaoyu.databinding.ActivityRecorderBinding;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static com.aliyun.svideo.base.AlivcSvideoEditParam.INTENT_PARAM_KEY_ENTRANCE;

public class RecorderActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    ActivityRecorderBinding binding;
    private String[] permissionString = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    public static void launch(Activity context){
        Intent intent = new Intent(context, RecorderActivity.class);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recorder);

        initView();
    }

    @Override
    public boolean isActionBarColorChange() {
        return true;
    }

    private void initView() {

        if (!EasyPermissions.hasPermissions(this, permissionString)) {
            EasyPermissions.requestPermissions(this, "正在申请读写权限", 0, permissionString);
        }

//        Intent record = new Intent();
//        record.setClassName(RecorderActivity.this, "com.aliyun.demo.recorder.activity.AlivcParamSettingActivity");
//        //判断是编辑模块进入还是通过社区模块的编辑功能进入
//        //svideo: 短视频
//        //community: 社区
//        record.putExtra(INTENT_PARAM_KEY_ENTRANCE, "svideo");
//        startActivity(record);

        AliyunSnapVideoParam recordParam = new AliyunSnapVideoParam.Builder().build();
        AlivcSvideoRecordActivity.startRecord(this, recordParam,"community");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}
