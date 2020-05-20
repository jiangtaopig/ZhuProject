package com.example.za_zhujiangtao.zhupro;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by za-zhujiangtao on 2018/12/13.
 */

public class EditPosterActivity extends AppCompatActivity {

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    private EditPosterAdapter mEditPosterAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_poster_layout);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        mEditPosterAdapter = new EditPosterAdapter(this, mRecyclerView);
        mRecyclerView.setAdapter(mEditPosterAdapter);

        final Widget WHITE_WIDGET =
                Widget.newLightBuilder(this)
                        .title("选择图片")
                        .statusBarColor(Color.WHITE)
                        .toolBarColor(Color.WHITE)
                        .build();

        mEditPosterAdapter.setOnAddImageListener(new EditPosterAdapter.OnAddImageListener() {
            @Override
            public void onAddImg() {
                Album.image(EditPosterActivity.this)
                        .multipleChoice()
                        .requestCode(200)
                        .camera(true)
                        .columnCount(3)
                        .selectCount(1)
                        .widget(WHITE_WIDGET)
                        .onResult(new Action<ArrayList<AlbumFile>>() {
                            @Override
                            public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                                if (result != null) {
                                    String path = result.get(0).getPath();
                                    Log.e("EditPosterAdapter", "path = " + path);
                                    if (!TextUtils.isEmpty(path)){
                                        mEditPosterAdapter.addData(path);
                                    }
                                }
                            }
                        })
                        .start();
            }
        });
    }
}
