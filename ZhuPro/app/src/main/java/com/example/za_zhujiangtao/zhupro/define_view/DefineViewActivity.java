package com.example.za_zhujiangtao.zhupro.define_view;

import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;

import butterknife.BindView;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/4/9
 */
public class DefineViewActivity extends BaseActivity {

    @BindView(R.id.txt_title)
    TextView mTitle;

    @BindView(R.id.writing_board_view)
    WritingBoardView mWritingBoardView;

    @Override
    protected int layoutId() {
        return R.layout.activity_define_view_layout_2;
    }

    @Override
    protected void onInitLogic() {
        mTitle.setOnClickListener(v -> {
            mWritingBoardView.reset();
        });
    }
}
