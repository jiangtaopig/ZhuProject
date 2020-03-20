package com.example.za_zhujiangtao.zhupro.token_auto;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/3/20
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomHolder> {

    private List<String> mList;

    public CustomAdapter(){
        mList = new ArrayList<>();
    }

    public void setList(List<String> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_item_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder customHolder, int i) {
        customHolder.bindData(i);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder{

        TextView name;

        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_name);
        }

        public void bindData(int position){
            name.setText(mList.get(position));
        }
    }
}
