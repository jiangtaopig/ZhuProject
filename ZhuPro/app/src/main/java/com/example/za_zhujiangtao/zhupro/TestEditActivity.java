package com.example.za_zhujiangtao.zhupro;

import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/10/24
 */
public class TestEditActivity extends BaseActivity {

    @BindView(R.id.btn_glide)
    Button mGlide;

    @BindView(R.id.img_poster)
    ImageView mPoster;

    @Override
    protected int layoutId() {
        return R.layout.test_chat_input_layout;
    }

    @Override
    protected void onInitLogic() {
        mGlide.setOnClickListener(v -> { //

            RequestOptions options = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC);
            Glide.with(this)
                    .applyDefaultRequestOptions(options)
                    .load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1432259458,2123192210&fm=26&gp=0.jpg")
                    .into(mPoster);
        });
    }
}
