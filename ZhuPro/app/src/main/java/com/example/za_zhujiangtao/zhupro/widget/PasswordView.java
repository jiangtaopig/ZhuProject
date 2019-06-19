package com.example.za_zhujiangtao.zhupro.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.utils.Utils;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 密码输入控件
 * Created by za-fanjianfeng on 2018/3/2.
 */

public class PasswordView extends FrameLayout {
    private static final int DEFAULT_PASSWORD_LENGTH = 6;
    private static final boolean DEFAULT_PASSWORD_VISIBLE = false;
    private static final int DEFAULT_PASSWORD_SPACING = Utils.getDimen(R.dimen.dimen_15dp);
    private static final int DEFAULT_PASSWORD_WIDTH = Utils.getDimen(R.dimen.dimen_50dp);
    private static final int DEFAULT_PASSWORD_HEIGHT = DEFAULT_PASSWORD_WIDTH;
    private static final int DEFAULT_PASSWORD_BACKGROUND = R.drawable.round_e1e1e1;
    private static final int DEFAULT_PASSWORD_APPEARANCE = R.style.font_14sp_white;
    private static final int INPUT_TYPE_TEXT = 0;
    private static final int INPUT_TYPE_NUMBER = 1;
    private static final int DEFAULT_PASSWORD_INPUT_TYPE = INPUT_TYPE_TEXT;
    private static final String DEFAULT_TRANSFORMATION = "●";
    /**
     * 密码长度
     */
    private int mPasswordLength;
    /**
     * 密码是否可见
     */
    private boolean mPasswordVisible;
    /**
     * 密码框间距
     */
    private int mPasswordSpacing;
    /**
     * 密码框单个宽高
     */
    private int mPasswordWidth;
    private int mPasswordHeight;
    /**
     * 密码框背景
     */
    private int mPasswordBackground;
    /**
     * 密码字体
     */
    private int mPasswordAppearance;
    /**
     * 密码输入类型
     */
    private int mPasswordInputType;
    private List<TextView> mTxtPasswords;
    private EditText mTxtRealPassword;
    private OnPasswordChangeListener mOnPasswordChangeListener;
    private OnPasswordCompleteListener mOnPasswordCompleteListener;

    public PasswordView(Context context) {
        this(context, null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initContent();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordView);
            mPasswordLength = typedArray.getInt(R.styleable.PasswordView_passwordLength, DEFAULT_PASSWORD_LENGTH);
            mPasswordVisible = typedArray.getBoolean(R.styleable.PasswordView_passwordVisible, DEFAULT_PASSWORD_VISIBLE);
            mPasswordSpacing = typedArray.getDimensionPixelSize(R.styleable.PasswordView_passwordSpacing, DEFAULT_PASSWORD_SPACING);
            mPasswordWidth = typedArray.getDimensionPixelOffset(R.styleable.PasswordView_passwordWidth, DEFAULT_PASSWORD_WIDTH);
            mPasswordHeight = typedArray.getDimensionPixelOffset(R.styleable.PasswordView_passwordHeight, DEFAULT_PASSWORD_HEIGHT);
            mPasswordBackground = typedArray.getResourceId(R.styleable.PasswordView_passwordBackground, DEFAULT_PASSWORD_BACKGROUND);
            mPasswordAppearance = typedArray.getResourceId(R.styleable.PasswordView_passwordAppearance, DEFAULT_PASSWORD_APPEARANCE);
            mPasswordInputType = typedArray.getInt(R.styleable.PasswordView_passwordInputType, DEFAULT_PASSWORD_INPUT_TYPE);
            typedArray.recycle();
        } else {
            mPasswordLength = DEFAULT_PASSWORD_LENGTH;
            mPasswordVisible = DEFAULT_PASSWORD_VISIBLE;
            mPasswordSpacing = DEFAULT_PASSWORD_SPACING;
            mPasswordWidth = DEFAULT_PASSWORD_WIDTH;
            mPasswordHeight = DEFAULT_PASSWORD_HEIGHT;
            mPasswordBackground = DEFAULT_PASSWORD_BACKGROUND;
            mPasswordAppearance = DEFAULT_PASSWORD_APPEARANCE;
            mPasswordInputType = DEFAULT_PASSWORD_INPUT_TYPE;
        }
    }

    private void initContent() {
        //实际由隐藏的EditText处理
        LayoutParams realPassParam = new LayoutParams(1, 1);
        realPassParam.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        mTxtRealPassword = new EditText(getContext());
        mTxtRealPassword.setLayoutParams(realPassParam);
        mTxtRealPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mPasswordLength)});
        if (mPasswordInputType == INPUT_TYPE_TEXT) {
            mTxtRealPassword.setInputType(InputType.TYPE_CLASS_TEXT);
        } else {
            mTxtRealPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        addView(mTxtRealPassword);

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mTxtPasswords = new ArrayList<>();
        for (int i = 0; i < mPasswordLength; i++) {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mPasswordWidth, mPasswordHeight);
            if (i < mPasswordLength - 1) {
                layoutParams.rightMargin = mPasswordSpacing;
            }
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundResource(mPasswordBackground);
            Utils.setTextAppearance(textView, mPasswordAppearance);
            mTxtPasswords.add(textView);
            linearLayout.addView(textView);
        }
        addView(linearLayout);
        linearLayout.setOnClickListener(v -> {
            mTxtRealPassword.requestFocus();
            Utils.showKeyboard(mTxtRealPassword);
        });
        RxTextView.textChanges(mTxtRealPassword)
                .subscribe(charSequence -> {
                    if (mOnPasswordChangeListener != null) {
                        mOnPasswordChangeListener.onPasswordChangeListener(charSequence);
                    }
                    for (int i = 0; i < mTxtPasswords.size(); i++) {
                        TextView textView = mTxtPasswords.get(i);
                        if (i < charSequence.length()) {
                            if (mPasswordVisible) {
                                textView.setText(String.valueOf(charSequence.charAt(i)));
                            } else {
                                textView.setText(DEFAULT_TRANSFORMATION);
                            }
                        } else {
                            textView.setText("");
                        }
                    }
                    if (charSequence.length() >= mPasswordLength && mOnPasswordCompleteListener != null) {
                        mOnPasswordCompleteListener.onPasswordCompleteListener(charSequence);
                    }
                });
    }

    public OnPasswordChangeListener getOnPasswordChangeListener() {
        return mOnPasswordChangeListener;
    }

    public void setPassword(String password){
        mTxtRealPassword.setText(password);
        mTxtRealPassword.setSelection(password.length());
    }

    public void setSpacing(int spacing) {
        mPasswordSpacing = spacing;
        for (TextView textView: mTxtPasswords) {
            ((MarginLayoutParams)textView.getLayoutParams()).rightMargin = mPasswordSpacing / 2;
            ((MarginLayoutParams)textView.getLayoutParams()).leftMargin = mPasswordSpacing / 2;
        }
        requestLayout();
    }

    public void setOnPasswordChangeListener(OnPasswordChangeListener onPasswordChangeListener) {
        this.mOnPasswordChangeListener = onPasswordChangeListener;
    }

    public OnPasswordCompleteListener getOnPasswordCompleteListener() {
        return mOnPasswordCompleteListener;
    }

    public void setOnPasswordCompleteListener(OnPasswordCompleteListener onPasswordCompleteListener) {
        this.mOnPasswordCompleteListener = onPasswordCompleteListener;
    }

    /**
     * 清除密码
     */
    public void clearPassword() {
        mTxtRealPassword.setText("");
    }

    /**
     * 获取密码
     *
     * @return
     */
    public Editable getPassword() {
        return mTxtRealPassword.getText();
    }

    /**
     * 隐藏键盘
     */
    public void hideKeyboard() {
        Utils.hideKeyBoard(mTxtRealPassword);
    }

    /**
     * 显示键盘
     */
    public void showKeyboard() {
        Utils.showKeyboard(mTxtRealPassword);
    }

    public EditText getEdit(){
        return mTxtRealPassword;
    }

    public int getPasswordWidth() {
        return mPasswordWidth;
    }

    public int getPasswordLength() {
        return mPasswordLength;
    }

    public void requestPwdFocus(){
        mTxtRealPassword.requestFocus();
    }
    /**
     * 密码改变监听
     */
    public interface OnPasswordChangeListener {
        void onPasswordChangeListener(CharSequence charSequence);
    }

    /**
     * 密码输入完成监听
     */
    public interface OnPasswordCompleteListener {
        void onPasswordCompleteListener(CharSequence charSequence);
    }
}
