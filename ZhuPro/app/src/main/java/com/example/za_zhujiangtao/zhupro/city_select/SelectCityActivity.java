package com.example.za_zhujiangtao.zhupro.city_select;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;
import com.zaaach.citypicker.CityPickerFragment;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/11/13
 */
public class SelectCityActivity extends BaseActivity {

    private FragmentManager fragmentManager;
    private CityPickerFragment domesticFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.DefaultCityPickerTheme);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_city_select_layout;
    }

    @Override
    protected void onInitLogic() {
        fragmentManager = getSupportFragmentManager();
        domesticFragment = CityPickerFragment.newInstance(false);
        domesticFragment.setLocatedCity(new LocatedCity("上海", "上海", "101020100"));
        List<HotCity> hotCityList = new ArrayList<>();
        hotCityList.add(new HotCity("北京", "北京", "101010100"));
        hotCityList.add(new HotCity("上海", "上海", "101020100"));
        domesticFragment.setHotCities(hotCityList);

        domesticFragment.setSelectCityTabListener(type -> {
            if (type == 0){
                domesticFragment.setLocatedCity(new LocatedCity("上海", "上海", "101020100"));
                List<HotCity> hotCities = new ArrayList<>();
                hotCities.add(new HotCity("北京", "北京", "101010100"));
                hotCities.add(new HotCity("上海", "上海", "101020100"));
                domesticFragment.setHotCities(hotCities);
                domesticFragment.changeTab();
            }else {
                domesticFragment.setLocatedCity(new LocatedCity("深圳", "广东", "101280601"));
                domesticFragment.setHotCities(null);
                domesticFragment.changeTab();
            }
            toFragment(domesticFragment);
        });

        toFragment(domesticFragment);

    }


    private void toFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

}
