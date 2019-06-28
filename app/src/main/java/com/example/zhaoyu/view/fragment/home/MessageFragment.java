package com.example.zhaoyu.view.fragment.home;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.zhaoyu.R;
import com.example.zhaoyu.base.BaseFragment;
import com.example.zhaoyu.common.ViewModelProvider;
import com.example.zhaoyu.databinding.FragmentMessageBinding;
import com.example.zhaoyu.view.activity.ChatActivity;
import com.example.zhaoyu.viewmodel.MessageVM;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.EaseConversationList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MessageFragment extends BaseFragment {

    FragmentMessageBinding binding;
    private MessageVM vm;
    private EaseConversationList listContact;
    protected List<EMConversation> conversationList = new ArrayList<>();
    protected boolean hidden;
    protected boolean isConflict;
    private final static int MSG_REFRESH = 2;

    @SuppressLint("HandlerLeak")
    protected Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0: //环信连接断开
                    break;
                case 1: //环信连接成功
                    break;
                case MSG_REFRESH:
                    {
                    conversationList.clear();
                    conversationList.addAll(loadConversationList());
                    listContact.refresh();
                    break;
                    }
                default:
                    break;
            }
        }
    };

    public static MessageFragment newInstance() {

        Bundle args = new Bundle();

        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if(savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false);
        binding.setVm(ViewModelProvider.getInstance().get(MessageVM.class));
        initView();
        return binding.getRoot();
    }

    private void initView() {
        vm = binding.getVm();
        listContact = binding.listContact;

        initContactList();
    }

    private void initContactList() {
        conversationList.addAll(loadConversationList());
        listContact.init(conversationList);
        EMClient.getInstance().addConnectionListener(connectionListener);

        listContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = listContact.getItem(position);
                startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
            }
        });
    }

    // 获取消息列表
    protected List<EMConversation> loadConversationList(){
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        List<Pair<Long, EMConversation>> sortList = new ArrayList<>();
         // lastMsgTime will change if there is new message during sorting
         // so use synchronized to make sure timestamp of last message won't change.
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                }
            }
        }
        try {
            // 对消息进行排序
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        return list;
    }

    // 根据时间对消息进行排序
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {
                if (con1.first.equals(con2.first)) {
                    return 0;
                } else if (con2.first.longValue() > con1.first.longValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }

    public void refresh() {
        if(!handler.hasMessages(MSG_REFRESH)){
            handler.sendEmptyMessage(MSG_REFRESH);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden && !isConflict) {
            refresh();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.hidden = isVisibleToUser;
        if (hidden && !isConflict) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            refresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().removeConnectionListener(connectionListener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(isConflict){
            outState.putBoolean("isConflict", true);
        }
    }

    //环信连接\断开监听
    protected EMConnectionListener connectionListener = new EMConnectionListener() {
        @Override
        public void onDisconnected(int error) {
            if (error == EMError.USER_REMOVED || error == EMError.USER_LOGIN_ANOTHER_DEVICE || error == EMError.SERVER_SERVICE_RESTRICTED
                    || error == EMError.USER_KICKED_BY_CHANGE_PASSWORD || error == EMError.USER_KICKED_BY_OTHER_DEVICE) {
                isConflict = true;
            } else {
                handler.sendEmptyMessage(0);
            }
        }

        @Override
        public void onConnected() {
            handler.sendEmptyMessage(1);
        }
    };
}
