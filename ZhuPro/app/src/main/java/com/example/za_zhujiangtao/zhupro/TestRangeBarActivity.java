package com.example.za_zhujiangtao.zhupro;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.za_zhujiangtao.zhupro.float_window.DisplayUtil;
import com.example.za_zhujiangtao.zhupro.widget.RangeBar;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/11
 */
public class TestRangeBarActivity extends BaseActivity {
    private RangeBar mRangeBar;
    @Override
    protected int layoutId() {
        return R.layout.activity_range_bar_layout;
    }

    @Override
    protected void onInitLogic() {
        mRangeBar = (RangeBar) findViewById(R.id.rangeBar);
        mRangeBar.setOnRangeSelectedListener(new RangeBar.OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(int left, int right) {
                Toast.makeText(TestRangeBarActivity.this, "left= " + left + " right= " + right, Toast.LENGTH_SHORT).show();
            }
        });

        int width = DisplayUtil.getScreenWidth(this);
        Log.e("zjt", "width = "+width);



    }

    public void resetClick(View view) {
        Message message = new Message();
        message.what = 11;
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 11){

                }
            }
        };
        handler.sendMessage(message);
        mRangeBar.setRange(0, 6);
    }
}
