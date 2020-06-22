package com.example.za_zhujiangtao.zhupro.dagger_kotlin

import dagger.Component

/**
 *Creaeted by ${za.zhu.jiangtao}
 *on 2020/5/29
 */
@Component
interface MyDaggerActivityComponent {
    fun inject(activity: MyDaggerActivity)
}