package com.example.za_zhujiangtao.zhupro.dragger_recycleview;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by za-zhujiangtao on 2018/12/13.
 */

public class DraggerAdapter extends RecyclerView.Adapter<DraggerAdapter.DaggerViewHolder> implements ItemTouchHelperAdapter {

    private List<String> mList;
    private Context mContext;

    public DraggerAdapter(Context context, RecyclerView recyclerView) {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new GridDividerItemDecoration(10));
        SimpleItemTouchHelperCallback callback = new SimpleItemTouchHelperCallback(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        mList = new ArrayList<>();
        mContext = context;
    }

    public void setData(List<String> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public DaggerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DaggerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.dragger_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(DaggerViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Log.e("DraggerAdapter", "onItemMove fromPosition = " + fromPosition + ", toPosition = " + toPosition);
        if (toPosition == mList.size() - 1) {//不让数据拖动到最后一个数据的后面
            return false;
        }
        LinkedList<String> linkedSections = new LinkedList<>(mList);
        linkedSections.remove(fromPosition);
        linkedSections.add(toPosition, mList.get(fromPosition));
        mList.clear();
        mList.addAll(linkedSections);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        Log.e("DraggerAdapter", "onItemDismiss position = " + position);
    }

    public class DaggerViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        @BindView(R.id.item_title)
        TextView title;

        @BindView(R.id.item_img)
        ImageView img;

        public DaggerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(int position) {
            title.setText(mList.get(position));
        }

        @Override
        public void onItemSelected() {
            ViewCompat.animate(itemView)
                    .scaleX(1.1f)
                    .scaleY(1.1f)
                    .setDuration(150)
                    .start();
        }

        @Override
        public void onItemClear() {
            ViewCompat.animate(itemView)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(150)
                    .setListener(new ViewPropertyAnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(View view) {
                            notifyDataSetChanged();
                        }
                    })
                    .start();
        }
    }
}
