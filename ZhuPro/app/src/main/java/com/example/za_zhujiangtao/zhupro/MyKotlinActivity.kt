package com.example.za_zhujiangtao.zhupro

/**
 *Creaeted by ${za.zhu.jiangtao}
 *on 2020/1/2
 */

class MyKotlinActivity : BaseActivity() {




    override fun layoutId(): Int {
        return R.layout.kotlin_activity_layout;
    }

    override fun onInitLogic() {

        val items = listOf("a", "b");
        for (item in items){

        }
    }

}