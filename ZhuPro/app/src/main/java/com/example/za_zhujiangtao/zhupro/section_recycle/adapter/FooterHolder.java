package com.example.za_zhujiangtao.zhupro.section_recycle.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.R;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/7/29
 */
public class FooterHolder extends RecyclerView.ViewHolder {
    private TextView txt;

    public FooterHolder(@NonNull View itemView) {
        super(itemView);
        txt = itemView.findViewById(R.id.txt_name);
    }

    public void setTxtVisibility(boolean visibility) {
        if (visibility) {
            txt.setVisibility(View.VISIBLE);
        } else {
            txt.setVisibility(View.GONE);
        }
    }
}
