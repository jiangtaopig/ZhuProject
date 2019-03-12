package com.example.za_zhujiangtao.zhupro.appbar_test;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by za-zhujiangtao on 2018/11/14.
 */

public class MyToolbarActivity extends AppCompatActivity {
    @BindView(R.id.img_zhangdan)
    ImageView mImgZhangdan;

    @BindView(R.id.img_zhangdan_txt)
    TextView mImgZhangdanTxt;

    @BindView(R.id.toolbar1)
    View toolbar1;

    @BindView(R.id.toolbar2)
    View toolbar2;

    @BindView(R.id.head_layout)
    ViewGroup mHeadLayout;

    @BindView(R.id.jiahao)
    ImageView mJiahao;

    @BindView(R.id.tongxunlu)
    ImageView mTongxunlu;

    @BindView(R.id.img_shaomiao)
    ImageView mImgShaomiao;

    @BindView(R.id.img_fukuang)
    ImageView mImgFukuang;

    @BindView(R.id.img_search)
    ImageView mImgSearch;

    @BindView(R.id.img_zhaoxiang)
    ImageView mImgZhaoxiang;

    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.rv)
    RecyclerView mRv;

    @BindView(R.id.activity_main)
    CoordinatorLayout mActivityMain;

    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_toolbar_layout);
        ButterKnife.bind(this);

        mRv.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(this);
        mRv.setAdapter(myAdapter);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScrollRange = appBarLayout.getTotalScrollRange();


                verticalOffset = Math.abs(verticalOffset);
                float alphaPercent = maxScrollRange != 0 ? (float) verticalOffset / (float) maxScrollRange : 0;
                if (alphaPercent > .95f) {
                    alphaPercent = 1f;
                } else if (alphaPercent < .05f) {
                    alphaPercent = 0f;
                }
                Log.e("MyToolbarActivity", "verticalOffset = " + verticalOffset + ", scrollRange = " + maxScrollRange
                        + ", alphaPercent = " + alphaPercent);
                toolbar2.setAlpha(1 - alphaPercent);
                toolbar1.setAlpha(alphaPercent);

//                if (verticalOffset == 0) {
//                    //完全展开
//                    toolbar1.setVisibility(View.VISIBLE);
//                    toolbar2.setVisibility(View.GONE);
//                    setToolbar1Alpha(255);
//                } else if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
//                    //appBarLayout.getTotalScrollRange() == 200
//                    //完全折叠
//                    toolbar1.setVisibility(View.GONE);
//                    toolbar2.setVisibility(View.VISIBLE);
//                    setToolbar2Alpha(255);
//                } else {//0~200上滑下滑
//                    if (toolbar1.getVisibility() == View.VISIBLE) {
////                        //操作Toolbar1
//                        int alpha = 300 - 155 - Math.abs(verticalOffset);
//                        Log.i("alpha:", alpha + "");
//                        setToolbar1Alpha(alpha);
//
//                    } else if (toolbar2.getVisibility() == View.VISIBLE) {
//                        if (Math.abs(verticalOffset) > 0 && Math.abs(verticalOffset) < 200) {
//                            toolbar1.setVisibility(View.VISIBLE);
//                            toolbar2.setVisibility(View.GONE);
//                            setToolbar1Alpha(255);
//                        }
////                        //操作Toolbar2
//                        int alpha = (int) (255 * (Math.abs(verticalOffset) / 100f));
//                        setToolbar2Alpha(alpha);
//                    }
//                }
            }
        });

        List<String> datas = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            datas.add("我是数据" + i);
        }
        myAdapter.setItemData(datas);
    }


    private void setToolbar1Alpha(int alpha) {
        mImgZhangdan.getDrawable().setAlpha(alpha);
        mImgZhangdanTxt.setTextColor(Color.argb(alpha, 255, 255, 255));
        mTongxunlu.getDrawable().setAlpha(alpha);
        mJiahao.getDrawable().setAlpha(alpha);
    }

    private void setToolbar2Alpha(int alpha) {
        mImgShaomiao.getDrawable().setAlpha(alpha);
        mImgFukuang.getDrawable().setAlpha(alpha);
        mImgSearch.getDrawable().setAlpha(alpha);
        mImgZhaoxiang.getDrawable().setAlpha(alpha);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<String> mItemData;
        private Context mContext;

        public MyAdapter(Context context) {
            this.mContext = context;
            mItemData = new ArrayList<>();
        }

        public void setItemData(List<String> stringList) {
            mItemData.clear();
            mItemData.addAll(stringList);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.my_adapter_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.title.setText(mItemData.get(position));
        }

        @Override
        public int getItemCount() {
            return mItemData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.my_title)
            TextView title;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
