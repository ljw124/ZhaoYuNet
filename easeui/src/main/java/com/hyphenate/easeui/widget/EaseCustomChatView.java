package com.hyphenate.easeui.widget;

import android.content.Context;
import android.text.Spannable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by Ljw on 2019/6/5.
 */
public class EaseCustomChatView extends EaseChatRow {

    private TextView tvMsg;
    private Button btnYes;
    private Button btnNo;
    private String type;
    private String from;

    public EaseCustomChatView(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? R.layout.ease_custom_chat_view : R.layout.ease_custom_chat_view,this);
    }

    @Override
    protected void onFindViewById() {
        tvMsg = findViewById(R.id.tv_msg);
        btnYes = findViewById(R.id.btn_yes);
        btnNo = findViewById(R.id.btn_no);
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onSetUpView() {
        final EMTextMessageBody messageBody = (EMTextMessageBody) message.getBody();
        Spannable span = EaseSmileUtils.getSmiledText(context, messageBody.getMessage());
        try {
            type = message.getStringAttribute("type");
            from = message.getStringAttribute("from");
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        tvMsg.setText(span, TextView.BufferType.SPANNABLE);
        btnYes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("phone")){
                    sendCMDMessage("PhoneYES");
                    sendOwnMessage(message.getTo() + "的手机号是：" + "13333333333");
                } else if (type.equals("wechat")){
                    sendCMDMessage("WechatYes");
                    sendOwnMessage(message.getTo() + "的微信号是：" + "1555555555");
                }
            }
        });
        btnNo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("phone")){
                    sendCMDMessage("PhoneNO");
                } else if (type.equals("wechat")){
                    sendCMDMessage("WechatNO");
                }
            }
        });
    }

    //发送透传消息
    public void sendCMDMessage(String type){
        EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
        String action = type;
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
        String toUsername = from;//发送给某个人
        cmdMsg.setTo(toUsername);
        cmdMsg.addBody(cmdBody);
        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
    }
    //发送微信号或手机号
    public void sendOwnMessage(String msg){
        EMMessage message = EMMessage.createTxtSendMessage(msg, from);
        EMClient.getInstance().chatManager().sendMessage(message);
    }
}
