package com.example.zhaoyu.view.fragment.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zhaoyu.R;
import com.example.zhaoyu.base.BaseFragment;
import com.example.zhaoyu.common.ViewModelProvider;
import com.example.zhaoyu.databinding.FragmentMyBinding;
import com.example.zhaoyu.view.activity.CommunicatedActivity;
import com.example.zhaoyu.view.activity.InterviewActivity;
import com.example.zhaoyu.view.activity.MyResumeActivity;
import com.example.zhaoyu.viewmodel.MyVM;
import com.example.zhaoyu.widget.CustomSettingView;

public class MyFragment extends BaseFragment implements CustomSettingView.OnRootClickListener {

    FragmentMyBinding binding;
    private MyVM vm;

    public static MyFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false);
        binding.setVm(ViewModelProvider.getInstance().get(MyVM.class));
        initView();
        return binding.getRoot();
    }

    private void initView() {
        vm = binding.getVm();
        vm.setActivity(getActivity());
        initSettingView();
    }

    private void initSettingView() {
        binding.csv1.initMine(R.mipmap.ic_launcher, "我的简历", "", true);
        binding.csv1.setOnRootClickListener(this, 1);
        binding.csv2.initMine(R.mipmap.ic_launcher, "管理求职意向", "", true);
        binding.csv2.setOnRootClickListener(this, 2);
        binding.csv3.initMine(R.mipmap.ic_launcher, "关注公司", "", true);
        binding.csv3.setOnRootClickListener(this, 3);
        binding.csv4.initMine(R.mipmap.ic_launcher, "鱼塘管理", "", true);
        binding.csv4.setOnRootClickListener(this, 4);
        binding.csv5.initMine(R.mipmap.ic_launcher, "私隐设置", "", true);
        binding.csv5.setOnRootClickListener(this, 5);
        binding.csv6.initMine(R.mipmap.ic_launcher, "帮助反馈", "", true);
        binding.csv6.setOnRootClickListener(this, 6);
    }

    @Override
    public void onRootClick(View view) {
        switch ((int)view.getTag()){
            case 1:
                MyResumeActivity.launch(getActivity());
                break;
            case 2:
                Toast.makeText(getActivity(), "2", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(getActivity(), "3", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(getActivity(), "4", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(getActivity(), "5", Toast.LENGTH_SHORT).show();
                break;
            case 6:
                Toast.makeText(getActivity(), "6", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
