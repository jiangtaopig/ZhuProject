package com.example.za_zhujiangtao.zhupro.arouter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.za_zhujiangtao.zhupro.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/com/urlActivity")
public class ARouterUrlActivity extends AppCompatActivity {

    @BindView(R.id.text_tv)
    TextView mTv;

    @Autowired
    String name;

    @Autowired
    int age;

    @Autowired
    boolean boy;

    @Autowired
    int high;

    @Autowired
    String obj ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter_url_layout);
        ButterKnife.bind(this);

        ARouter.getInstance().inject(this);

        mTv.setText("参数是： " + "name: " + name + "  age: " + age
                + " boy: " + boy + " obj: " + obj );
    }
}
