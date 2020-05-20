package com.example.za_zhujiangtao.zhupro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/1/13
 */
public class ImageSliderActivity extends BaseActivity {
    public static final String KEY_IMAGE_SLIDER_LIST = "image_slider_data_list";
    public static final String KEY_IMAGE_SLIDER_INDEX = "image_slider_data_index";

    private PhotoView[] photoViews;
    private ViewPager mViewPager;
    private TextView mCurIndexTV;
    private List<String> mImags;
    private int mSelectPos;

    public static void enter(Context context, List<String> list, int selectPos) {
        Intent intent = new Intent(context, ImageSliderActivity.class);
        intent.putStringArrayListExtra(KEY_IMAGE_SLIDER_LIST, (ArrayList<String>) list);
        intent.putExtra(KEY_IMAGE_SLIDER_INDEX, selectPos);
        startActivity(context, intent);
    }

    public static void startActivity(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_slider_image_layout;
    }

    @Override
    protected void onInitLogic() {
        mImags = getIntent().getStringArrayListExtra(KEY_IMAGE_SLIDER_LIST);
        mSelectPos = getIntent().getIntExtra(KEY_IMAGE_SLIDER_INDEX, 0);
        photoViews = new PhotoView[mImags.size()];

        mViewPager = findViewById(R.id.view_pager);
        mCurIndexTV = findViewById(R.id.cur_index_tv);
        mViewPager.setOffscreenPageLimit(2);

        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImags.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                mCurIndexTV.setText((mViewPager.getCurrentItem() + 1) + "/" + mImags.size());
                PhotoView photoView = new PhotoView(ImageSliderActivity.this);
//                Glide.with(ImageSliderActivity.this).load(mImags.get(position))
//                        .into(photoView);
                photoView.setImageBitmap(BitmapFactory.decodeFile(mImags.get(position)));
                photoView.setOnClickListener(view -> {
                    finish();
                });
                container.addView(photoView, LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                photoViews[position] = photoView;
                return photoView;

            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(photoViews[position]);
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurIndexTV.setText((position + 1) + "/" + mImags.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(mSelectPos);

    }
}
