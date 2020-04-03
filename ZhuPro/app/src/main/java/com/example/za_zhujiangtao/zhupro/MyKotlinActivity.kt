package com.example.za_zhujiangtao.zhupro

import android.app.IntentService
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.kotlin_activity_layout.*

/**
 *Creaeted by ${za.zhu.jiangtao}
 *on 2020/1/2
 */

class MyKotlinActivity : BaseActivity() {

    private var Tag: String = "MyKotlinActivity";
    private lateinit var tv: TextView;
    var name: String? = null;

    override fun layoutId(): Int {
        return R.layout.kotlin_activity_layout;
    }

    override fun onInitLogic() {

        kt_txt.setOnClickListener {
            //            name = "124";
            name = "1234"
            kt_txt.text = name
            var arr = IntArray(5) { it * 1 };
            arr.forEach {
                Log.e(Tag, "v = $it");
            }
            var x = 3;
            when(x){
                1, 0 -> Log.e(Tag, "x ==0 or x == 1")
                else -> Log.e(Tag, "other")
            }

            var person : Person?;
            person = Person(23, "zju");
            Log.e(Tag,"name = ${person.name}")
            showPerson(person)
        };

        val items = listOf("a", "b");
        for (item in items) {

        }

        kt_txt_2.setOnClickListener {
            back()
            foo();

            var person = Person(20, "hwzs", true)
            Log.e(Tag, "key = ${person.customKey }, sex = ${person.sex}")

        }
    }

    fun showPerson(person: Person?){
        person?.sex = false
        kt_txt.text = "${person?.name+person?.age}"
    }

    fun back(){
        loop@ for (i in 1..5){
            for (j in 1..20){
                Log.e(Tag, "i=$i , j = $j");
                if(j == 10)
                    break@loop //直接break最外层循环
            }
        }

    }

    fun foo() {
        listOf(1, 2, 3, 4, 5).forEach {
            Log.e(Tag, "foo  $it")
            if (it == 3) return@forEach // 局部返回到该 lambda 表达式的调用者，即 forEach 循环
            print(it)
        }
        Log.e(Tag," done with implicit label")
    }

    inner class Person (var age: Int ?,var name : String ?){

        var sex :Boolean ?= true
        var customKey = name?.toUpperCase()

        constructor(age: Int?,  name: String?,  sex: Boolean? ) : this(age, name) {
            this.sex = sex;
        }

    }

}