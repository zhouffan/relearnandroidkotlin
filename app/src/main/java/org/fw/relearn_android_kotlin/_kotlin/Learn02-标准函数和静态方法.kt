package org.fw.relearn_android_kotlin._kotlin

import android.content.Intent
import java.lang.StringBuilder

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/24 21:57
 *    desc   :
 *    version: 1.0
 */
fun testRoot(){
    /**
     * 1 with函数接收2个参数：任意值    和       Lambda表达式(提供上下文)-----最后一行作为返回值
     * 2 run函数   在某个对象的基础上调用，只有一个Lambda参数(提供上下文)-------最后一行作为返回值
     * 3 apply函数 在某个对象的基础上调用，只有一个Lambda参数(提供上下文)-------返回上下文对象本身
     */
    val list = listOf("a", "b", "c")
    println("=======================================================================================with函数")
    var result = with(StringBuilder()){
        append("=====\n")
        for (s in list) {
            append("$s===")
        }
        toString()
    }
    print(result)
    println("=======================================================================================run函数")
    result = StringBuilder().run {
        append("=====\n")
        for (s in list) {
            append("$s===")
        }
        toString()
    }
    print(result)
    println("=======================================================================================apply函数")
    val result1 = StringBuilder().apply {
        append("=====\n")
        for (s in list) {
            append("$s===")
        }
    }
//    val intent = Intent("").apply {
//        putExtra("key0", "value0")
//        putExtra("key1", "value1")
//    }
    print(result1.toString())

}

//==================================================================================================定义静态方法
object Util1{
    //@JvmStatic
    fun test(){
        println("Util1===>静态方法")
    }
}
class Util2{
    /**
     * 1 companion object 关键字会在Util类的内部创建一个伴生类，里面的方法就是对应的实例方法。 Util2.test()实际调用的是伴生对象的该方法
     * 2 真正的静态方法是需要在 单例类或者companion object中的方法上加 @JvmStatic。则会编译成真正的静态方法，java代码中也可调用
     * 3 顶层方法会被编译成静态方法，直接调用。  如果是java中调用，则使用====>文件名.方法名
     */
    companion object{
        @JvmStatic
        fun test(){
            println("Util2===>静态方法")
        }
    }
}

fun main() {
    testRoot()
    println()
    Util1.test()
    Util2.test()

}