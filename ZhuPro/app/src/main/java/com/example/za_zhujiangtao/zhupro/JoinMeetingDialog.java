package com.example.za_zhujiangtao.zhupro;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.utils.ABaseTool;
import com.example.za_zhujiangtao.zhupro.utils.Utils;
import com.example.za_zhujiangtao.zhupro.widget.PasswordView;
import com.transitionseverywhere.TransitionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * zhujiangtao 2019-06-05
 */
public class JoinMeetingDialog extends Dialog {
    @BindView(R.id.group_outside)
    ViewGroup mGroupOutside;

    @BindView(R.id.group_inside)
    ViewGroup mGroupInside;

    @BindView(R.id.input_meeting_id)
    EditText mTxtInput;

    @BindView(R.id.ok)
    TextView mMeetingIdConfirmTV;

    @BindView(R.id.back_arrow)
    ImageView mBackImg;

    @BindView(R.id.meet_password)
    PasswordView mPasswordView;

    @BindView(R.id.meeting_id_hint)
    TextView mMeetingIdHintTV;

    @BindView(R.id.meeting_id_layout)
    LinearLayout mMeetingIdLayout;

    @BindView(R.id.password_layout)
    RelativeLayout mPasswordLayout;

    private Activity mActivity;
    private OnLayoutChangeListener mOnLayoutChangeListener;
    private int mScreenHeight = 0;
    private int mLastDiff = 0;
    private OnConfirmListener mOnConfirmListener;
    private boolean mIsShowInputMeetingIdView = true;

    public JoinMeetingDialog(Context context, int theme, boolean showInputMeetingIdView) {
        super(context, theme);
        setContentView(R.layout.dialog_join_meeting);
        mIsShowInputMeetingIdView = showInputMeetingIdView;
        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this, this);

        mTxtInput.setOnEditorActionListener((v, actionId, event) -> {
            switch (actionId) {
                case KeyEvent.KEYCODE_BACK:
                case KeyEvent.KEYCODE_ENDCALL:
                    dismiss();
                    return false;
                default:
                    return false;
            }
        });

        mTxtInput.post(() -> ABaseTool.showKeyboard(getContext(), mTxtInput));

        mGroupOutside.setOnClickListener(v -> dismiss());

        mGroupInside.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            Rect r = new Rect();
            //获取当前界面可视部分
            getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
            //获取屏幕的高度
            if (mScreenHeight <= 0 &&
                    (getWindow().getDecorView().getRootView().getHeight() == oldBottom || getWindow().getDecorView().getRootView().getHeight() == bottom)) {
                mScreenHeight = getWindow().getDecorView().getRootView().getHeight();
            }
            //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
            int heightDifference = mScreenHeight - r.bottom;

            if (heightDifference <= 0 && mLastDiff > 0) {
                //imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                dismiss();
            } else if (mOnLayoutChangeListener != null) {
                int offset = -heightDifference - mGroupInside.getHeight() - Utils.getStatusBarHeight(mActivity);
                mOnLayoutChangeListener.onLayoutChange(offset);
            }
            mLastDiff = heightDifference;

        });
        mGroupInside.setOnClickListener(v -> dismiss());

        mPasswordView.setOnPasswordCompleteListener(charSequence -> {
            if (mOnConfirmListener != null){
                mOnConfirmListener.onConfirmPwd(charSequence.toString());
            }
        });

        if (mIsShowInputMeetingIdView){
            showInputMeetNoView();
        }else {
            showPwdView();
        }
    }

    @OnClick(R.id.ok)
    protected void confirmMeetingId(){
        if (mOnConfirmListener != null){
            mOnConfirmListener.onConfirmMeetingNo(mTxtInput.getText().toString());
        }
    }

    public void showPwdView() {
        com.transitionseverywhere.Slide slide = new com.transitionseverywhere.Slide(Gravity.RIGHT);
        TransitionManager.beginDelayedTransition(mGroupInside, slide);
        mMeetingIdLayout.setVisibility(View.GONE);
        mPasswordLayout.setVisibility(View.VISIBLE);
        requestPwdFocus();
        mTxtInput.setText("");
    }

    @OnClick(R.id.back_arrow)
    protected void back2MeetingIdInputView(){
        showInputMeetNoView();

    }

    public void showInputMeetNoView() {
        com.transitionseverywhere.Slide slide = new com.transitionseverywhere.Slide(Gravity.RIGHT);
        TransitionManager.beginDelayedTransition(mGroupInside, slide);
        mMeetingIdLayout.setVisibility(View.VISIBLE);
        mPasswordLayout.setVisibility(View.GONE);
        mPasswordView.clearPassword();
    }

    public void requestPwdFocus(){
        mPasswordView.requestPwdFocus();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mOnLayoutChangeListener != null) {
            mOnLayoutChangeListener.onLayoutChange(0);
        }
        //dismiss之前重置mLastDiff值避免下次无法打开
        mLastDiff = 0;
    }

    public static class Builder {
        private OnLayoutChangeListener layoutChangeListener;
        private OnConfirmListener onConfirmListener;
        private boolean isShowInputMeetingIdView;

        public JoinMeetingDialog.Builder setLayoutChangeListener(OnLayoutChangeListener listener) {
            layoutChangeListener = listener;
            return this;
        }

        public Builder setOnConfirmListener(OnConfirmListener listener){
            onConfirmListener = listener;
            return this;
        }

        public Builder setShowInputMeetingIdView(boolean showInputMeetingIdView){
            isShowInputMeetingIdView = showInputMeetingIdView;
            return this;
        }

        public JoinMeetingDialog build(Activity activity, int theme) {
            JoinMeetingDialog dialog = new JoinMeetingDialog(activity, theme, isShowInputMeetingIdView);
            dialog.mActivity = activity;
            dialog.mOnLayoutChangeListener = layoutChangeListener;
            dialog.mOnConfirmListener = onConfirmListener;
            return dialog;
        }
    }

    public interface OnLayoutChangeListener {
        void onLayoutChange(int offset);
    }

    public interface OnConfirmListener{
        void onConfirmMeetingNo(String meetingNo);
        void onConfirmPwd(String pwd);
    }
}
