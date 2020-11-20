package com.example.za_zhujiangtao.zhupro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/11/17
 */
public class LayoutInflaterActivity extends BaseActivity {
    @Override
    protected int layoutId() {
        return R.layout.activity_layout_inflater_layout;
    }

    @Override
    protected void onInitLogic() {
        ViewGroup rootLayout = findViewById(R.id.root_layout);
        LayoutInflater inflater = LayoutInflater.from(this);

        // 如果 root == null，那么 resource 最外层布局，比方说这里的 inflater_layout_2中的最外层布局 LinearLayout 的大小虽然设置了300dp，
        // 但是都是没有效果的，因为不会给 下面的 view 设置 root 的 LayoutParams;

        // attachToRoot true 表示将 view 添加到 root中，否则不添加进root
        View view = inflater.inflate(R.layout.inflater_layout_2, null, false);
        // 然后 addView 的时候 发现 params 为空，则会给view设置默认的params，
        // 比方说 如果是RelativeLayout ,则  LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // 如果是 LinearLayout 且 orientation = 'vertiacal'，则 LayoutParams(LayoutParams.MATCH_PARENT
        // , LayoutParams.WRAP_CONTENT);
        rootLayout.addView(view);

    }
}
