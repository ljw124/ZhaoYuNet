package com.example.zhaoyu.view.fragment.position;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhaoyu.R;
import com.example.zhaoyu.adapter.JobIntentionRVAdapter;
import com.example.zhaoyu.base.BaseFragment;
import com.example.zhaoyu.common.ViewModelProvider;
import com.example.zhaoyu.databinding.FragmentManagerJobIntentionBinding;
import com.example.zhaoyu.viewmodel.PositionVM;

import java.util.ArrayList;
import java.util.List;

public class ManagerJobIntentionFragment extends BaseFragment {

    FragmentManagerJobIntentionBinding binding;
    private PositionVM vm;
    private RecyclerView rvJobIntention;
    private JobIntentionRVAdapter mAdapter;

    public static ManagerJobIntentionFragment newInstance() {

        Bundle args = new Bundle();

        ManagerJobIntentionFragment fragment = new ManagerJobIntentionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_manager_job_intention, container, false);
        binding.setVm(ViewModelProvider.getInstance().get(PositionVM.class));
        initView();
        return binding.getRoot();
    }

    private void initView() {
        vm = binding.getVm();
        rvJobIntention = binding.rvJobIntention;

        binding.ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        List<String> salary = new ArrayList<>();
        salary.add("10K-16K");
        salary.add("11K-18K");
        salary.add("12K-19K");
        mAdapter = new JobIntentionRVAdapter(R.layout.item_job_intention, salary);
        rvJobIntention.setLayoutManager(new LinearLayoutManager(getContext()));
        rvJobIntention.setAdapter(mAdapter);
    }

}
