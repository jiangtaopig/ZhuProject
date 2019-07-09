package com.example.za_zhujiangtao.zhupro;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.factory.Shape;
import com.example.za_zhujiangtao.zhupro.factory.ShapeFactory;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DefineViewActivity extends BaseActivity {

    private final static String TAG = "DefineViewActivity";
    @BindView(R.id.animation_tv)
    TextView mTv;

    private JoinMeetingDialog mInputDialog;

    @Override
    protected int layoutId() {
        return R.layout.activity_define_view_layout;
    }

    @Override
    protected void onInitLogic() {
        mTv.setOnClickListener(v -> {
            performAnimation(mTv, mTv.getWidth(), 600);
//            displayInputDialog();
            OkHttpClient client = new OkHttpClient();
            new Thread(){
                @Override
                public void run() {
                    Request request = new Request.Builder()
                            .url("https://www.imooc.com/api/teacher?type=4&num=1")
                            .build();
                    try {
                        Response response = client.newCall(request).execute();
                        Log.e("xxx", "response = "+response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        });

        ShapeFactory shapeFactory = new ShapeFactory();
        Shape shape = shapeFactory.create("Rectangle");
        shape.draw();
    }

    private  void displayInputDialog() {
        if (mInputDialog == null) {
            mInputDialog = new JoinMeetingDialog
                    .Builder()
                    .setShowInputMeetingIdView(true)
                    .setOnConfirmListener(new JoinMeetingDialog.OnConfirmListener() {
                        @Override
                        public void onConfirmMeetingNo(String meetingNo) {
                            mInputDialog.showPwdView();
                        }

                        @Override
                        public void onConfirmPwd(String pwd) {

                        }
                    })
                    .build(this, R.style.LiveBottomDialogTheme);
        }
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mInputDialog.getWindow().getAttributes();
        lp.width = (display.getWidth()); //设置宽度
        mInputDialog.getWindow().setAttributes(lp);
        mInputDialog.setCancelable(true);
        mInputDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mInputDialog.show();
    }

    private void performAnimation(View targetView, int start, int end){
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        IntEvaluator intEvaluator = new IntEvaluator();
        animator.addUpdateListener(animation -> {
            int currentValue = (int) animation.getAnimatedValue();
            float fraction = animation.getAnimatedFraction();
            Log.e(TAG, "currentValue = "+currentValue+", fraction = "+fraction);
            int value = intEvaluator.evaluate(fraction, start, end);
            targetView.getLayoutParams().width = value;
            targetView.requestLayout();
        });
        animator.setDuration(3000);
        animator.start();
    }
}
