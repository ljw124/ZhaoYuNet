package com.aliyun.demo.recorder.view.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.aliyun.demo.R;
import com.aliyun.demo.recorder.view.effects.paster.AlivcPasterChooseView;
import com.aliyun.demo.recorder.view.effects.paster.PasterSelectListener;
import com.aliyun.svideo.sdk.external.struct.form.PreviewPasterForm;

import java.util.ArrayList;
import java.util.List;

public class GIfEffectChooser extends BasePageChooser {
    private PasterSelectListener pasterSelectListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //适配有底部导航栏的手机，在full的style下会盖住部分视图的bug
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.QUDemoFullFitStyle);
    }

    @Override
    public List<Fragment> createPagerFragmentList() {
        List<Fragment> fragments = new ArrayList<>();

        AlivcPasterChooseView pasterChooseView = new AlivcPasterChooseView();
        pasterChooseView.setPasterSelectListener(new PasterSelectListener() {
            @Override
            public void onPasterSelected(PreviewPasterForm pasterForm) {
                if (pasterSelectListener != null) {
                    pasterSelectListener.onPasterSelected(pasterForm);
                }
            }

            @Override
            public void onSelectPasterDownloadFinish(String path) {
                if (pasterSelectListener != null) {
                    pasterSelectListener.onSelectPasterDownloadFinish(path);
                }
            }
        });
        fragments.add(pasterChooseView);
        return fragments;
    }

    public void setPasterSelectListener(PasterSelectListener pasterSelectListener) {
        this.pasterSelectListener = pasterSelectListener;
    }
}
