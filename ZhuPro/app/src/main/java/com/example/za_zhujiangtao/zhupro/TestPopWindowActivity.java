package com.example.za_zhujiangtao.zhupro;

import android.app.IntentService;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/10/28
 */
public class TestPopWindowActivity extends BaseActivity {

    @BindView(R.id.start_pop)
    Button button;

    @BindView(R.id.start_anim)
    Button startAnim;

    @BindView(R.id.anim_root_layout)
    LinearLayout mAnimRootLayout;

    @BindView(R.id.text)
    TextView mTv;

    @Override
    protected int layoutId() {
        return R.layout.activity_pop_window_layout;
    }

    @Override
    protected void onInitLogic() {


//        concurrentHashMap.put("key2", "bb");

        button.setOnClickListener(v -> {
//            showPopWindow();
            ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
            concurrentHashMap.put("key1", "aa");

        });

        startAnim.setOnClickListener(v -> {
            TransitionSet transitionSet = new TransitionSet();
            transitionSet
                    .addTransition(new Fade().addTarget(mAnimRootLayout))
                    .addTransition(new ChangeBounds())
                    .addTransition(new Slide(Gravity.TOP).addTarget(mTv))
//                    .addTransition(new Fade().addTarget(mTv))
                    .setInterpolator(new DecelerateInterpolator());
            TransitionManager.beginDelayedTransition((ViewGroup) getWindow().getDecorView(), transitionSet);

            if (mAnimRootLayout.getVisibility() == View.VISIBLE) {
                mAnimRootLayout.setVisibility(View.GONE);
            }else {
                mAnimRootLayout.setVisibility(View.VISIBLE);
            }
        });

    }

    private void showPopWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_window_view, null, false);
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // 设置背景颜色
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置可以获取焦点
        popupWindow.setFocusable(true);
        // 设置可以触摸弹出框以外的区域
        popupWindow.setOutsideTouchable(true);
        // 更新popupwindow的状态
//        popupWindow.update();
        // 以下拉的方式显示，并且可以设置显示的位置
        popupWindow.showAsDropDown(button, 0, 20);
//        popupWindow.showAtLocation(button, Gravity.LEFT | Gravity.BOTTOM, 0, 50);//这里的50是因为我底部按钮的高度是50

    }
}
