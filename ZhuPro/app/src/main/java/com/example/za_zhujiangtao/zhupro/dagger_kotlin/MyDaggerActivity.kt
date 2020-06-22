package com.example.za_zhujiangtao.zhupro.dagger_kotlin

import com.example.za_zhujiangtao.zhupro.BaseActivity
import com.example.za_zhujiangtao.zhupro.R
import kotlinx.android.synthetic.main.activity_test_dagger_layout.*
import javax.inject.Inject

/**
 *Creaeted by ${za.zhu.jiangtao}
 *on 2020/5/29
 */
class MyDaggerActivity : BaseActivity() {

    @Inject
    lateinit var mCar :Car

    override fun onInitLogic() {
        txt_title.setOnClickListener {

        }
    }

    override fun layoutId(): Int {
        return R.layout.activity_test_dagger_layout
    }
}