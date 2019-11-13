package com.example.za_zhujiangtao.zhupro.city_select;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.R;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/11/13
 */
public class HomeFragment extends Fragment {

    private LayoutInflater mInflater;
    private TextView mTxt;
    private OnFragmentClickListener mFragmentClickListener;

    public static HomeFragment getInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return mInflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTxt = getView().findViewById(R.id.home_txt);

        mTxt.setOnClickListener(v -> {
            if (mFragmentClickListener != null){
                mFragmentClickListener.onFfagmentClick(1);
            }
        });
    }

    public void setFragmentClickListener(OnFragmentClickListener clickListener) {
        this.mFragmentClickListener = clickListener;
    }
}
