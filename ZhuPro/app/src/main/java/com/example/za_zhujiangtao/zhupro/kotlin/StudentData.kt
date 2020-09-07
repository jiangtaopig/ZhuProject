package com.example.za_zhujiangtao.zhupro.kotlin

import android.util.Log

/**
 *Creaeted by ${za.zhu.jiangtao}
 *on 2020/8/27
 */
class StudentData(name: String, age: Int, val clazz: String) : UserData(name, age) {

    override fun show() {
        super.show()
        Log.e("StudentData", "clazz = "+clazz)
    }
}