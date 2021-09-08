package com.example.za_zhujiangtao.zhupro;

import android.os.Build;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import java.util.function.Function;

public class TestLayoutInflaterActivity extends BaseActivity {

    private LinearLayout rootLayout;
    private LayoutInflater inflater;

    @Override
    protected int layoutId() {
        return R.layout.activity_inflater_layout;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onInitLogic() {
        rootLayout = findViewById(R.id.root_layout);
        inflater = LayoutInflater.from(this);
//        test1(R.layout.inflater_layout, rootLayout, true);

        //false 那么不将inflater_layout 添加到 rootLayout中，要想显示只能手动添加
//        View view = test1(R.layout.inflater_layout, rootLayout, false);
//        rootLayout.addView(view);

        //如果 root 为 null, 无论 attachToRoot 是否为 true,则 R.layout.inflater_layout 的根布局设置的大小无效，
        View view = test1(R.layout.inflater_layout, null, false);
        rootLayout.addView(view);

        // :: 的用法；；
        Function<String, Integer> parseInt = Integer::parseInt;
        Integer val = parseInt.apply("123");
    }

    /**
     * @param resource     要转化的为View的xml布局
     * @param root         如果root布局传入的为null, 那么，resource 的 xml布局的根布局是无效的，例如这里的 android:layout_width="200dp"
     *                     android:layout_height="200dp" 将是无效的
     * @param attachToRoot 是否将 resource 布局添加到root 的布局
     */
    private View test1(@Nullable int resource, ViewGroup root, boolean attachToRoot) {
        return inflater.inflate(resource, root, attachToRoot);

    }


    public void showDialog(View view) {
        // NoticeDialog 中设置了 style ,此 style 继承了 Theme.Dialog，所以导致 dialog 中的ProgressBar 设置了indeterminateTint来改变颜色，此ProgressBar 不转动
//        NoticeDialog noticeDialog = NoticeDialog.getInstance("我是中国人，我爱中国");
//        noticeDialog.show(getSupportFragmentManager(), "NOTICE");

//        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
