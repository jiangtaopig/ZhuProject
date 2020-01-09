package com.example.za_zhujiangtao.zhupro.section_recycle;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.float_window.DisplayUtil;
import com.example.za_zhujiangtao.zhupro.section_recycle.entity.ImageTypeData;
import com.example.za_zhujiangtao.zhupro.section_recycle.entity.MoreTypeBaseData;
import com.example.za_zhujiangtao.zhupro.section_recycle.entity.NormalTypeData;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/1/9
 */
public class MoreTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int IMAGE_VIEW = 1;
    public static final int NORMAL_VIEW = IMAGE_VIEW + 1;

    private List<MoreTypeBaseData> moreTypeBaseDataList;

    public MoreTypeAdapter(){
        moreTypeBaseDataList = new ArrayList<>();
    }

    public void setMoreTypeBaseDataList(List<MoreTypeBaseData> typeBaseDataList){
        moreTypeBaseDataList.clear();
        moreTypeBaseDataList.addAll(typeBaseDataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == IMAGE_VIEW){
            return new ImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_header_layout, parent, false));
        }else if (viewType == NORMAL_VIEW){
            return new NormalHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_hotel_item_layout, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ImageHolder){
            ((ImageHolder) viewHolder).bindImageData(position);
        }else if (viewHolder instanceof NormalHolder){
            ((NormalHolder) viewHolder).bindNormalData(position);
        }
    }

    @Override
    public int getItemCount() {
        return moreTypeBaseDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int type = moreTypeBaseDataList.get(position).getHolderType();
        if (type == IMAGE_VIEW){
            return IMAGE_VIEW;
        }else if (type == NORMAL_VIEW){
            return NORMAL_VIEW;
        }
        return super.getItemViewType(position);
    }

    public class ImageHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleTv;
        TextView propertyTv;
        LinearLayout tagLayout;
        TextView showMoreTv;
        View splitLine;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.poster);
            titleTv = itemView.findViewById(R.id.title_tv);
            propertyTv = itemView.findViewById(R.id.property_tv);
            tagLayout = itemView.findViewById(R.id.tag_layout);
            showMoreTv = itemView.findViewById(R.id.show_more_tv);
            splitLine = itemView.findViewById(R.id.horizontal_split_line);
        }

        public void bindImageData(int position){
            ImageTypeData typeData = (ImageTypeData) moreTypeBaseDataList.get(position);
            StringBuilder properties = new StringBuilder();
            if (typeData.properties != null && typeData.properties.size() > 0){
                for (String s : typeData.properties){
                    properties.append(s).append(" ");
                }
            }
            propertyTv.setText(properties.toString());
            titleTv.setText(typeData.title);

            tagLayout.removeAllViews();
            if (typeData.tags != null && typeData.tags.size() > 0){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.rightMargin = DisplayUtil.dip2px(3);
                for (String tag : typeData.tags){
                    TextView textView = new TextView(itemView.getContext());
                    textView.setText(tag);
                    textView.setLayoutParams(params);
                    textView.setBackgroundColor(Color.parseColor("#ff0000"));
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                    textView.setPadding(1, 1, 1, 1);
                    textView.setGravity(Gravity.CENTER);
                    tagLayout.addView(textView);
                }
            }
        }
    }

    public class NormalHolder extends RecyclerView.ViewHolder{

        TextView propertyTv;
        TextView cancelTv;
        LinearLayout tagLayout;
        TextView buyTv;
        View splitLine;

        public NormalHolder(@NonNull View itemView) {
            super(itemView);
            propertyTv = itemView.findViewById(R.id.property_tv);
            tagLayout = itemView.findViewById(R.id.tag_layout);
            cancelTv = itemView.findViewById(R.id.cancel_tv);
            buyTv = itemView.findViewById(R.id.buy_now);
            splitLine = itemView.findViewById(R.id.horizontal_split_line);

        }

        public void bindNormalData(int position){
            NormalTypeData normalTypeData = (NormalTypeData) moreTypeBaseDataList.get(position);
            StringBuilder properties = new StringBuilder();
            if (normalTypeData.propertyList != null && normalTypeData.propertyList.size() > 0){
                for (String s : normalTypeData.propertyList){
                    properties.append(s).append(" ");
                }
            }
            propertyTv.setText(properties.toString());
            cancelTv.setText(normalTypeData.cancelInfo);
            if (normalTypeData.tagList != null && normalTypeData.tagList.size() > 0){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.rightMargin = DisplayUtil.dip2px(3);
                for (String tag : normalTypeData.tagList){
                    TextView textView = new TextView(itemView.getContext());
                    textView.setText(tag);
                    textView.setLayoutParams(params);
                    textView.setBackgroundColor(Color.parseColor("#ff0000"));
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                    textView.setPadding(1, 1, 1, 1);
                    textView.setGravity(Gravity.CENTER);
                    tagLayout.addView(textView);
                }
            }
        }
    }
}
