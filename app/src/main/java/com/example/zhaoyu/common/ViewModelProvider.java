package com.example.zhaoyu.common;

import android.arch.lifecycle.ViewModel;

import com.example.zhaoyu.viewmodel.HomeVM;
import com.example.zhaoyu.viewmodel.LoginVM;
import com.example.zhaoyu.viewmodel.MessageVM;
import com.example.zhaoyu.viewmodel.MyVM;
import com.example.zhaoyu.viewmodel.PositionVM;
import com.example.zhaoyu.viewmodel.SearchVM;

/**
 * Created by Ljw on 2019/5/22.
 */
public class ViewModelProvider {

    private static ViewModelProvider instance;

    public static ViewModelProvider getInstance() {
        return instance == null ? instance = new ViewModelProvider() : instance;
    }

    private LoginVM loginVM = new LoginVM();
    private HomeVM homeVM = new HomeVM();
    private PositionVM positionVM = new PositionVM();
    private MessageVM messageVM = new MessageVM();
    private MyVM myVM = new MyVM();
    private SearchVM searchVM = new SearchVM();
    public <T extends ViewModel> T get(Class<T> modelClass) {
        if (modelClass.getName().equals(LoginVM.class.getName()))
            return (T) loginVM;
        if (modelClass.getName().equals(HomeVM.class.getName()))
            return (T) homeVM;
        if (modelClass.getName().equals(PositionVM.class.getName()))
            return (T) positionVM;
        if (modelClass.getName().equals(MessageVM.class.getName()))
            return (T) messageVM;
        if (modelClass.getName().equals(MyVM.class.getName()))
            return (T) myVM;
        if (modelClass.getName().equals(SearchVM.class.getName()))
            return (T) searchVM;
        return null;
    }
}
