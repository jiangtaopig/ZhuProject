package com.example.za_zhujiangtao.zhupro

import android.app.IntentService
import android.nfc.Tag
import android.util.Log
import android.widget.TextView
import com.example.za_zhujiangtao.zhupro.kotlin.KotlinUtils
import kotlinx.android.synthetic.main.kotlin_activity_layout.*

/**
 *Creaeted by ${za.zhu.jiangtao}
 *on 2020/1/2
 */

class MyKotlinActivity : BaseActivity() {

    public var Tag: String = "MyKotlinActivity";
    private lateinit var tv: TextView;
    var name: String? = null;

    override fun layoutId(): Int {
        return R.layout.kotlin_activity_layout;
    }

    fun plus(a: Int, b: Int): Int {
        return a + b;
    }

    override fun onInitLogic() {

        kt_txt.setOnClickListener {
            //            name = "124";
            var nameLength = name?.length
            name = "1234"
            kt_txt.text = name

            var arr = IntArray(5) { it * 1 };
            arr.forEach {
                Log.e(Tag, "v = $it");
            }
            var x = 3;
            when (x) {
                1, 0 -> Log.e(Tag, "x ==0 or x == 1")
                else -> Log.e(Tag, "other")
            }

            var person: Person?;
            person = Person(23, "zju");
            Log.e(Tag, "name = ${person.name}")
            showPerson(person)
        };

        val items = listOf("a", "b");
        for (item in items) {

        }

        kt_txt_2.setOnClickListener {
            back()
            foo();

            var person = Person(20, "hwzs", true)
            Log.e(Tag, "key = ${person.customKey}, sex = ${person.sex}")

            var filledRectangle = FilledRectangle();
            filledRectangle.draw()
            var fillColor = filledRectangle.fillColor
            filledRectangle.size = 30

            Log.e(Tag, "fillColor = $fillColor , size = ${filledRectangle.size}")

            var filler = FilledRectangle().Filler();
            filler.drawAndFill()

            var instance = MyClass.Factory.plus(2, 3)
            MyClass.Factory.name = "xz"

            var plus = MyClass.plus(3, 4);
            Log.e(Tag, "plus = $plus")

            //扩展伴生对象的方法
            fun MyClass.Factory.diff(a: Int, b: Int): Int {
                return a - b;
            }

            var diff = MyClass.diff(8, 2)
            Log.e(Tag, "diff = $diff")

            var sum = KotlinUtils.sum(3, 5);

//            var kotinUtils = KotlinUtils(); //KotlinUtils 的构造函数 是private 所以不能初始化

            val list = mutableListOf(1, 2, 3)
            for (v in list){
                Log.e(Tag, "v = $v")
            }

            //为MutableList 添加扩展函数
            fun MutableList<Int>.swap(i: Int, j: Int){
                var size = this.size
                Log.e(Tag, " swap : size = $size")
                var tmp = this[i]
                this[i] = this[j]
                this[j] = tmp
            }

            list.swap(0, 2)

            for (v in list){
                Log.e(Tag, "after swap : v = $v")
            }

            Connection(Host("zjt.cy"), 12345).connect();

            val user1 = User("john");
            val user2 = User("john");
            user1.age = 10
            user2.age = 20

            Log.e(Tag, "user1 toString ${user1.toString()}, user2 toString ${user2.toString()} user1 equal user2 = ")


        }
    }

    fun showPerson(person: Person?) {
        person?.sex = false
        kt_txt.text = "${person?.name + person?.age}"
    }

    fun back() {
        loop@ for (i in 1..5) {
            for (j in 1..20) {
                Log.e(Tag, "i=$i , j = $j");
                if (j == 10)
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
        Log.e(Tag, " done with implicit label")
    }

    inner class Person(var age: Int?, var name: String?) {

        var sex: Boolean? = true
        var customKey = name?.toUpperCase()

        constructor(age: Int?, name: String?, sex: Boolean?) : this(age, name) {
            this.sex = sex;
        }

    }

}


open class Rectangle {
    open fun draw() {
        Log.e("MyKotlinActivity", "Drawing a rectangle")
    }

    val borderColor: String get() = "black";

    open var size: Int? = 10
}

interface Polygon {
    fun draw() {
        Log.e("MyKotlinActivity", "Drawing a polygon")
    }
}

class FilledRectangle : Rectangle() {
    override fun draw() {
        super.draw()
        Log.e("MyKotlinActivity", "filling the rectangle")
    }

    val fillColor: String get() = super.borderColor;

    override var size: Int?
        get() = super.size
        set(value) {
            Log.e("MyKotlinActivity", "size set value = $value")
        }

    inner class Filler {
        fun fill() {
            Log.e("MyKotlinActivity", "inner Filler fill...")
        }

        fun drawAndFill() {
            super@FilledRectangle.draw() //访问外部类的超类 Rectangle 的 draw 方法
            fill()
            Log.e("MyKotlinActivity", "draw a filled rectangle with color ${super@FilledRectangle.borderColor}")
        }
    }
}

class RectangleAndPloygon : Rectangle(), Polygon {

    override fun draw() {
        super<Rectangle>.draw()//调用Rectangle的draw
        super<Polygon>.draw()//调用Polygon的draw方法
    }
}

//.........................伴生对象.............................目的就是为了实现类似于Java中静态属性和静态方法
class MyClass {

    companion object Factory {
        @JvmStatic
        fun plus(a: Int, b: Int): Int {
            return a + b
        } //JvmStatic 用在方法上， 这样外面就可以像 Java 中调用静态属性 和静态方法那样调用

        @JvmField
        var name: String? = "zcy" //JvmField 用在属性上
    }
}

//........................在一个内的内部为另一个类声明扩展..................................
class Host(val hostName : String){
    fun printHost(){
        Log.e("MyKotlinActivity", "hostName = $hostName")
    }

    override fun toString(): String {
        return "Host hostName = $hostName"
    }
}

class Connection(val host: Host, val port: Int){
    fun printPort(){
        Log.e("MyKotlinActivity", "port = $port")
    }

    fun Host.printConnectionString(){ //声明Host类的方法
        printHost()
        Log.e("MyKotlinActivity", "............")
        printPort()
        Log.e("MyKotlinActivity", "Host toString : "+toString())//调用的是Host的toString, 因为这里扩展的是 Host的方法
        Log.e("MyKotlinActivity", "Connection toString : "+this@Connection.toString()) // 调用的是 Connection 的toString
    }

    fun connect(){
        host.printConnectionString()
    }

    override fun toString(): String {
        return "Connection host = ${host.hostName}, prot = $port"
    }
}

data class User(var name: String){
    var age: Int = 11;
}