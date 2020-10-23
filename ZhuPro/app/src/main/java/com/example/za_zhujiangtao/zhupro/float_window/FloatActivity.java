package com.example.za_zhujiangtao.zhupro.float_window;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.swipe_back.app.TestSwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

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

    @BindView(R.id.txt_hh)
    TextView textView;

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

        List<String> strings = new ArrayList<>();
        strings.add("1");

        setDrawableInTxt(this, textView, "我的文章我的文章我的文章我的文章我的文章我的文章我的文章我的文章我的文章我的文章我的文章我的文章", R.drawable.contact_add_receiver);
    }

    private void setDrawableInTxt(Context context, TextView tv, String str, int drawable) {
        SpannableString ss = new SpannableString("logo");
        Drawable d = context.getResources().getDrawable(drawable);//得到drawable对象，即所要插入的图片
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);//用这个drawable对象代替字符串easy
        //包括0但是不包括"easy".length()即：4。[0,4)。值得注意的是当我们复制这个图片的时候，实际是复制了"easy"这个字符串。
        ss.setSpan(span, 0, "logo".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setText("");
        tv.append(ss);
        tv.append(str);
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
                                WindowUtil.getInstance().showPermissionWindow(FloatActivity.this, null, true);
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
