package com.example.za_zhujiangtao.zhupro.kotlin

import android.util.Log
import android.widget.TextView
import com.example.za_zhujiangtao.zhupro.BaseActivity
import com.example.za_zhujiangtao.zhupro.R
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

            var instance = MyClass.plus(2, 3)
            MyClass.name = "xz"

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
            for (v in list) {
                Log.e(Tag, "v = $v")
            }

            //为MutableList 添加扩展函数
            fun MutableList<Int>.swap(i: Int, j: Int) {
                var size = this.size
                Log.e(Tag, " swap : size = $size")
                var tmp = this[i]
                this[i] = this[j]
                this[j] = tmp
            }

            list.swap(0, 2)

            for (v in list) {
                Log.e(Tag, "after swap : v = $v")
            }

            Connection(Host("zjt.cy"), 12345).connect();
//---------------------------------data 声明的数据类 start ------------------------------------------------------
            val user1 = User("john");
            val user2 = User("john");
            user1.age = 10
            user2.age = 20

            // user1.equals(user2) 返回 true, 因为 比较的是 主构造函数里面的 name字段；
            Log.e(Tag, "user1 toString ${user1.toString()}, user2 toString ${user2.toString()} user1 equal user2 = " + user1.equals(user2))

            //copy 函数
            val user3 = user2.copy()
            user3.age = 23
            Log.e(Tag, "user name = ${user3.name} , age = ${user3.age}")

            //数据类的解构声明
            val (name) = user3;
            Log.e(Tag, "name = $name")
//---------------------------------data 声明的数据类 end ------------------------------------------------------
// -----------------------------枚举开始--------------------
            val direction = Direction.EAST;
            Log.e(Tag, "direction = $direction")

//-----------------------------枚举结束--------------------
            val total = sum(1, 2, 3, 4, 5)
            Log.e(Tag, "total = $total")


//---------------------------------------lambda表达式演练开始------------------------------
            val add = { x: Int, y: Int -> x + y }
            var v = add(2, 3)
            Log.e(Tag, "v  = $v ")

            //如果lambda表达式是唯一的参数，函数调用的括号可以去掉
            twoAndThress { a, b -> a + b }
            twoAndThress { a, b -> a * b }

            //如果 函数的最后一个参数是函数，那么 作为相应参数传入的lambda表达式可以放在圆括号之外
            val s = multiple(5) { a, b ->
                a + b
            }
            Log.e(Tag, " s  = $s ")
//---------------------------------------lambda表达式演练结束------------------------------

            //可变集合 可以 add remove
            val numbers = mutableListOf(1, 5, 3, 6);
            numbers.add(3, 4);//在 index为3的位置插入4
//            numbers.remove(5)//删除元素5
            val nums = numbers.filter { it > 4 }
            for (v in nums) {
                Log.e(Tag, " v  = $v ")
            }

            //只读集合，不可以 add / remove
            val list2 = listOf("a", "b", "c")

            val p1 = Person2("bob", 31);
            val pList = listOf<Person2>(Person2("adal", 32), p1)
            val pList2 = listOf<Person2>(Person2("adal", 32), Person2("bob", 31))

            Log.e(Tag, "pList == pList2 ? ${pList == pList2}")

            //只读的map, 用to创建的短时存活对象 性能不佳，可以使用 apply来替代
            val numberMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3)
            Log.e(Tag, "key = ${numberMap.keys}, value = ${numberMap.values}")

            val mutableMap = mutableMapOf("a" to "zjt", "b" to "zcy")
            mutableMap.put("c", "wq")
            for (value in mutableMap.values) {
                Log.e(Tag, "val = $value")
            }

            val stringMap = mutableMapOf<String, String>().apply { this["one"] = "1"; this["two"] = "2" }
            Log.e(Tag, "stringMap val = ${stringMap.values}")

            //空集合
            val map = emptyMap<String, String>()
            val mm = mutableMapOf<String, String>()

            val sourceList = mutableListOf<Person2>(Person2("zjt", 32), Person2("wq", 32), Person2("zcy", 3));
            val copyList = sourceList.toMutableList();
            sourceList.get(0).apply {
                this.name = "xxx"
                this.age = 23
            }

            //把sourceList的所有age改为10
            sourceList.map {
                it.age = 10;
            }

            copyList.add(Person2("mm", 55))
            for (p in sourceList) {
                Log.e(Tag, "source list name = ${p.name}, age = ${p.age}")
            }
            for (p in copyList) {
                Log.e(Tag, "..... copy list name = ${p.name}, age = ${p.age}")
            }















        }
    }

    //----------------------------------------lambda 表达式 start------------------------------------------------------------------

    /**
     * lambda作为函数参数
     */
    fun twoAndThress(operation: (Int, Int) -> Int) {
        val result = operation(2, 3);
        Log.e(Tag, "twoAndThress  result= $result ")
    }

    fun multiple(x: Int, operation: (Int, Int) -> Int): Int {
        val res = operation(3, 4);
        return x * res;
    }
    //----------------------------------------lambda 表达式 start------------------------------------------------------------------

    /**
     * kotlin 的可变参数
     */
    fun sum(vararg ts: Int): Int {
        var sum: Int = 0;
        for (t in ts) {
            sum += t;
        }
        return sum;
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

    //使用 inner 声明的 内部类才能够范文外部类的成员。内部类会持有内部类的一个引用
    inner class Filler {
        fun fill() {
            Log.e("MyKotlinActivity", "inner Filler fill...")
        }

        fun drawAndFill() {
            super@FilledRectangle.draw() //访问外部类的超类 Rectangle 的 draw 方法
            draw()
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
class Host(val hostName: String) {
    fun printHost() {
        Log.e("MyKotlinActivity", "hostName = $hostName")
    }

    override fun toString(): String {
        return "Host hostName = $hostName"
    }
}

class Connection(val host: Host, val port: Int) {
    private fun printPort() {
        Log.e("MyKotlinActivity", "port = $port")
    }

    private fun Host.printConnectionString() { //声明Host类的方法
        printHost()
        Log.e("MyKotlinActivity", "............")
        printPort()
        Log.e("MyKotlinActivity", "Host toString : " + toString())//调用的是Host的toString, 因为这里扩展的是 Host的方法
        Log.e("MyKotlinActivity", "Connection toString : " + this@Connection.toString()) // 调用的是 Connection 的toString
    }

    fun connect() {
        host.printConnectionString()
    }

    override fun toString(): String {
        return "Connection host = ${host.hostName}, prot = $port"
    }
}

// data 声明的数据类
data class User(var name: String) {
    var age: Int = 11;
}

/**
 * 枚举类
 */
enum class Direction {
    EAST, SOUTH, WEST, NORTH;
}

class Person2(var name: String?, var age: Int?) {

}