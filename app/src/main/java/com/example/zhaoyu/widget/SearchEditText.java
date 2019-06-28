package com.example.zhaoyu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhaoyu.R;
import com.example.zhaoyu.utils.KeyBoardUtil;
import com.example.zhaoyu.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 搜索框
 */
public class SearchEditText extends LinearLayout implements View.OnKeyListener, TextWatcher {

    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.iv_clean)
    ImageView ivClean;
    @BindView(R.id.tv_search)
    TextView tvSearch;

    private String strHint;

    public SearchEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.SearchEditText);
        strHint = typedArray.getString(
                R.styleable.SearchEditText_search_title);
        typedArray.recycle();
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_search, this);
        setOrientation(HORIZONTAL);
        setBackgroundColor(Color.WHITE);
        ButterKnife.bind(this);

        edtSearch.setOnKeyListener(this);
        edtSearch.addTextChangedListener(this);
        if (!StringUtil.isEmpty(strHint)) {
            edtSearch.setHint(strHint);
        }
        ivClean.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchEditText.this.setText("");
                if (null != listener) {
                    listener.onClean();
                }
            }
        });
        tvSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtil.hideKeyboard(v);
                if (null != listener) {
                    listener.onSearch(SearchEditText.this.getText());
                }
            }
        });
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (null != listener && (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_SEARCH)) {
            KeyBoardUtil.hideKeyboard(v);
            listener.onSearch(getText());
        }
        return false;
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() < 1) {
            ivClean.setVisibility(INVISIBLE);
            tvSearch.setVisibility(GONE);
        } else {
            ivClean.setVisibility(VISIBLE);
            tvSearch.setVisibility(VISIBLE);
        }
        if (null != listener) {
            listener.onTextChanged(s);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    }

    public String getText() {
        return edtSearch.getText().toString().trim();
    }

    public void setText(String s) {
        edtSearch.setText(s);
    }

    public void setHint(String s) {
        strHint = s;
        edtSearch.setHint(s);
    }

    private OnSearchListener listener;

    public interface OnSearchListener {
        void onSearch(String s);

        void onClean();

        void onTextChanged(Editable s);
    }

    public void setOnSearchListener(OnSearchListener listener) {
        this.listener = listener;
    }
}
