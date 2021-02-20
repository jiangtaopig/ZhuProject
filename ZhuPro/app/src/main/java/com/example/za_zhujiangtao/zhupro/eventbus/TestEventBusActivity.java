package com.example.za_zhujiangtao.zhupro.eventbus;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/2/20
 */
public class TestEventBusActivity extends BaseActivity {

    @BindView(R.id.btn_register_eventbus)
    Button mRigisterEventBusBtn;

    @BindView(R.id.post_sticky_event)
    Button mPostStickyEventBtn;

    @Override
    protected int layoutId() {
        return R.layout.activity_eventbus_layout;
    }

    @Override
    protected void onInitLogic() {
        EventBus.getDefault().register(this);
        mRigisterEventBusBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AnotherEventActivity.class);
            startActivity(intent);
        });

        mPostStickyEventBtn.setOnClickListener(v -> {
            EventBus.getDefault().postSticky(new TestEvent("I am spider"));
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMyEvent(MyEvent event){
        if (event != null){
            String action = event.getAction();
            Log.e("xxx", "action = " + action);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
