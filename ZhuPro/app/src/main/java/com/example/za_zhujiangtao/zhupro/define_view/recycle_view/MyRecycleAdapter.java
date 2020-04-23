package com.example.za_zhujiangtao.zhupro.define_view.recycle_view;

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
 * on 2020/4/20
 */
public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyRecycleHolder> {

    private List<String> mList;
    private int mCreateHolderCmt;

    public MyRecycleAdapter() {
        mList = new ArrayList<>();
    }

    public void setList(List<String> strings) {
        mList.clear();
        mList.addAll(strings);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyRecycleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.e("MyRecycleAdapter", "onCreateViewHolder mCreateHolderCmt = " + mCreateHolderCmt);
        mCreateHolderCmt++;
        return new MyRecycleHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_recycle_item_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecycleHolder myRecycleHolder, int i) {
        Log.e("MyRecycleAdapter", "onBindViewHolder i = " + i);
        myRecycleHolder.bindData(i);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyRecycleHolder extends RecyclerView.ViewHolder {

        TextView mTitle;

        public MyRecycleHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.txt_title);
        }

        public void bindData(int position) {
            mTitle.setText(mList.get(position));
        }
    }
}
