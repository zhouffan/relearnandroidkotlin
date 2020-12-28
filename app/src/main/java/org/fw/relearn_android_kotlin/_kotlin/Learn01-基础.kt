package org.fw.relearn_android_kotlin._kotlin

import android.view.View
import android.widget.Button


/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/23 20:59
 *    desc   :
 *    version: 1.0
 */
class Learn01 {

    /**
     * 1 kotlin完全摒弃了基本数据类型，全部使用对象数据类型。
     * 2 除非一个变量明确可以被修改，否则加final。 kotlin中永远优先使用val，其次var。
     * 3 if语句 是可以有返回值的
     * 4 关键字： public/private/protected:当前类和子类可见(java中当前类 子类 同一个包路径下可见) /internal：同一模块中的类可见。  摒弃default
     */
    fun test(): Unit {
        var num = 10
        //if一行编写
        val str2 = if(num >1) "cc" else "dd"
        println(isNum(1))
        printNum()
    }

    /**
     * 循环 0..10 --->[0, 10]
     */
    fun printNum(){
        //..为语法糖， 运算符重载
        for (i in 0.rangeTo(10)){
        }
        for ( i in 0..10){
            print("$i ")
        }
        println()
        val range = 0 until 10//0..9  [0, 10)
        for (i in range step 2){
            print("$i ")
        }
        println()
        for (i in 10 downTo 0 step 2){//降序
            print("$i ")
        }
    }

    /**
     * when可以不写传参
     */
    fun getScore3(name: String) = when{
        name == "tom" -> 20
        name.startsWith("j") -> 30
        name.endsWith("p") -> 40
        else -> -1
    }


    fun isNum(num: Number): Int{
        return when(num){
            is Int -> {10 + 10}
            is Double -> 40
            is Float -> 50
            else -> {-1}
        }
    }

    fun getScore(name: String) = when(name){
        "tom"-> 10
        "jim"-> 20
        "jack"-> 30
        else -> -1
    }

    fun getScore2(name: String) = if (name == "tom"){
        10
    }else if(name == "jim"){
        20
    }else if(name == "jack"){
        30
    } else{
        -1
    }

    /**
     * if 返回最后一行
     */
    fun getIfReturn(){
        val num = 10
        val str = if(num > 1){
            "aa"
        }else{
            "bb"
        }
    }

}
//========================================分割线=====================================================类
/**
 * 1 ===============主构造函数中申明成var或者val的参数将自动成为该类的字段===============
 * 2 次构造函数必须调用主构造函数
 * 3 子类的主构造函数必须调用父类的主构造函数
 * 4 主构造函数在类名后，次构造函数在类中定义
 */
class Student2_1(val sno: String, val grade:String, name: String, age: Int): Person2(name, age){
    constructor(sno: String, grade: String): this(sno, grade, "none", -1)
    constructor(): this("none", "none") //无参构造函数
}

/**
 * 特殊情况：只有构造函数，没有主构造函数，少见
 */
class Student2_2: Person2{
    constructor(name: String, age: Int): super(name, age)
}
open class Person2(val name: String, val age: Int){

}

/**
 * 1 oepn 关键字继承
 * 2 每个类都有一个不带参的主构造函数，没有函数体
 * 3 子类构造函数必须调用父类构造函数（student类的主构造函数会调用Person父无参构造函数）
 */
class Student2(val name: String = "none", val age: Int = -1): Person(){
    init {
        print("$name   ----  $age")
    }
}
class Student: Person() {
    var name = ""
    var age = -1
}
open class Person{
    var id = -1
}
//========================================分割线=====================================================接口
class Student3_1(name: String, age: Int):Person2(name, age), Study{
    override fun readBooks() {
        println("readBooks")
    }
    /**
     * 可选择是否实现
     */
    override fun doHomework() {
        println("doHomework")
    }
}
interface Study{
    fun readBooks()
    fun doHomework(){
        println("默认实现类")
    }
}

//========================================分割线=====================================================数据类和单例类
/**
 * data关键字： 根据主构造函数的参数，自动生成equals()/hashCode()/toString()等固定无实际逻辑意义的方法
 *
 */
data class Cellphone(val id: String, val price: Double)

/**
 * object关键字：单例类
 */
object Singleton{
    fun test1(){
        println("单例类--方法，打印")
    }
}
//========================================分割线=====================================================集合--lambda
/**
 * listOf：不可变函数
 * mutableListOf：可变
 * setOf: 底层使用hash映射机制存放数据，元素是无序的
 *
 * Lambda语法结构：{参数1：参数类型，参数2：参数类型 -> 函数体}
 */
fun testList(){
    val list = listOf("a", "b", "c")
    for (i in list){
        print("$i  ")
    }
    println()
    val list2 = mutableListOf("a", "b", "c")
    list2.add("d")
    for (j in list2){
        print("$j  ")
    }
    println()
    val list3 = setOf("a", "b", "c")
    for (z in list3){
        print("$z  ")
    }
    println()
    val hashMap1 = HashMap<String, String>()
    hashMap1.put("1", "1")
    hashMap1["2"] = "2"
    //简写  infix函数
    val hashMap2 = mapOf("1" to "1", "2" to "2")
    for ((key, value) in hashMap2){
        print("$key -- $value \n")
    }

    println("==============================================================================函数演变")
    val list_1 = listOf("aaa","bbb","a","sssss","bbbbbbb")
    var maxStr = ""
    for (i in list_1){
        if(i.length > maxStr.length){
            maxStr = i
        }
    }
    println(maxStr)
    //使用maxBy函数，演变一
    val lambda = {str: String -> str.length}
    val maxStr1 = list.maxBy(lambda)
    //使用maxBy函数，演变二
    val maxStr2 = list.maxBy({str: String -> str.length})
    //使用maxBy函数，演变三   规定：当Lambda参数是函数的最后一个参数时，可将Lambda表达式移到括号外
    val maxStr3 = list.maxBy(){str: String -> str.length}
    //使用maxBy函数，演变四   规定：当Lambda参数是函数的唯一一个参数，则可省略括号
    val maxStr4 = list.maxBy{str: String -> str.length}
    //使用maxBy函数，演变五   规定：当Lambda表达式的参数只有一个，则不必申明参数，用it关键字代替
    val maxStr5 = list.maxBy{it.length}

    println("==============================================================================集合的函数式api：map函数，filter函数...")
    /**
     * 1 map函数，对元素进行任意的映射转换
     * 2 filter函数过滤
     * 3 any函数至少一个满足
     * 4 all函数所有满足
     */
    println("any: " + list_1.any { it.length > 5})
    println("all: " + list_1.all { it.length > 5})
    val list_2 = list_1
        .filter { it.length > 3 }
        .map { it.toUpperCase() }
    for (i in list_2){
        print("$i  ")
    }
    println("==============================================================================java的函数式api：如Runnable 接口")
    /**
     * 舍弃new关键字，改用object
     * 经常使用java开发的sdk方法，kotlin调用这些接口时，可能会用到如下java函数式API的写法
     */
    println("currentThread:"+ Thread.currentThread())
    //Runnable 接口
    Thread(object :Runnable{
        override fun run() {
            println("currentThread:"+ Thread.currentThread())
        }
    }).start()
    //演变 1
    Thread(Runnable { println("currentThread:"+ Thread.currentThread()) }).start()
    //演变 2--java方法的参数列表中只有一个java单抽象方法接口参数，对接口进行省略
    Thread( { println("currentThread:"+ Thread.currentThread()) }).start()
    //演变 3--Lambda表达式是最后一个参数，可移到括号外面。同时只有一个参数时可以省略括号
    Thread { println("currentThread:"+ Thread.currentThread()) }.start()

    //eg：OnClickListener
    val button: Button? = null
    button?.setOnClickListener(object :View.OnClickListener{
        override fun onClick(v: View?) {
            print(v?.id)
        }
    })
    button?.setOnClickListener {
        print(it.id)
    }

    println("==============================================================================空指针检查")
    /**
     * 1 kotlin默认所有的参数和变量都 不 可为空， Int？ 就表示可以容纳空
     *
     */
    //判空辅助工具
    val str3: String? = null
    str3?.toUpperCase() //不为空时执行toUpperCase
    val c = if (str3 != null) str3 else ""
    val c_1 = str3 ?: ""





    println("============================")
}

class Test{
    var content: String? = "aaa"
    /**
     * !!.  :确定这里不会为空， 如果出现空了，抛空指针异常
     *
     */
    fun toUpper(){
        val up = content!!.toUpperCase()
        print(up)
    }
    //========================================分割线=====================================================let函数

    /**
     * let 函数可以处理全局变量的判空问题，全局变量随时可修改
     *
     */
    fun doStudy(study: Study? = null, num: Number = 10){
        study?.doHomework()
        study?.readBooks()

        study?.let {
            it.doHomework()
            it.readBooks()
        }
    }

    /**
     * 方法参数指定默认值及传特定参数，  思考：对应到主/次构造函数（为什么可以省略次构造函数）
     *
     */
    fun newObj(){
        doStudy()
        doStudy(num = 30)
        doStudy(null, 20)

        //主/次构造函数
        val stu_1_1 = Stu_1()
        val stu_1_2 = Stu_1("zz", 100)
        //使用默认值替代 次构造函数
        val stu_2_1 = Stu_2()
        val stu_2_2 = Stu_2("123", "男")
        val stu_2_3 = Stu_2(sex = "男", age = 100)
    }
}

/**
 * 是否有必要定义次构造函数？
 *
 */
class Stu_1(val sno: String, val sex: String, name: String, age: Int): Person2(name, age){
    constructor(name: String, age: Int):this("","", name, age)
    constructor():this("", -1)
}
class Stu_2(val sno: String = "", val sex: String = "", name: String ="", age: Int = -1): Person2(name, age){

}



fun main() {
    val learn01 = Learn01()
    learn01.test()
    println()
    //======================
    val student = Student()
    student.age = 100
    println(student)
    val student2 = Student2()
    println(student2)
    //======================
    val phone = Cellphone("123", 10.2)
    val phone2 = Cellphone("123", 10.2)
    println(phone)
    println(phone == phone2) //true
    Singleton.test1()
    //======================
    testList()
}