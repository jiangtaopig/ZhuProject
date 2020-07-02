package com.example.za_zhujiangtao.zhupro.recycle;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;

import java.util.Random;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/24
 */
public class TestRecycleViewActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private RecycleAdapter recycleAdapter;
    private TextView sendMsg;

    @Override
    protected int layoutId() {
        return R.layout.activity_test_recycle_view_layout;
    }

    @Override
    protected void onInitLogic() {
        sendMsg = findViewById(R.id.sendMsg);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recycleAdapter = new RecycleAdapter();

        recyclerView.setAdapter(recycleAdapter);

        recyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            int maxHeight = (int) getResources().getDimension(R.dimen.dimen_170dp);

            @Override
            public boolean onPreDraw() {
                if (recyclerView.getHeight() >= maxHeight) {
                    recyclerView.getLayoutParams().height = maxHeight;
                    recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                }
                return true;
            }
        });


        sendMsg.setOnClickListener(v -> {
            int random = new Random().nextInt(20) + 1;
            String name = "小安" + random;
            String msg = "消息" + random;
            addMessage(new MsgBean(name, msg));
        });

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
    }


    public void addMessage(MsgBean msgBean) {
        int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        int count = recycleAdapter.getItemCount();
        Log.e("TestRecycleViewActivity", "count = "+count+", lastVisibleItemPosition = "+lastVisibleItemPosition);
        boolean scrollToBottom = (count - 1 <= lastVisibleItemPosition);
        recycleAdapter.addData(msgBean);
        recycleAdapter.notifyDataSetChanged();
        if (scrollToBottom) {
            recyclerView.post(() -> recyclerView.scrollToPosition(recycleAdapter.getItemCount() - 1));
        }
    }
}
