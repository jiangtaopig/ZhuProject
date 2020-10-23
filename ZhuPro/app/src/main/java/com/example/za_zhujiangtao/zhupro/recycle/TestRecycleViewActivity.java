package com.example.za_zhujiangtao.zhupro.recycle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 模拟直播发送消息
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

        // 由于我的 itemView 的高度是固定的23dp，所以设置 setHasFixedSize(true)，这样Recycleview 在 onMeasure 的时候可以直接计算出 RecycleView 的高度,而不需要多次计算子 itemView 的高度，
        // 这种情况对于垂直RecyclerView中嵌套横向RecyclerView效果非常显著。
        recyclerView.setHasFixedSize(true);


        List<MsgBean> msgBeanList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            String name = "小安" + i;
            String msg = "消息" + i;
            msgBeanList.add(new MsgBean(name, msg));
        }
        recycleAdapter.setMsgBeanList(msgBeanList);
        sendMsg.setOnClickListener(v -> {
//            int random = new Random().nextInt(20) + 1;
//            String name = "小安" + random;
//            String msg = "消息" + random;
//            addMessage(new MsgBean(name, msg));
            recycleAdapter.notifyDataSetChanged();
            recycleAdapter.notifyItemChanged(0);
        });
    }

    public void addMessage(MsgBean msgBean) {
        int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        int count = recycleAdapter.getItemCount();
        Log.e("TestRecycleViewActivity", "count = " + count + ", lastVisibleItemPosition = " + lastVisibleItemPosition);
        boolean scrollToBottom = (count - 1 <= lastVisibleItemPosition);
        recycleAdapter.addData(msgBean);
        recycleAdapter.notifyDataSetChanged();

        if (scrollToBottom) {
            recyclerView.post(() -> recyclerView.scrollToPosition(recycleAdapter.getItemCount() - 1));
        }
    }
}
