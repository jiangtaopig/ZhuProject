package com.example.za_zhujiangtao.zhupro;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
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

import com.example.za_zhujiangtao.zhupro.dragger_recycleview.GridDividerItemDecoration;
import com.example.za_zhujiangtao.zhupro.dragger_recycleview.ItemTouchHelperAdapter;
import com.example.za_zhujiangtao.zhupro.dragger_recycleview.ItemTouchHelperViewHolder;
import com.example.za_zhujiangtao.zhupro.dragger_recycleview.SimpleItemTouchHelperCallback;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by za-zhujiangtao on 2018/12/13.
 */

public class EditPosterAdapter extends RecyclerView.Adapter<EditPosterAdapter.EditPosterViewHolder> implements ItemTouchHelperAdapter {

    private List<String> mList;
    private Context mContext;
    private OnAddImageListener mOnAddImageListener;

    public EditPosterAdapter(Context context, RecyclerView recyclerView) {
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

    public void addData(String imgPath){
        mList.add(0, imgPath);
        notifyDataSetChanged();
    }

    public void setOnAddImageListener(OnAddImageListener listener){
        this.mOnAddImageListener = listener;
    }

    @Override
    public EditPosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EditPosterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.edit_poster_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(EditPosterViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Log.e("EditPosterAdapter", "onItemMove fromPosition = " + fromPosition + ", toPosition = " + toPosition);
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
    public void onItemDismiss(int position) {//删除某个item时调用的方法
        Log.e("EditPosterAdapter", "onItemDismiss position = " + position);
    }

    public class EditPosterViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        @BindView(R.id.edit_img)
        ImageView img;

        public EditPosterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(int position) {
            if (position == getItemCount() - 1) {
                img.setImageResource(R.drawable.add_img);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (mOnAddImageListener != null){
                            mOnAddImageListener.onAddImg();
                        }
                    }
                });
            } else {
                img.setOnClickListener(null);
                img.setImageBitmap(BitmapFactory.decodeFile(mList.get(position)));
            }
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

    public interface OnAddImageListener{
        void onAddImg();
    }


}
