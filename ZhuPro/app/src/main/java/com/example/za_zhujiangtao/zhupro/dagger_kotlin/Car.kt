package com.example.za_zhujiangtao.zhupro.dagger_kotlin

import android.util.Log
import javax.inject.Inject

/**
 *Creaeted by ${za.zhu.jiangtao}
 *on 2020/5/29
 */
class Car @Inject constructor() {

    public fun showCarName(){
        Log.e("Car", "this is bmw car")
    }
}