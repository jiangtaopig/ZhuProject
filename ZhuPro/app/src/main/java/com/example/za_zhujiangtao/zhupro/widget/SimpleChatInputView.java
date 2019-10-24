package com.example.za_zhujiangtao.zhupro.widget;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.za_zhujiangtao.zhupro.R;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.TransitionManager;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/9/9
 */
public class SimpleChatInputView extends LinearLayout {

    ImageView mSender;

    EditText mTxtInput;

    ImageView mPhotoImg;

    ImageView mPhoneImg;

//    CustomerServiceView mCustomerServiceView;

    public SimpleChatInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.simple_chat_input_view, this);
        initView(context);

    }

    private void initView(Context context){
        mPhotoImg = findViewById(R.id.photo);
        mPhoneImg = findViewById(R.id.phone);
        mTxtInput = findViewById(R.id.input);
        mSender = findViewById(R.id.send);

        mTxtInput.setOnClickListener(v -> {
            onEditClick();
        });

        mTxtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("zjt", "beforeTextChanged s = "+s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("zjt", "onTextChanged s = "+s.toString());
                if (TextUtils.isEmpty(s.toString())){
                    mSender.setImageDrawable(context.getResources().getDrawable(R.drawable.customer_send_default));
                }else {
                    mSender.setImageDrawable(context.getResources().getDrawable(R.drawable.customer_send_icon));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("zjt", "afterTextChanged s = "+s.toString());
            }
        });

        mTxtInput.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                onEditClick();
            }
            return false;
        });

        mSender.setOnClickListener(v -> {
        });

        mPhotoImg.setOnClickListener(v -> {
        });
    }

    public void setText(String text){
        mTxtInput.setText(text);
    }

    private void onEditClick() {
//        KeyboardUtil.showKeyboard(mTxtInput);
//        if (mCustomerServiceView != null){
//            mTxtInput.postDelayed(() -> mCustomerServiceView.onEditTextClick(),150);
//        }
    }

//    public void setCustomerServiceView(CustomerServiceView view){
//        mCustomerServiceView = view;
//    }

    public void hidedKeyboard() {
        TransitionManager.beginDelayedTransition(this,new ChangeBounds().setDuration(200).setInterpolator(new DecelerateInterpolator()));
//        KeyboardUtil.hideKeyboard(mTxtInput);
        mTxtInput.clearFocus();//隐藏软键盘时，失去焦点
    }

    public void showImgOrPhone(boolean show){
        if (show){
            mPhotoImg.setVisibility(VISIBLE);
            mPhoneImg.setVisibility(VISIBLE);
        }else {
            mPhotoImg.setVisibility(GONE);
            mPhoneImg.setVisibility(GONE);
        }
    }

    public void setCallPhone(Activity activity, String phone){
        mPhoneImg.setOnClickListener(v -> {
//            PhoneUtil.callPhone(activity, phone);
        });
    }

}
