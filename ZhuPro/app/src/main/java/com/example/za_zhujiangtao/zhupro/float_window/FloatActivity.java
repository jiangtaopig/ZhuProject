package com.example.za_zhujiangtao.zhupro.float_window;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.swipe_back.app.TestSwipeBackActivity;

import butterknife.BindView;
import cn.jake.share.frdialog.dialog.FRDialog;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/8/8
 */
public class FloatActivity extends BaseActivity {

    @BindView(R.id.show_float_window)
    Button mShowWindow;

    @BindView(R.id.dismiss_float_window)
    Button mDismissWindow;

    @BindView(R.id.show_float_view)
    Button hideFloatView;

    @BindView(R.id.dismiss_float_view)
    Button showFloatView;

//    @BindView(R.id.float_view)
//    JoinFloatView joinFloatView;

    @BindView(R.id.my_img)
    ImageView joinFloatView;

    @BindView(R.id.to_swipe_back)
    Button swipeBack;

    @Override
    protected int layoutId() {
        return R.layout.activity_float_layout;
    }

    @Override
    protected void onInitLogic() {
        mShowWindow.setOnClickListener(v -> {
            WindowUtil.getInstance().showPermissionWindow(FloatActivity.this, ()->{
                showDialog();
            }, false);
        });

        mDismissWindow.setOnClickListener(v -> {
            WindowUtil.getInstance().dismissWindow();
        });

        hideFloatView.setOnClickListener(v -> {
            hideView(300, 300);
        });

        showFloatView.setOnClickListener(v -> {
            hideView(0, 0);
        });

        swipeBack.setOnClickListener(v -> {
            Intent intent = new Intent(FloatActivity.this, TestSwipeBackActivity.class);
            startActivity(intent);
        });
    }

    private void showDialog() {
        FRDialog dialog = new FRDialog.MDBuilder(this)
                .setTitle("悬浮窗权限")
                .setMessage("您的手机没有授予悬浮窗权限，请开启后再试")
                .setPositiveContentAndListener("现在去开启", view -> {
                    RomUtils.applyPermission(this, () -> {
                        new Handler().postDelayed(() -> {
                            if (!RomUtils.checkFloatWindowPermission(this)) {
                                // 授权失败
                                showDialog();
                            } else {
                                WindowUtil.getInstance().showPermissionWindow(FloatActivity.this, null, false);
                            }
                        }, 500);
                    });
                    return true;
                }).setNegativeContentAndListener("暂不开启", view -> true).create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RomUtils.onActivityResult(this, requestCode, resultCode, data);
    }

    private void hideView(int x, int y){
        ObjectAnimator transX = ObjectAnimator.ofFloat(joinFloatView, "translationX", x);
        ObjectAnimator transY = ObjectAnimator.ofFloat(joinFloatView, "translationY", y);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new DecelerateInterpolator());
        animSet.play(transX).with(transY); //先后执行
        animSet.start();
    }
}
