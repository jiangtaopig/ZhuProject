package com.example.za_zhujiangtao.zhupro;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.za_zhujiangtao.zhupro.utils.KeyBoardUtils;

import butterknife.BindView;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/1/7
 */
public class KeyBoardActivity extends BaseActivity {

    @BindView(R.id.bottom_empty_layout)
    RelativeLayout mBottomEmptyLayout;

    @BindView(R.id.root_layout)
    LinearLayout mContentLayout;

    private KeyBoardUtils mKeyBoardUtils;
    private int mBottomHeight = 0;

    @Override
    protected int layoutId() {
        return R.layout.activity_keyboard_layout;
    }

    @Override
    protected void onInitLogic() {
        mKeyBoardUtils = new KeyBoardUtils(this);
        mKeyBoardUtils.onCreate();

        mBottomEmptyLayout.post(() -> {
            mBottomHeight = mBottomEmptyLayout.getHeight();
            Log.e("KeyBoardActivity", "mBottomHeight = " + mBottomHeight);
        });

        mKeyBoardUtils.setOnKeyBoardStatusChangeListener(new KeyBoardUtils.OnKeyBoardStatusChangeListener() {
            @Override
            public void OnKeyBoardPop(int keyBoardheight) {
                if (mBottomHeight > keyBoardheight) { //底部空白区域高度大于软键盘高度，没遮住
                    mBottomEmptyLayout.setVisibility(View.GONE);
                } else {
                    int offset = (int) (mBottomHeight - keyBoardheight - getResources().getDimension(R.dimen.dimen_18dp)); // 距离底部18dp
                    final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mContentLayout.getLayoutParams();
                    lp.topMargin = offset - 300;
                    mContentLayout.setLayoutParams(lp);
                }
            }

            @Override
            public void OnKeyBoardClose(int oldKeyBoardheight) {
                if (View.VISIBLE != mBottomEmptyLayout.getVisibility()) {
                    mBottomEmptyLayout.setVisibility(View.VISIBLE);
                }

                final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mContentLayout.getLayoutParams();
                if (lp.topMargin != 0) {
                    lp.topMargin = 0;
                    mContentLayout.setLayoutParams(lp);
                }
            }
        });
    }
}
