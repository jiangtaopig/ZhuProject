package com.example.za_zhujiangtao.zhupro.recycle;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/24
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MsgHolder> {

    private List<MsgBean> msgBeanList;

    public RecycleAdapter(){
        msgBeanList = new ArrayList<>();
    }

    public void setMsgBeanList(List<MsgBean> list){
        msgBeanList.clear();
        msgBeanList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(MsgBean msgBean){
        msgBeanList.add(msgBean);
    }

    @NonNull
    @Override
    public MsgHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("RecycleAdapter", "---- onCreateViewHolder ----- ");
        return new MsgHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_live_chat_message, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MsgHolder msgHolder, int i) {
        Log.e("RecycleAdapter", "onBindViewHolder  i = "+i);
        msgHolder.bindData(i);
    }

    @Override
    public int getItemCount() {
        return msgBeanList.size();
    }

    public class MsgHolder extends RecyclerView.ViewHolder{

        TextView nameTv;
        TextView msgTv;

        public MsgHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.txt_name);
            msgTv = itemView.findViewById(R.id.txt_msg);
        }

        public void bindData(int position){
            MsgBean bean = msgBeanList.get(position);
            nameTv.setText(bean.name);
            msgTv.setText(bean.msg);
        }
    }
}
