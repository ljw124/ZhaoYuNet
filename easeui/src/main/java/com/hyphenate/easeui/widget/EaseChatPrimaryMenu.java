package com.hyphenate.easeui.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.UsefulInfo;
import com.hyphenate.util.EMLog;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * primary menu
 *
 */
public class EaseChatPrimaryMenu extends EaseChatPrimaryMenuBase implements OnClickListener {
    private EditText editText;
    private View buttonSetModeKeyboard;
    private RelativeLayout edittext_layout;
    private View buttonSetModeVoice;
    private View buttonSend;
    private View buttonPressToSpeak;
    private ImageView faceNormal;
    private ImageView faceChecked;
    private Button buttonMore;
    private boolean ctrlPress = false;
    private ListView lvUseful; //常用语
    private List<String> usefulData = new ArrayList<>();

    public EaseChatPrimaryMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public EaseChatPrimaryMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EaseChatPrimaryMenu(Context context) {
        super(context);
        init(context, null);
    }

    private void init(final Context context, AttributeSet attrs) {
        Context context1 = context;
        LayoutInflater.from(context).inflate(R.layout.ease_widget_chat_primary_menu, this);
        editText = (EditText) findViewById(R.id.et_sendmessage);
        buttonSetModeKeyboard = findViewById(R.id.btn_set_mode_keyboard);
        edittext_layout = (RelativeLayout) findViewById(R.id.edittext_layout);
        buttonSetModeVoice = findViewById(R.id.btn_set_mode_voice);
        buttonSend = findViewById(R.id.btn_send);
        buttonPressToSpeak = findViewById(R.id.btn_press_to_speak);
        faceNormal = (ImageView) findViewById(R.id.iv_face_normal);
        faceChecked = (ImageView) findViewById(R.id.iv_face_checked);
        RelativeLayout faceLayout = (RelativeLayout) findViewById(R.id.rl_face);
        buttonMore = (Button) findViewById(R.id.btn_more);
        edittext_layout.setBackgroundResource(R.drawable.ease_input_bar_bg_normal);

        lvUseful = findViewById(R.id.lv_useful);
        View footerView = LayoutInflater.from(getContext()).inflate(R.layout.ease_item_useful_footer, null);
        lvUseful.addFooterView(footerView);
        initUseful();
        
        buttonSend.setOnClickListener(this);
        buttonSetModeKeyboard.setOnClickListener(this);
        buttonSetModeVoice.setOnClickListener(this);
        buttonMore.setOnClickListener(this);
        faceLayout.setOnClickListener(this);
        editText.setOnClickListener(this);
        editText.requestFocus();
        
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edittext_layout.setBackgroundResource(R.drawable.ease_input_bar_bg_active);
                } else {
                    edittext_layout.setBackgroundResource(R.drawable.ease_input_bar_bg_normal);
                }

            }
        });
        // listen the text change
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    buttonMore.setVisibility(View.GONE);
                    buttonSend.setVisibility(View.VISIBLE);
                } else {
                    buttonMore.setVisibility(View.VISIBLE);
                    buttonSend.setVisibility(View.GONE);
                }

                if (listener != null) {
                    listener.onTyping(s, start, before, count);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                EMLog.d("key", "keyCode:" + keyCode + " action:" + event.getAction());

                // test on Mac virtual machine: ctrl map to KEYCODE_UNKNOWN
                if (keyCode == KeyEvent.KEYCODE_UNKNOWN) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        ctrlPress = true;
                    } else if (event.getAction() == KeyEvent.ACTION_UP) {
                        ctrlPress = false;
                    }
                }
                return false;
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                EMLog.i("EaseChatPrimaryMenu", "actionId: " + actionId);
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER &&
                         event.getAction() == KeyEvent.ACTION_DOWN &&
                         ctrlPress == true)) {
                    String s = editText.getText().toString();
                    editText.setText("");
                    listener.onSendBtnClicked(s);
                    return true;
                }
                else{
                    return false;
                }
            }
        });

        
        buttonPressToSpeak.setOnTouchListener(new OnTouchListener() {
            
            @Override 
            public boolean onTouch(View v, MotionEvent event) {
                if(listener != null){
                    return listener.onPressToSpeakBtnTouch(v, event);
                }
                return false;
            }
        });
    }

    private void initUseful() {
        //从数据库中取出常用语
        List<UsefulInfo> usefulInfos = LitePal.findAll(UsefulInfo.class);
        for (int i=0; i<usefulInfos.size(); i++){
            if (!usefulData.contains(usefulInfos.get(i).getUseful())){
                usefulData.add(usefulInfos.get(i).getUseful());
            }
        }
        lvUseful.setAdapter(new UsefulAdapter(usefulData));
        lvUseful.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == usefulData.size()){
                    showAlertDialog();
                } else {
//                    lvUseful.setVisibility(GONE);
                    editText.setText(usefulData.get(position));
                }
            }
        });
        lvUseful.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("删除此条常用语？");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LitePal.delete(UsefulInfo.class, position);
                        initUseful();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                return false;
            }
        });
    }

    boolean isContains = false;
    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("新增常用语");
        final EditText edit = new EditText(getContext());
        edit.setHint("输入您的常用回复，请不要填写微信，QQ，手机号码等联系方式或广告信息，否则系统将封禁您的账号。");
        builder.setView(edit);
        builder.setPositiveButton("保存", null);
        builder.setCancelable(true);
        final AlertDialog dialog = builder.create();
        dialog.show();
        //修改按钮的样式
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout.LayoutParams ll =(LinearLayout.LayoutParams)positiveButton.getLayoutParams();
        ll.gravity= Gravity.CENTER;
        ll.width = ViewGroup.LayoutParams.MATCH_PARENT;
        positiveButton.setLayoutParams(ll);
        positiveButton.setTextSize(18);
        positiveButton.setTextColor(Color.BLACK);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edit.getText())) {
                    Toast.makeText(getContext(), "文字不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String useful = edit.getText().toString().trim();
                List<UsefulInfo> usefulInfos = LitePal.findAll(UsefulInfo.class);
                for (int i=0; i<usefulInfos.size(); i++){
                    if (usefulInfos.get(i).getUseful().equals(useful)){
                        isContains = true;
                        return;
                    }
                }
                if (!isContains){
                    UsefulInfo usefulInfo = new UsefulInfo();
                    usefulInfo.setUseful(useful);
                    usefulInfo.save();
                }
                initUseful();
                dialog.dismiss();
            }
        });
    }

    /**
     * set recorder view when speak icon is touched
     * @param voiceRecorderView
     */
    public void setPressToSpeakRecorderView(EaseVoiceRecorderView voiceRecorderView){
        EaseVoiceRecorderView voiceRecorderView1 = voiceRecorderView;
    }

    /**
     * append emoji icon to editText
     * @param emojiContent
     */
    public void onEmojiconInputEvent(CharSequence emojiContent){
        editText.append(emojiContent);
    }
    
    /**
     * delete emojicon
     */
    public void onEmojiconDeleteEvent(){
        if (!TextUtils.isEmpty(editText.getText())) {
            KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
            editText.dispatchKeyEvent(event);
        }
    }
    
    /**
     * on clicked event
     * @param view
     */
    private boolean isPress = true; //是否显示常用语
    @Override
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btn_send) {
            if(listener != null){
                String s = editText.getText().toString();
                editText.setText("");
                listener.onSendBtnClicked(s);
            }
        } else if (id == R.id.btn_set_mode_voice) {
            // TODO: 2019/6/6
            if (isPress){
                isPress = !isPress;
                lvUseful.setVisibility(VISIBLE);
                showNormalFaceImage();
                if(listener != null)
                    listener.onToggleVoiceBtnClicked();
            } else {
                isPress = !isPress;
                lvUseful.setVisibility(GONE);
                showNormalFaceImage();
                if(listener != null)
                    listener.onToggleVoiceBtnClicked();
            }
        } else if (id == R.id.btn_set_mode_keyboard) {
            isPress = true;
            lvUseful.setVisibility(GONE);
            setModeKeyboard();
            showNormalFaceImage();
            if(listener != null)
                listener.onToggleVoiceBtnClicked();
        } else if (id == R.id.btn_more) {
            isPress = true;
            lvUseful.setVisibility(GONE);
            buttonSetModeVoice.setVisibility(View.VISIBLE);
            buttonSetModeKeyboard.setVisibility(View.GONE);
            edittext_layout.setVisibility(View.VISIBLE);
            buttonPressToSpeak.setVisibility(View.GONE);
            showNormalFaceImage();
            if(listener != null)
                listener.onToggleExtendClicked();
        } else if (id == R.id.et_sendmessage) {
            isPress = true;
            lvUseful.setVisibility(GONE);
            edittext_layout.setBackgroundResource(R.drawable.ease_input_bar_bg_active);
            faceNormal.setVisibility(View.VISIBLE);
            faceChecked.setVisibility(View.INVISIBLE);
            if(listener != null)
                listener.onEditTextClicked();
        } else if (id == R.id.rl_face) {
            isPress = true;
            lvUseful.setVisibility(GONE);
            toggleFaceImage();
            if(listener != null){
                listener.onToggleEmojiconClicked();
            }
        } else {
        }
    }

    class UsefulAdapter extends BaseAdapter{

        private List<String> usefulData;
        public UsefulAdapter(List<String> usefulData){
            this.usefulData = usefulData;
        }

        @Override
        public int getCount() {
            return usefulData == null ? 0 : usefulData.size();
        }

        @Override
        public String getItem(int position) {
            return usefulData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                convertView =  LayoutInflater.from(getContext()).inflate(R.layout.ease_item_useful, null);
                holder = new ViewHolder();
                holder.tv_userful = convertView.findViewById(R.id.tv_useful);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_userful.setText(usefulData.get(position));
            return convertView;
        }

        private class ViewHolder{
            TextView tv_userful;
        }
    }
    
    /**
     * show voice icon when speak bar is touched
     * 
     */
    protected void setModeVoice() {
        hideKeyboard();
        edittext_layout.setVisibility(View.GONE);
        buttonSetModeVoice.setVisibility(View.GONE);
        buttonSetModeKeyboard.setVisibility(View.VISIBLE);
        buttonSend.setVisibility(View.GONE);
        buttonMore.setVisibility(View.VISIBLE);
        buttonPressToSpeak.setVisibility(View.VISIBLE);
        faceNormal.setVisibility(View.VISIBLE);
        faceChecked.setVisibility(View.INVISIBLE);

    }

    /**
     * show keyboard
     */
    protected void setModeKeyboard() {
        edittext_layout.setVisibility(View.VISIBLE);
        buttonSetModeKeyboard.setVisibility(View.GONE);
        buttonSetModeVoice.setVisibility(View.VISIBLE);
        // mEditTextContent.setVisibility(View.VISIBLE);
        editText.requestFocus();
        // buttonSend.setVisibility(View.VISIBLE);
        buttonPressToSpeak.setVisibility(View.GONE);
        if (TextUtils.isEmpty(editText.getText())) {
            buttonMore.setVisibility(View.VISIBLE);
            buttonSend.setVisibility(View.GONE);
        } else {
            buttonMore.setVisibility(View.GONE);
            buttonSend.setVisibility(View.VISIBLE);
        }

    }
    
    
    protected void toggleFaceImage(){
        if(faceNormal.getVisibility() == View.VISIBLE){
            showSelectedFaceImage();
        }else{
            showNormalFaceImage();
        }
    }
    
    private void showNormalFaceImage(){
        faceNormal.setVisibility(View.VISIBLE);
        faceChecked.setVisibility(View.INVISIBLE);
    }
    
    private void showSelectedFaceImage(){
        faceNormal.setVisibility(View.INVISIBLE);
        faceChecked.setVisibility(View.VISIBLE);
    }
    

    @Override
    public void onExtendMenuContainerHide() {
        showNormalFaceImage();
    }

    @Override
    public void onTextInsert(CharSequence text) {
       int start = editText.getSelectionStart();
       Editable editable = editText.getEditableText();
       editable.insert(start, text);
       setModeKeyboard();
    }

    @Override
    public EditText getEditText() {
        return editText;
    }

}
