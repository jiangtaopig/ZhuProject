package com.example.za_zhujiangtao.zhupro.kotlin

import android.util.Log

/**
 *Creaeted by ${za.zhu.jiangtao}
 *on 2020/8/26
 */
open class UserData (val name : String, val age : Int) {

    open fun show(){
        Log.e("UserData", "name = $name , age = $age")
    }
}