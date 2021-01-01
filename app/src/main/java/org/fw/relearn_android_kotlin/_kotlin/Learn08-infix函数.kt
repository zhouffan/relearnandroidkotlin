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
 *    date   : 2021/1/1 20:57
 *    desc   :
 *    version: 1.0
 */


/**
 * 1 一种高级语法糖：infix函数， 把编程语言函数调用的语法规则改变类  如 A to B，等价于 A.to(B)
 * 2 原因： infix函数允许函数调用时的小数点/括号等计算机相关的语法去掉，接近英语语法编程， A.to(B) ===> A to B
 * 3 限制1：不能定义成顶层函数，必须是某个类的成员函数， 可使用扩展函数定义到某个类中
 *   限制2：只能接收一个参数
 *
 *
 */
class Test08{
    fun test08(){
        if ("Hello world".startsWith("Hello")){

        }
        //使用自定义的infix函数
        if ("Hello world" beginsWith "Hello"){

        }
    }
    /**
     * 扩展函数， 编程infix函数
     */
    private infix fun String.beginsWith(prefix: String) = startsWith(prefix)
}




//========================================分割线============================================例二
class Test01_2{
    fun test02_2(){
        val list = listOf("a", "b", "c")
        if(list.contains("b")){

        }
        //===等价于===
        if(list has "b"){

        }
        /**
         * public infix fun <A, B> A.to(that: B): Pair<A, B> = Pair(this, that)
         * 1 定义在A类型下的，接收B类型的参数。
         * 2 对应的实现： 包含A/B数据的Pair对象
         * 3 mapof接收Pair类型的可变参数列表
         *
         */
        val map = mapOf("key1" to  1, "key2" to 2)
        //自定义
        val map2 = mapOf("key1" with 1, "key2" with 2)

    }
    private infix fun <T> Collection<T>.has(element: T) = contains(element)

    //mapof  to
    private infix fun <A, B> A.with(that: B): Pair<A, B> = Pair(this, that)
}


fun main() {

}