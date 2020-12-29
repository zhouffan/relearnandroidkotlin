package org.fw.relearn_android_kotlin._kotlin

import android.content.SharedPreferences
import android.content.UriMatcher
import android.util.Log
import androidx.core.content.edit
import com.google.android.material.chip.ChipDrawable
import java.lang.StringBuilder
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/29 23:24
 *    desc   :
 *    version: 1.0
 */

/**
 * 1 泛型类/泛型方法
 * 2 泛型默认值  Any?
 *
 */
class Myclass<T>{
    fun test(param: T){
        println(param)
    }
}
class Myclass2{
    fun <T> test(param: T): T{
        println(param)
        return param
    }
}
class MyClass3{
    /**
     * 泛型限制   数字类型  Int/Float/Double
     */
    fun <T: Number> test(param: T): T{
        println(param)
        return param
    }
}

//只能作用于StringBuilder
fun StringBuilder.build2(block: StringBuilder.()->Unit): StringBuilder{
    block()
    return this
}
//可以作用于所有类上
fun <T> T.build2(block: T.()->Unit): T{
    block()
    return this
}

//========================================分割线============================================委托
/**
 * 1 委托是一种设计模式： 把工作委托给李恩一个辅助对象处理（将一个类的具体实现委托给另一个类去完成）
 * 2 分为 类委托/委托属性
 * 3 by关键字
 *
 * set/list  ： set无序且不能重复
 */
/**
 * 解释：Myset委托helperSet进行实现,  如果Set有成百上千方法，怎么办？
 *      大部分调用辅助对象的方法，少部分自己实现，甚至加速自己的方法
 */
class Myset<T>(private val helperSet: HashSet<T>): Set<T>{
    override val size: Int
        get() = helperSet.size

    override fun contains(element: T)= helperSet.contains(element)

    override fun containsAll(elements: Collection<T>)=helperSet.containsAll(elements)

    override fun isEmpty()=helperSet.isEmpty()

    override fun iterator()=helperSet.iterator()
}
//委托helperSet处理
class Myset2<T>(private val helperSet: HashSet<T>): Set<T> by helperSet{
    //自定义方法
    fun hello() = println("xxx")

    //选择性重写
    override fun isEmpty() = false
}


/**
 * 1 属性委托： 将一个属性（字段）的具体实现委托给另一个类完成
 * 2 必须实现 getValue和setValue（不一定必须，如果属性是val，无法重新赋值）  并且使用operator关键字
 *
 */
class Myclass3{
    var name by Delegate()
}
class Delegate{
    var value: Any? = null
    operator fun getValue(myclass3: Myclass3, property: KProperty<*>): Any? {
        return value
    }

    operator fun setValue(myclass3: Myclass3, property: KProperty<*>, any: Any?) {
        value = any
    }
}
//========================================分割线============================================lazy函数

fun test07(){
    /**
     * 1 by是关键字，  lazy是高阶函数
     * 2 在lazy函数中会创建并返回一个Delegate对象，调用p属性时，其实调用的是 Delegate对象的getValue。
     *   getValue又调用lazy函数传入的Lambda表达式， 表达式执行，并返回最后一行
     */
    val p by lazy {  }
}

/**
 * 自定义lazy函数
 * 接收一个函数类型参数
 *
 * 懒加载是不会对属性进行赋值，就不写setValue
 */
class Later<T>(val block: ()->T){
    var value: Any? = null
    //any: Any? 希望在所有类中都可以使用
    operator fun getValue(any: Any?, prop: KProperty<*>): T{
        if(value == null){
            value = block()
        }
        return value as T
    }
}
fun <T> later(block: () -> T) = Later(block)
//测试
fun test07_1(){
    val uriMatcher by later {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        matcher.addURI("","",1)
        matcher.addURI("","",1)
        matcher
    }

    /**
     * activity点击事件调用该属性p，  只有点击时才会打印，进行初始化执行，并且第二次是点击时是不会再次执行。
     */
    val p by later {
        Log.i("ss", "ssss")
        "xxxx"
    }
}

fun main() {
    val my1 = Myclass<String>()
    my1.test("1111")
    val  my2 = Myclass2()
    my2.test("qqq")

    "".build2 {
        //
    }
}