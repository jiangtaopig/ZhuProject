package com.zaaach.citypicker;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zaaach.citypicker.adapter.CityListAdapter;
import com.zaaach.citypicker.adapter.InnerListener;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.adapter.decoration.DividerItemDecoration;
import com.zaaach.citypicker.adapter.decoration.SectionItemDecoration;
import com.zaaach.citypicker.db.DBManager;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;
import com.zaaach.citypicker.util.ScreenUtil;
import com.zaaach.citypicker.view.SideIndexBar;
import com.zaaach.citypicker.view.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.zaaach.citypicker.view.TabLayout.LEFT_TAB_TYPE;

/**
 * @Author: Bro0cL
 * @Date: 2018/2/6 20:50
 */
public class CityPickerFragment extends Fragment implements TextWatcher,
        View.OnClickListener, SideIndexBar.OnIndexTouchedChangedListener, InnerListener {

    private final int DOMESTIC_CITY_TAB = 0;
    private final int INTERNATIONAL_CITY_TAB = 1;

    private View mContentView;
    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private TextView mOverlayTextView;
    private SideIndexBar mIndexBar;
    private EditText mSearchBox;
    private TextView mCancelBtn;
    private ImageView mClearAllBtn;
    private TabLayout mSelectTabLayout;
    private LinearLayoutManager mLayoutManager;
    private CityListAdapter mAdapter;
    private List<City> mAllCities = new ArrayList<>();
    private List<HotCity> mHotCities;
    private List<City> mResults;
    private List<City> mNormalCities = new ArrayList<>();

    private DBManager dbManager;
    private boolean enableAnim = false;
    private int mAnimStyle = com.zaaach.citypicker.R.style.DefaultCityPickerAnimation;
    private LocatedCity mLocatedCity;
    private int locateState;
    private OnPickListener mOnPickListener;
    private OnSelectCityTabListener mSelectCityTabListener;

    private SectionItemDecoration mSectionItemDecoration;
    private DividerItemDecoration mDividerItemDecoration;

    /**
     * 获取实例
     *
     * @param enable 是否启用动画效果
     * @return
     */
    public static CityPickerFragment newInstance(boolean enable) {
        final CityPickerFragment fragment = new CityPickerFragment();
        Bundle args = new Bundle();
        args.putBoolean("cp_enable_anim", enable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setLocatedCity(LocatedCity location) {
        mLocatedCity = location;
    }

    public void setHotCities(List<HotCity> data) {
        this.mHotCities = data;
    }

    public void setNormalCity(List<City> cities){
        mNormalCities.clear();
        mNormalCities.addAll(cities);
    }

    @SuppressLint("ResourceType")
    public void setAnimationStyle(@StyleRes int resId) {
        this.mAnimStyle = resId <= 0 ? mAnimStyle : resId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(com.zaaach.citypicker.R.layout.cp_dialog_city_picker, container, false);
        return mContentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initData();

        mSectionItemDecoration = new SectionItemDecoration(getActivity(), mAllCities);
        mDividerItemDecoration = new DividerItemDecoration(getActivity());
        mRecyclerView.addItemDecoration(mSectionItemDecoration, 0);
        mRecyclerView.addItemDecoration(mDividerItemDecoration, 1);

        mAdapter = new CityListAdapter(getActivity(), mAllCities, mHotCities, locateState);
        mAdapter.autoLocate(true);
        mAdapter.setInnerListener(this);
        mAdapter.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initViews() {
        mRecyclerView = mContentView.findViewById(com.zaaach.citypicker.R.id.cp_city_recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mEmptyView = mContentView.findViewById(com.zaaach.citypicker.R.id.cp_empty_view);
        mOverlayTextView = mContentView.findViewById(com.zaaach.citypicker.R.id.cp_overlay);

        mIndexBar = mContentView.findViewById(com.zaaach.citypicker.R.id.cp_side_index_bar);
        mIndexBar.setNavigationBarHeight(ScreenUtil.getNavigationBarHeight(getActivity()));
        mIndexBar.setOverlayTextView(mOverlayTextView)
                .setOnIndexChangedListener(this);

        mSearchBox = mContentView.findViewById(com.zaaach.citypicker.R.id.cp_search_box);
        mSearchBox.addTextChangedListener(this);

        mCancelBtn = mContentView.findViewById(com.zaaach.citypicker.R.id.cp_cancel);
        mClearAllBtn = mContentView.findViewById(com.zaaach.citypicker.R.id.cp_clear_all);
        mCancelBtn.setOnClickListener(this);
        mClearAllBtn.setOnClickListener(this);

        mSelectTabLayout = mContentView.findViewById(R.id.select_tab);

        mSelectTabLayout.setTabSelectListener(new TabLayout.OnTabSelectListener() {
            @Override
            public void onTabSelect(int type) {
                if (mSelectCityTabListener != null){
                    if (type == LEFT_TAB_TYPE){
                        type = DOMESTIC_CITY_TAB;
                    }else {
                        type = INTERNATIONAL_CITY_TAB;
                    }
                    mSelectCityTabListener.onSelectCityTab(type);
                }
            }
        });
    }

    private void initData() {
        Bundle args = getArguments();
        if (args != null) {
            enableAnim = args.getBoolean("cp_enable_anim");
        }
        //初始化热门城市
        if (mHotCities == null || mHotCities.isEmpty()) {
            mHotCities = new ArrayList<>();
            mHotCities.add(new HotCity("北京", "北京", "110100"));
            mHotCities.add(new HotCity("上海", "上海", "310100"));
            mHotCities.add(new HotCity("深圳", "广东", "440300"));
        }
        //初始化定位城市，默认为空时会自动回调定位
        if (mLocatedCity == null) {
            mLocatedCity = new LocatedCity(getString(com.zaaach.citypicker.R.string.cp_locating), "未知", "0");
            locateState = LocateState.LOCATING;
        } else {
            locateState = LocateState.SUCCESS;
        }
//        dbManager = new DBManager(getActivity());
//        mAllCities = dbManager.getAllCities();
        mAllCities.clear();
        mAllCities.addAll(mNormalCities);

        List<String> letters = getCityFirstLetter();

        mAllCities.add(0, mLocatedCity);
        mAllCities.add(1, new HotCity("热门城市", "未知", "0"));
        mResults = mAllCities;

        if (letters.size() > 0) {
            mIndexBar.setLetters(letters);
        }
    }

    public void changeTab(){
        initData();
        mRecyclerView.removeItemDecoration(mSectionItemDecoration);
        mRecyclerView.removeItemDecoration(mDividerItemDecoration);
        mSectionItemDecoration = new SectionItemDecoration(getActivity(), mAllCities);

        mRecyclerView.addItemDecoration(mSectionItemDecoration, 0);
        mRecyclerView.addItemDecoration(mDividerItemDecoration, 1);
        mAdapter.updateData(mAllCities, mHotCities, locateState);
        mRecyclerView.scrollToPosition(0);
    }

    @NonNull
    private List<String> getCityFirstLetter() {
        List<String> letters = new ArrayList<>();
        String tmp = "";
        if (mAllCities != null && mAllCities.size() > 0) {
            for (City city : mAllCities) {
                String cityPinyin = city.getPinyin();
                if (cityPinyin.length() > 1) {
                    char firstLetter = cityPinyin.charAt(0);
                    if (firstLetter >= 'a' && firstLetter <= 'z') {
                        firstLetter = Character.toUpperCase(firstLetter);
                        tmp = String.valueOf(firstLetter);
                        if (!letters.contains(tmp)) {
                            letters.add(tmp);
                        }
                    }
                }
            }
        }
        Log.e("CityPickerDialog", "mLetters size = " + letters);
        return letters;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 搜索框监听
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String keyword = s.toString();
        if (TextUtils.isEmpty(keyword)) {
            mClearAllBtn.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
            mResults = mAllCities;
            ((SectionItemDecoration) (mRecyclerView.getItemDecorationAt(0))).setData(mResults);
            mAdapter.updateData(mResults);
        } else {
            mClearAllBtn.setVisibility(View.VISIBLE);
            //开始数据库查找
            mResults = dbManager.searchCity(keyword);
            ((SectionItemDecoration) (mRecyclerView.getItemDecorationAt(0))).setData(mResults);
            if (mResults == null || mResults.isEmpty()) {
                mEmptyView.setVisibility(View.VISIBLE);
            } else {
                mEmptyView.setVisibility(View.GONE);
                mAdapter.updateData(mResults);
            }
        }
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == com.zaaach.citypicker.R.id.cp_cancel) {
            if (mOnPickListener != null) {
                mOnPickListener.onCancel();
            }
        } else if (id == com.zaaach.citypicker.R.id.cp_clear_all) {
            mSearchBox.setText("");
        }
    }


    @Override
    public void onIndexChanged(String index, int position) {
        //滚动RecyclerView到索引位置
        mAdapter.scrollToSection(index);
    }

    public void locationChanged(LocatedCity location, int state) {
        mAdapter.updateLocateState(location, state);
    }

    @Override
    public void dismiss(int position, City data) {
        if (mOnPickListener != null) {
            mOnPickListener.onPick(position, data);
        }
    }

    @Override
    public void locate() {
        if (mOnPickListener != null) {
            mOnPickListener.onLocate();
        }
    }

    public void setOnPickListener(OnPickListener listener) {
        this.mOnPickListener = listener;
    }

    public void setSelectCityTabListener(OnSelectCityTabListener listener) {
        this.mSelectCityTabListener = listener;
    }
}
