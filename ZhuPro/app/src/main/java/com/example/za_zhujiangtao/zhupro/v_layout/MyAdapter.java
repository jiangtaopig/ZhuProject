package com.example.za_zhujiangtao.zhupro.v_layout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.example.za_zhujiangtao.zhupro.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by za-zhujiangtao on 2019/1/2.
 */

public class MyAdapter extends DelegateAdapter.Adapter<RecyclerView.ViewHolder>{

    public final static int NORMAL_TYPE = 1;
    public final static int AD_TYPE = NORMAL_TYPE + 1;

    private LayoutHelper mLayoutHelper;
    private Context mContext;
    private List<TestData> mList;
    private LayoutInflater mInflater;

    public MyAdapter(Context context, LayoutHelper layoutHelper){
        this.mContext = context;
        this.mLayoutHelper = layoutHelper;
        mList = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }

    public void setList( List<TestData> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL_TYPE){
            return new NormalHolder(mInflater.inflate(R.layout.normal_item_view, parent, false));
        } else if (viewType == AD_TYPE){
            return new ADViewHolder(mInflater.inflate(R.layout.ad_item_view, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalHolder){
            ((NormalHolder) holder).bindData(position);
        }else if (holder instanceof ADViewHolder){
            ((ADViewHolder) holder).bindData(position);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (NORMAL_TYPE == mList.get(position).type){
            return NORMAL_TYPE;
        }else if (AD_TYPE == mList.get(position).type){
            return AD_TYPE;
        }
        return super.getItemViewType(position);
    }

    public class NormalHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.title)
        TextView titleTv;

        public NormalHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(int position){
            TestData testData = mList.get(position);
            titleTv.setText(testData.title);
        }

    }

    public class ADViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.title_tv)
        TextView name;

        public ADViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(int position){
            TestData testData = mList.get(position);
            name.setText(testData.title);
        }
    }
}
