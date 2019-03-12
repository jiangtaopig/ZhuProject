package com.example.za_zhujiangtao.zhupro;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by za-zhujiangtao on 2018/6/8.
 */

public class CustomDialog extends DialogFragment implements DialogInterface {

    @BindView(R.id.positive_txt)
    TextView positiveTxt;

    @BindView(R.id.negative_txt)
    TextView negativeTxt;

    private int mDialogStyle;
    private int mBackgroundResource;
    private int mTitleAppearance;
    private String mTitle;
    private int mSubTitleAppearance;
    private String mSubTitle;
    private int mPositiveAppearance;
    private String mPositiveText;
    private DialogInterface.OnClickListener mOnPositiveClickListener;
    private int mNegativeAppearance;
    private String mNegativeText;
    private DialogInterface.OnClickListener mOnNegativeClickListener;
    private boolean mCancelable;
    private boolean mCanceledOnTouchOutside;
    private DialogInterface.OnCancelListener mOnCancelListener;
    private DialogInterface.OnDismissListener mOnDismissListener;
    @Override
    public void cancel() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.SimpleDialog);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(mOnCancelListener);
        dialog.setOnDismissListener(mOnDismissListener);
        initView(dialog);
        return dialog;
    }


    private void initView(Dialog dialog){
        ButterKnife.bind(this, dialog);

        positiveTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnPositiveClickListener != null){
                    mOnPositiveClickListener.onClick(CustomDialog.this, 0);
                }
            }
        });

        negativeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnNegativeClickListener != null){
                    mOnNegativeClickListener.onClick(CustomDialog.this, 0);
                }
            }
        });
    }



    public static class Builder {
        private static final int DEFAULT_DIALOG_STYLE = R.style.SimpleDialog;
        private int dialogStyle;
        private int backgroundResource;
        private int titleAppearance;
        private String title;
        private int subTitleAppearance;
        private String subTitle;
        private int positiveAppearance;
        private String positiveText;
        private DialogInterface.OnClickListener onPositiveClickListener;
        private int negativeAppearance;
        private String negativeText;
        private DialogInterface.OnClickListener onNegativeClickListener;
        private boolean cancelable;
        private boolean canceledOnTouchOutside;
        private DialogInterface.OnCancelListener onCancelListener;
        private DialogInterface.OnDismissListener onDismissListener;

        public Builder() {
            dialogStyle = DEFAULT_DIALOG_STYLE;
            cancelable = true;
            canceledOnTouchOutside = true;
        }

        public Builder setDialogStyle(int dialogStyle) {
            this.dialogStyle = dialogStyle;
            return this;
        }

        public Builder setBackgroundResource(int backgroundResource) {
            this.backgroundResource = backgroundResource;
            return this;
        }

        public Builder setTitleAppearance(int titleAppearance) {
            this.titleAppearance = titleAppearance;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setSubTitleAppearance(int subTitleAppearance) {
            this.subTitleAppearance = subTitleAppearance;
            return this;
        }

        public Builder setSubTitle(String subTitle) {
            this.subTitle = subTitle;
            return this;
        }

        public Builder setPositiveAppearance(int positiveAppearance) {
            this.positiveAppearance = positiveAppearance;
            return this;
        }

        public Builder setPositiveText(String positiveText) {
            this.positiveText = positiveText;
            return this;
        }

        public Builder setOnPositiveClickListener(DialogInterface.OnClickListener onPositiveClickListener) {
            this.onPositiveClickListener = onPositiveClickListener;
            return this;
        }

        public Builder setNegativeAppearance(int negativeAppearance) {
            this.negativeAppearance = negativeAppearance;
            return this;
        }

        public Builder setNegativeText(String negativeText) {
            this.negativeText = negativeText;
            return this;
        }

        public Builder setOnNegativeClickListener(DialogInterface.OnClickListener onNegativeClickListener) {
            this.onNegativeClickListener = onNegativeClickListener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.onCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public CustomDialog build() {
            CustomDialog simpleDialog = new CustomDialog();
            simpleDialog.mDialogStyle = dialogStyle;
            simpleDialog.mBackgroundResource = backgroundResource;
            simpleDialog.mTitleAppearance = titleAppearance;
            simpleDialog.mTitle = title;
            simpleDialog.mSubTitleAppearance = subTitleAppearance;
            simpleDialog.mSubTitle = subTitle;
            simpleDialog.mPositiveAppearance = positiveAppearance;
            simpleDialog.mPositiveText = positiveText;
            simpleDialog.mOnPositiveClickListener = onPositiveClickListener;
            simpleDialog.mNegativeAppearance = negativeAppearance;
            simpleDialog.mNegativeText = negativeText;
            simpleDialog.mOnNegativeClickListener = onNegativeClickListener;
            simpleDialog.mCancelable = cancelable;
            simpleDialog.mCanceledOnTouchOutside = canceledOnTouchOutside;
            simpleDialog.mOnCancelListener = onCancelListener;
            simpleDialog.mOnDismissListener = onDismissListener;
            return simpleDialog;
        }
    }

}
