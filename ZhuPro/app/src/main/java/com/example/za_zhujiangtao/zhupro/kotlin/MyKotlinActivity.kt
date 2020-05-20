package com.example.za_zhujiangtao.zhupro.kotlin

import android.os.Build
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.za_zhujiangtao.zhupro.BaseActivity
import com.example.za_zhujiangtao.zhupro.R
import kotlinx.android.synthetic.main.kotlin_activity_layout.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.lang.IllegalArgumentException
import kotlin.system.measureTimeMillis

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
            Log.e(Tag, "------------------------------- lambda ---------------------------------------")
            val add = { x: Int, y: Int -> x + y }
            var v = add.invoke(2, 3) // 或者 add(2, 3)
            Log.e(Tag, "v  = $v ")


            twoAndThress({ a, b -> a + b })
            //如果lambda表达式是唯一的参数，函数调用的括号可以去掉,函数的最后一个参数是函数，那么 作为相应参数传入的lambda表达式可以放在圆括号之外
            twoAndThress { a, b -> a * b }

            //如果 函数的最后一个参数是函数，那么 作为相应参数传入的lambda表达式可以放在圆括号之外
            val s = multiple(5) { a, b ->
                a + b
            }
            Log.e(Tag, " s  = $s ")

            a{
                a , b -> a+b
            }

            // 对于一个声明好的函数，不管是你要把它作为参数传递给函数，还是要把它赋值给变量，都得在函数名的左边加上双冒号(::)才行
            // 函数名左边 加上双冒号后，这个就变成了一个和这个函数具有相同功能的对象，----是对象了，因为 kotlin中只有对象才能作为参数传递和赋值给变量
            val r1 = a(::ff)
            val plusFun = :: ff
            val r2 = plusFun(2, 6)

            Log.e(Tag, "r1 = $r1, r2 = $r2")
            // 单参数的时候只要不用这个参数就可以直接不写了,其实就算用到，也可以不写，因为 Kotlin 的 Lambda 对于省略的唯一参数有默认的名字：it：
            int2String {
                it.toString()
            }


           // 把一个匿名函数赋值给变量而不是作为函数参数传递的时候
            val b = fun(params: Int) :String{
               return params.toString()
            }

            // 如果简写成lambda表达式, 那么就不可以省略 参数类型 Int 了，因为省略了Int 就无法推断 params 类型了
            val c = {params : Int ->
                params.toString()
            }

            // 如果你出于场景的需求或者个人偏好，就是想在这里省掉参数类型，那你需要给左边的变量指明类型：
            val d : (params : Int) -> String = {
                it.toString() //另外 Lambda 的返回值不是用 return 来返回，而是直接取最后一行代码的值
            }

            d.invoke(5)


            Log.e(Tag, "------------------------------- lambda ---------------------------------------")


//---------------------------------------lambda表达式演练结束------------------------------

            testCollection()

// ------------------------------强制类型转换-------------------------------------------------------------
            Log.e(Tag, "----------------------------  测试强制类型转换  ----------------------------------------")
            testTransform()
            val user4 :User2 = User2(12, "xxx")
            val maneger = Manager("zhujiangtao", 23)
            Log.e(Tag, "manager name's size = ${maneger.size}, name = ${maneger.name}")

        }
    }

    private fun testTransform() {
        val myBase: MyBase = MyDetail(23)
        myBase.name = "zjt"
        // as 强制类型转换，但是可能会强制转换为一个错误的类型， 所以 用 as?来解决，as? 转换后是一个可空类型，所以 后面调用 doSth() 要加 ?
        (myBase as? MyDetail)?.doSth()

        // 还可以使用类型判断
        if (myBase is MyDetail){
            myBase.doSth()
        }

        val txt = """hi ${myBase.name} my name is xxx\n """ // """ 不会转义， \n 会直接输出
        Log.e(Tag, "txt = $txt")

        val ll = listOf(21, 40, 11, 33, 78)
       val filterList: List<Int> = ll.filter {
            it % 3 ==0
        }

        for (v in filterList){
            Log.e(Tag, "v = $v")
        }
    }

    private fun testCollection() {
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

        //------------------------------集合的迭代器------------------------------------

        val number = listOf("one", "two", "three", "four")
        val numIterator = number.iterator()
        while (numIterator.hasNext()) {
            Log.e(Tag, ".... val = ${numIterator.next()} ....")
        }

        for (num in number) {
            Log.e(Tag, "--- val = ${num} ---")
        }

        number.forEach {
            Log.e(Tag, "xxx val = $it xxx")
        }

        //双向迭代器 ListIterator
        val listIterator = number.listIterator();
        //先把迭代器指向list的尾部
        while (listIterator.hasNext())
            listIterator.next()
        while (listIterator.hasPrevious()) {
            Log.e(Tag, "index = ${listIterator.previousIndex()} , value = ${listIterator.previous()}")
        }

        //可变迭代器
        val strings = mutableListOf("china", "america", "england")
        val mutableIterator = strings.iterator()

        mutableIterator.next()
        mutableIterator.remove()
        Log.e(Tag, "after remove $strings")

        // mutableListIterator 还可以在迭代列表时插入和替换元素
        val numString = mutableListOf("one", "four", "four")
        val mutableListIterator = numString.listIterator()
        mutableListIterator.next()
        mutableListIterator.add("two")
        mutableListIterator.next()
        mutableListIterator.set("three")
        Log.e(Tag, "after add and set $numString")

        //--------------------------------------集合区间--------------------------------------
        for (i in 1..4) {
            Log.e(Tag, "i = $i")
        }

        //反向迭代
        for (i in 4 downTo 2) {
            Log.e(Tag, "反向迭代 i = $i")
        }

        //设置步长
        for (i in 1..8 step 3) {
            Log.e(Tag, "设置步长 i = $i")
        }

        for (i in 8 downTo 1 step 2) {
            Log.e(Tag, "反向加设置步长 i = $i")
        }

        //设置迭代不包含其结束元素的数字区间
        for (i in 1 until 5) {//[1, 5) 不包含5
            Log.e(Tag, "设置不包含结束元素 i = $i")
        }

        //-------------------------------集合操作------------------------------------
        //结合的操作会返回操作后的结果，而不会影响原始集合
        val myNums = listOf("one", "two", "three", "four")
        val nums2 = myNums.filter { it.length > 3 }

        Log.e(Tag, "-----------------------------------------------------------------------------------------------")
        Log.e(Tag, "myNums = $myNums, nums2 = $nums2")

        //对于某些集合操作，可以指定目标对象
        val filterRes = mutableListOf<String>()
        myNums.filterTo(filterRes) {
            it.length > 3
        }
        Log.e(Tag, "filterRes = $filterRes")
        myNums.filterIndexedTo(filterRes) { index, s ->
            index == 0 //index = 0的加入到filterRes中去
        }
        Log.e(Tag, "filterRes = $filterRes")


// ------------------------------------------------集合转换---------------------------------------------------------------
        val sets = setOf(1, 2, 3)
        val res = sets.map { it * 3 }
        Log.e(Tag, "sets = $sets, res = $res")
        val res2 = sets.mapIndexed { index, value ->
            index * value
        }
        Log.e(Tag, "res2 = $res2")

        val myMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3)
        val mapRes = myMap.mapKeys { it.key.toUpperCase() }
        val mapRes2 = myMap.mapValues { it.value + it.key.length }
        Log.e(Tag, "myMap = $myMap mapRes = $mapRes , mapRes2 = $mapRes2")

        // zip 操作

        val colors = listOf("red", "grey")
        val animals = listOf("fox", "wolf")

        // 如果集合的大小不同，则 zip() 的结果为较小集合的大小；结果中不包含较大集合的后续元素。 zip() 也可以中缀形式调用 a zip b
        val ss: List<Pair<String, String>> = colors.zip(animals) // zip() 返回 Pair 对象的列表
        val p: Pair<String, String> = ss.get(0)
        Log.e(Tag, "color = ${p.first} animal = ${p.second} , ss = $ss")

        // unzip 进行反向逆转 把 Pari 列表 List<Pair<>> 转成 Pair<>
        val unzip: Pair<List<String>, List<String>> = ss.unzip()
        Log.e(Tag, "color = ${unzip.first} animal = ${unzip.second}")

        val list3 = listOf("one" to 1, "two" to 2)
        val res3: Pair<List<String>, List<Int>> = list3.unzip()
        Log.e(Tag, "first = ${res3.first} second = ${res3.second} , list3 = $list3")

        // associateWith 操作， 允许从集合元素和与其关联的某些值构建 Map
        // 例1
        val list4 = listOf("one", "two", "three", "four", "four") // 原始集合元素是键
        // 如果两个元素相等，则仅最后一个保留在 Map 中, 原始集合中包含2个相同的值 "four"，结果只会保留一个
        val map3: Map<String, Int> = list4.associateWith { it.length } // 集合中每个元素的长度作为 value
        Log.e(Tag, "associateWith map3 = $map3 ")

        // associateBy 使用原始集合 作为值来构建 Map ,它需要一个函数，该函数的返回值作为 Map 的键;
        // 如果2个元素相等 则只保留一个到 Map , it.first() 表示 元素的首字母，"two" 和 "three" 的首字母相等，所以只保留 "three"，同理只保留后面的"four"
        val map4 = list4.associateBy { it.first().toUpperCase() }
        Log.e(Tag, "associateBy map4 = $map4 ") // 结果  associateBy map4 = {O=one, T=three, F=four}

        // flatten 操作 将集合中嵌套集合 打平成一个集合
        val list5 = listOf(listOf(1, 2, 3), listOf(4, 5), listOf(1, 2))
        val list6 = list5.flatten()
        Log.e(Tag, "flatten list5 = $list5 , list6 = $list6 ")

        // flatMap 作用是通过一个函数将嵌套集合映射到另一个集合 作用相当于 map 和 flatten

        val containers = listOf(listOf("one", "two", "three"), listOf("four", "five"))
        val list7 = containers.flatMap { it -> it.reversed() } // 反转 containers 中的每个元素(集合)
        Log.e(Tag, "list7 = $list7") //  [three, two, one, five, four]

// ----------------------------------------------集合的过滤----------------------------------------------------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            filterCollection()
        }
// ----------------------------------------------协程测试----------------------------------------------------------------------------
        testCoroutines()
    }

    /**
     *  集合过滤操作
     */
    @RequiresApi(Build.VERSION_CODES.N)
    private fun filterCollection() {

        // filter 操作
        val list = listOf("one", "two", "three", "four", "five", "six")
        val list2 = list.filter { it.length > 3 }
        val list3 = list.filterIndexed { index, s -> (index != 0 && s.length < 5) }
        Log.e(Tag, "list1 = $list , list2 = $list2 , list3 = $list3")

        val map = mapOf("key1" to 1, "key2" to 2, "key11" to 11)
        val map2 = map.filter { (key, value) ->
            key.endsWith("1") && value > 10
        }
        Log.e(Tag, "map = $map map2 = $map2")


        // partition 操作
        val (match, rest) = list.partition { it.length > 3 }
        Log.e(Tag, "match = $match  , rest = $rest")

        // groupBy 分组
        val list4 = list.groupBy { it.first() }
        Log.e(Tag, "list4 = $list4") //  {o=[one], t=[two, three], f=[four]}

        // slice 去集合的一部分
        val list5 = list.slice(1..3); //  [two, three, four]
        val list6 = list.slice(0..4 step 2) // list6 = [one, three, five]
        Log.e(Tag, "list5 = $list5 , list6 = $list6")

        // take 。。  take 从头获取指定数量的元素， takeLast 从尾部开始获取指定数量的元素， 当指定的数量大于集合的大小时，返回整个集合
        val list7 = list.take(3);
        val list8 = list.takeLast(3)
        Log.e(Tag, "list7 = $list7 , list8 = $list8")

        // 要从头或从尾部去掉指定数量的元素 调用 drop 和 dropLast

        // takeWhile 是带有谓词的 take()：它将不停获取元素直到排除与谓词匹配的首个元素。如果首个集合元素与谓词匹配，则结果为空。


        //取单个元素 elementAt(int index) first last
        val element = list.elementAt(2)
        Log.e(Tag, "element = $element , first = ${list.first()} , last = ${list.last()}")

        val e2 = list.first { it.length > 3 }
        Log.e(Tag, "e2 = $e2")

        val e3 = list.find { it.length > 3 } // 找第一个满足条件的元素
        Log.e(Tag, "e3 = $e3")

//----------------------------------顺序和倒序----------------------------------------------
        // 自然顺序
        val numbers = listOf("one", "two", "three", "four", "five")
        val sort1 = numbers.sorted() // 升序
        val sort2 = numbers.sortedDescending() // 降序
        Log.e(Tag, "source = $numbers , sort1 = $sort1 , sort2 = $sort2")

        // 自定义顺序
        val sort3 = numbers.sortedBy { it.length } // 根据字符长度排序
        val sort4 = numbers.sortedByDescending { it.first() } // 第一个字符降序排列

        Log.e(Tag, "sort3 = $sort3 , sort4 = $sort4")

        // 倒序

        val sort5 = numbers.reversed()
        val sort6 = numbers.asReversed()
        Log.e(Tag, "sort5 = $sort5, sort6 = $sort6")

// ---------------------------------set 相关操作-------------------------------------
        val num2 = setOf("one", "two", "three")
        val unionResult = num2 union setOf("four", "five", "six") // 并集
        val same = num2 intersect setOf("two", "one", "five") // 交集
        val diff = num2 subtract setOf("two", "five") // 差集 另一个集合中不存在的元素

        Log.e(Tag, "num2 = $num2 , unionResult = $unionResult , same = $same , diff = $diff")

// --------------------------------- map 相关操作----------------------------------------

        Log.e(Tag, "------------------------------------ map 的 相关操作  ------------------------------------------------")
        val mapNums = mutableMapOf("one" to 1)
        mapNums.put("two", 2)
        mapNums.put("three", 3)

        val one = mapNums.get("one")
        val two = mapNums["two"]
        val four = mapNums.getOrDefault("four", -1)
        val five = mapNums["five"]
        Log.e(Tag, "one = $one , two = $two , four = $four , five = $five")

        val keys: MutableSet<String> = mapNums.keys
        var value: Int? = 0
        for (key in keys) {
            value = mapNums.get(key)
            Log.e(Tag, "key = $key , value = $value")
        }

        val mapValue = mapNums.values
        Log.e(Tag, "mapValue = $mapValue")

        val newMap = mapNums + Pair("four", 4) // plus 操作
        val newMap2 = newMap - "one" // minus 操作

        Log.e(Tag, "newMap = $newMap , newMap2 = $newMap2")


    }


    /**
     * 协程 test case
     */
    private fun testCoroutines() {
        Log.e(Tag, "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  测试协程  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
        GlobalScope.launch { // 在后台启动一个新的协程并继续
            delay(1000L) // 非阻塞等待1s
            Log.e(Tag, "World! threadName = ${Thread.currentThread().name}") // 新的协程
        }
        Log.e(Tag, "Hello threadName = ${Thread.currentThread().name}") // main线程
//        Thread.sleep(2000)
        runBlocking { // 阻塞主线程
            delay(2000L)
        }
        Log.e(Tag, " ${Thread.currentThread().name} 执行完毕")
        Log.e(Tag, "--------------------------join------------------")
//        testJoin()
//        testScope()
//        world()
//        cancelJob()
//        cancelFailed()
//        testAsyc()
//        testLazyAsyc()
//
//        testAsycFun()
//        testAsync2()
//
//        testDispatcher()
//        testFatherCoroutines()

        testFlow()
    }

    /**
     * 使用 runBlocking 将 testJoin 函数转换为协程
     */
    private fun testJoin() = runBlocking {
        // 可以使用 join 等待协程执行结束后再执行main线程的后续操作，实例如下：
        val job = GlobalScope.launch { // 在后台启动一个新的协程并继续
            delay(1000L) // 非阻塞等待1s
            Log.e(Tag, "threadName = ${Thread.currentThread().name}") // 新的协程
        }
        Log.e(Tag, "threadName = ${Thread.currentThread().name}") // main线程

        job.join() // 类型于Thread.join()方法
        Log.e(Tag, "${Thread.currentThread().name} 执行后续操作") // main线程

        // 在 runBlocking 的作用域中启动一个新的协程
        launch {
            Log.e(Tag, "我是在runBlocking 作用域中启动的 新协程 threadName = ${Thread.currentThread().name}")
        }
    }

    private fun testScope() = runBlocking {
        launch {
            delay(200L)
            Log.e(Tag, "Task from runBlocking threadName = ${Thread.currentThread().name}")
        }

        coroutineScope {// 创建一个协程作用域
            launch {
                delay(500L)
                Log.e(Tag, "Task from nest threadName = ${Thread.currentThread().name}")
            }

            delay(100L)
            Log.e(Tag, "Task from coroutineScope threadName = ${Thread.currentThread().name}")
        }

        Log.e(Tag, " coroutineScope is over threadName = ${Thread.currentThread().name}")
    }

    // 我们来将 launch { …… } 内部的代码块提取到独立的函数中。当你对这段代码执行“提取函数”重构时，你会得到一个带有 suspend 修饰符的新函数。 那是你的第一个挂起函数。

    private fun world() = runBlocking {
        launch {
            doWorld()
        }
        Log.e(Tag, "Hello ")
    }

    private suspend fun doWorld() {
        delay(2000L)
        Log.e(Tag, "World!")
    }

    private fun cancelJob() = runBlocking {
        val job = launch {
            repeat(1000) { i ->
                Log.e(Tag, "i am repeat $i times threadName = ${Thread.currentThread().name}")
                delay(500L)
            }
        }

        delay(1600L)
        Log.e(Tag, "i am tired of wating")
        job.cancel() // 取消作业
        job.join() // 等待作业执行结束
        Log.e(Tag, "now i can quit!")
    }

    /**
     * 如果协程正在执行计算任务，并且没有检查取消的话，那么它是不能被取消的
     */
    private fun cancelFailed() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 10) { // 一个执行计算的循环，只是为了占用 CPU
                // 每秒打印消息两次
                if (System.currentTimeMillis() >= nextPrintTime) {
                    Log.e(Tag, "job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L) // 1.3 s hou 取消job
        Log.e(Tag, "i am tired of wating")
        job.cancelAndJoin()
        Log.e(Tag, "now i can quit!")
    }

    /**
     * 使用 async 异步执行
     */
    private fun testAsyc() = runBlocking {
        val time = measureTimeMillis {
            val t1 = async { doSth1() }
            val t2 = async { doSth2() }
            val plus = t1.await() + t2.await() // async 返回一个类似于Java 的Future ,可以使用 .await() 获取他的值
            Log.e(Tag, "plus = $plus")
        }

        Log.e(Tag, "complete time is $time")
    }

    /**
     * 惰性启动的 async
     */
    private fun testLazyAsyc() = runBlocking {
        val time = measureTimeMillis {
            val t1 = async(start = CoroutineStart.LAZY) {
                Log.e(Tag, "doSth1 threadName = ${Thread.currentThread().name}")
                doSth1()
            }
            val t2 = async(start = CoroutineStart.LAZY) {
                Log.e(Tag, "doSth2 threadName = ${Thread.currentThread().name}")
                doSth2()
            }
            // 如果不调用 start ，只是调用 .await() 那么就是顺序执行的，调用 start 才是异步执行； 如果不调用 await 或者 start 那么就不会执行
            t1.start()
            t2.start()
            val plus = t1.await() + t2.await() //
            Log.e(Tag, "plus = $plus")
        }

        Log.e(Tag, "complete time is $time")
    }

    suspend fun doSth1(): Int {
        delay(1000L)
        return 5
    }

    suspend fun doSth2(): Int {
        delay(1000L)
        return 7;
    }

    fun testAsycFun() {
        doAsyc1()
        doAsyc2()
    }

    /**
     * aysc 风格函数 ，异步执行, 他 不是 挂起 函数，可以在任意函数中调用，不限于 suspend 函数中；
     * warn : kotlin 中不推荐这种做法,
     */
    fun doAsyc1() = GlobalScope.async {
        Log.e(Tag, "doSth1 start")
        doSth1()
        Log.e(Tag, "doSth1 end")
    }

    fun doAsyc2() = GlobalScope.async {
        Log.e(Tag, "doSth2 start")
        doSth2()
        Log.e(Tag, "doSth2 end")
    }

    fun testAsync2() = runBlocking {
        val time = measureTimeMillis {
            val res = concurrentSum()
            Log.e(Tag, "res = $res")
        }

        Log.e(Tag, "time = $time")
    }

    /**
     * 如果在 concurrentSum 函数内部发生了错误，并且它抛出了一个异常， 所有在作用域中启动的协程都会被取消。
     */
    suspend fun concurrentSum(): Int = coroutineScope {
        val one = async { doSth1() }
        val two = async { doSth2() }
        one.await() + two.await()
    }

    /**
     * 测试调度器与线程
     */
    private fun testDispatcher() = runBlocking {
        launch { // 运行在父协程的上下文中，即 runBlocking 主协程
            Log.e(Tag, "main runBlocking : i am working in ${Thread.currentThread().name}") // main
        }

        launch(Dispatchers.Unconfined) {// 不受限的-----将工作在主线程
            Log.e(Tag, "Unconfined : i am working in ${Thread.currentThread().name}") // main
        }

        launch(Dispatchers.Default) {// 默认的调度器中， 当协程在GlobalScope中启动时，使用的是由 Dispatchers.Default 代表的默认调度器
            Log.e(Tag, "Default : i am working in ${Thread.currentThread().name}") //  DefaultDispatcher-worker-1
        }

        // newSingleThreadContext 为协程的运行启动了一个线程。 一个专用的线程是一种非常昂贵的资源。 在真实的应用程序中两者都必须被释放，当不再需要的时候，使用 close 函数
        val newDispacher: ExecutorCoroutineDispatcher = newSingleThreadContext("MyOwnerThread")
        val owner: Job = launch(newDispacher) {// MyOwnerThread
            delay(2000L)
            Log.e(Tag, "newSingleThreadContext : i am working in ${Thread.currentThread().name}")
        }

        owner.join()
        Log.e(Tag, "close newDispacher ")
        newDispacher.close()
    }

    /**
     * 一个父协程总是等待所有的子协程执行结束。父协程并不显式的跟踪所有子协程的启动，并且不必使用 Job.join 在最后的时候等待它们：
     */
    private fun testFatherCoroutines() = runBlocking {
        val request = launch {
            repeat(3) { i ->
                launch {
                    delay((i + 1) * 200L)
                    Log.e(Tag, "Coroutines $i is done")
                }
            }
            Log.e(Tag, "request : I'm done and I don't explicitly join my children that are still active")
        }
        request.join()
        Log.e(Tag, "now processing of the request is complete")
    }

    //-------------------------------------流--------------------------------------------------------
    fun fooo(): Flow<Int> = flow { // 流构建器
        for (i in 1..3) {
//            delay(100) // 不会阻塞主线程
//            Thread.sleep(100) // 阻塞住 主线程
            emit(i) // 发送下一个值
        }
    }


    fun testFlow() = runBlocking {
        // 启动并发的协程
        launch {
            for (k in 1..3) {
                Log.e(Tag, " i am not blocking $k")
                delay(100)
            }
        }
        // 收集这个流
        fooo().collect { value -> Log.e(Tag, "value = $value") }
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

    fun ff(a: Int, b: Int) :Int{
        return a + b;
    }

    fun a(operation: (Int, Int) -> Int) : Int{
        return operation(7, 4)
    }

    fun int2String(operation: (Int) -> String){
        operation(4)
    }
    //----------------------------------------lambda 表达式 end ------------------------------------------------------------------

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

open class MyBase(val age: Int?) {
    var name: String? = null;
    open fun show() {

    }
}

class MyDetail(age: Int) : MyBase(age) {
    override fun show() {
        TODO("Not yet implemented")
    }

    fun doSth() {
        Log.e("MyKotlinActivity", "age = $age , name = $name")
    }

}

// Kt 中类默认是final 的，不可以被继承，要想继承 必须在 class 前面加上 open
open class User2{
    val id: Int
    val name: String

    // Kt 中的构造函数
    constructor(id: Int, name: String){
        this.id = id
        this.name = name
    }

}

class Stu(id: Int, name: String) : User2(id, name) {

}

open class Employee{
    var name: String ? = "default"

    constructor(name: String?){
        this.name = name
    }

    constructor(){

    }
}

class Manager (name: String?, age: Int?): Employee(name){

    var title: String? = "Manager"
    // 初始化代码块，先于次级构造器，后于主构造器执行
    init {
        Log.e("MyKotlinActivity", "init block Manager name = $name , title = $title")
    }

    val size: Int?
      get() {
          return name?.length
      }

}

//-------------------------------object 关键字 的使用 类似于Java中的单例---------------------------------------------------------------

/**
 * object 第一个字母是小写的，不同于Java 的Object, Kotlin 中 Any 对应Java中的Object ,表示所有类的基类
 * oject : 创建一个类，并且创建一个这个类的对象，使用很简单， 直接在 代码中调用 Sample.name, Sample.showName(), 类似于Java中的单例
 * Java 中静态变量和方法 等价于 kotlin 中的 companion object --- 伴生对象
 */
object Sample{
    val name = "test object"
    fun showName(){
        Log.e("MyKotlinActivity", "Sample name = $name")
    }
}

class A{

    fun doSthA(){

    }
    companion object{
        val age = 1
        fun showAge(){
            Log.e("MyKotlinActivity", "A age = ${age}")
        }
    }
}// 调用的地方 直接可以使用 A.age A.showAge()


class User1 constructor(name: String) { // 通常主构造器是可以省略的，但是 主构造器 上使用「可见性修饰符」或者「注解」就不可以省略 如下的 User4 类
    //                  👇 这里与构造器中的 name 是同一个
    var name: String = name

    constructor(name: String, age: Int): this(name){ // 通过 this 直接调用主构造器

    }

    constructor(name:String, age: Int, id: Int): this(name, age){ // 通过上一个次级构造器，间接调用主构造器

    }
}


/**
 *  constructor(var name: String) 表示在主构造器中直接声明属性，即 name 就是 User4 一个属性了；
 *  如果在主构造器的参数声明时加上 var 或者 val，就等价于在类中创建了该名称的属性
 */
class User4 private constructor(var name: String){// 主构造器被修饰为私有的，外部就无法调用该构造器
    constructor(name: String, age: Int): this(name){

    }
}

class LoginManager(var user: String){

    fun login(user: String, password: String){
        if (user.isEmpty()){
            throw IllegalArgumentException("illegal params user is null")
        }

        if (password.isEmpty()){
            throw IllegalArgumentException("illegal params password is null")
        }

        // 这个函数中 参数检查的部分有点冗余，我们又不想将这段逻辑作为一个单独的函数对外暴露，这时可以使用嵌套函数，即在 login 函数内部声明一个函数
        fun validate(value: String){
            if (value.isEmpty()){
                throw IllegalArgumentException("illegal params value is null")
            }
        }
        validate(user)
        validate(password)
    }
}