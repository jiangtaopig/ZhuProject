package com.example.za_zhujiangtao.zhupro.eventbus;

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
public class AnotherEventActivity extends BaseActivity {

    @BindView(R.id.post_normal_event)
    Button mPostNormalEventBtn;

    @Override
    protected int layoutId() {
        return R.layout.activity_another_event_layout;
    }

    @Override
    protected void onInitLogic() {
        EventBus.getDefault().register(this);
        mPostNormalEventBtn.setOnClickListener(v -> {
            EventBus.getDefault().post(new MyEvent("I am superman"));
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void doMyEvent(TestEvent event) {
        if (event != null) {
            String name = event.getAction();
            Log.e("xxx", "name = " + name);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
