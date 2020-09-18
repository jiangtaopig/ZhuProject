package com.example.za_zhujiangtao.zhupro.kotlin

import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.za_zhujiangtao.zhupro.BaseActivity
import com.example.za_zhujiangtao.zhupro.R

/**
 *Creaeted by ${za.zhu.jiangtao}
 *on 2020/9/8
 */
@Route(path = "/kt/another")
class AnotherKotlinActivity : BaseActivity() {

    @Autowired(name = "/kt/id")
    @JvmField
    var mId: Int? = null

    override fun onInitLogic() {
        ARouter.getInstance().inject(this)
        Log.e("AnotherKotlinActivity", "mId = $mId")


        test(4){
            num1, num2 -> num1 + num2
        }
    }

    fun test(a : Int , opt : (Int, Int) -> Int) : Int{
        return a + opt.invoke(3, 6)
    }

    override fun layoutId(): Int {
        return R.layout.activity_another_kotlin_layout;
    }
}