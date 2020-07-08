package com.example.za_zhujiangtao.zhupro.city_select;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Button;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;

import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/7/3
 */
public class TestFragmentActivity extends BaseActivity {

    private HomeFragment homeFragment;
    private FragmentManager fragmentManager;

    private Button button;

    @Override
    protected int layoutId() {
        return R.layout.activity_test_fragment_layout;
    }

    @Override
    protected void onInitLogic() {
        fragmentManager = getSupportFragmentManager();
        homeFragment = HomeFragment.getInstance();
        button = findViewById(R.id.add_btn);
        button.setOnClickListener(v -> {
            showFragment(new FriendFragment());
        });
        showFragment(homeFragment);
    }

    private void showFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_content, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void back(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> fragmentList = fragmentManager.getFragments();
        fragmentTransaction.remove(fragmentList.get(fragmentList.size() -1));
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            back();
        }
        return true; // 表示消费了此次事件，不再交由系统执行 退出Activity的操作
    }
}
