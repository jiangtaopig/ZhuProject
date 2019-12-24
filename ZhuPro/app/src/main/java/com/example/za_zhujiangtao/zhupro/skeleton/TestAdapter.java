package com.example.za_zhujiangtao.zhupro.skeleton;

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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/24
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {


    private List<ItemData> dataList;

    public TestAdapter(){
        dataList = new ArrayList<>();
    }

    public void setDataList(List<ItemData> data){
        dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TestHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TestHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.skeleton_item_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TestHolder testHolder, int i) {
        testHolder.bindData(i);
    }

    @Override
    public int getItemCount() {
        Log.e("zjt", "getItemCount size = "+dataList.size());
        return dataList.size();
    }

    class TestHolder extends RecyclerView.ViewHolder{

        TextView dateTv;

        TextView titleTv;

        TextView subTitleTv;


        public TestHolder(@NonNull View itemView) {
            super(itemView);
            dateTv = itemView.findViewById(R.id.date);
            titleTv = itemView.findViewById(R.id.title);
            subTitleTv = itemView.findViewById(R.id.sub_title);
        }


        public void bindData(int position){
            Log.e("zjt", "bindData position = "+position);
            ItemData data = dataList.get(position);

            dateTv.setText(data.date);
            titleTv.setText(data.title);
            subTitleTv.setText(data.subTitle);
        }
    }

}
