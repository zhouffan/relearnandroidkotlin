package org.fw.relearn_android_kotlin._kotlin

import org.fw.relearn_android_kotlin.util.LogUtil
import java.lang.StringBuilder

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/26 16:58
 *    desc   :
 *    version: 1.0
 */

/**
 * 1 扩展函数： 修改某个类源码的情况下， 扩展新的函数
 *
 */
object StringUtil{
    fun lettersCount(string: String): Int{
        var count = 0
        for (char in string){
            if(char.isLetter()){
                count++
            }
        }
        return count
    }
}

//=================================================================================================扩展函数
/**
 * 2 扩展函数，最好定义在最外层，它就拥有全局的访问域
 *
 */
fun String.lettersCount(): Int{
    var count = 0
    for (char in this){
        if(char.isLetter()){
            count++
        }
    }
    return count
}


//=================================================================================================运算符重载
/**
 * 3 运算符重载使用operator关键字
 * 4 operator/plus 都是关键字
 * 5 一个运算符可以重载
 * 6 语法糖 --> 实际调用函数    + --> plus      - --> minus      * --> times      / --> div       % --> rem      ++ --> inc
 *   -- --> dec      +a --> unaryPlus      -a --> unaryMinus      !a --> not       == --> equals      >= --> compareTo
 *   .. --> rangeTo     a[b] --> a.get(b)      a[b]=c --> a.set(b,c)      a in b --> b.contains(a)
 *   eg: a in b，实际调用的函数是b.contains(a)
 *
 */
class Money(private val value: Int){
    operator fun plus(money: Money): Money{
        val sum = this.value + money.value
        return Money(sum)
    }
    operator fun plus(num: Int): Money{
        val sum = this.value + num
        return Money(sum)
    }

    override fun toString(): String {
        return "===>$value"
    }

    fun test(){
        //================================================================String类对contains()函数进行了重载
        if("hello".contains("he")){
        }
        //等价于
        if("he" in "hello"){
        }
    }
}
//==========================================================应用
/**
 * 传统写法
 */
fun getRandomLengthStr(string: String): String{
    val n = (1..20).random()
    val sb = StringBuilder()
    repeat(n){
        sb.append(string)
    }
    return sb.toString()
}
/**
 * 1 运算符重载应用
 *
 * 将string 随机重复n遍
 */
//operator fun String.times(num: Int): String{
//    val sb = StringBuilder()
//    repeat(num){
//        sb.append(this)
//    }
//    return sb.toString()
//}
//string内置了重复函数
operator fun String.times(num: Int) = repeat(num)

//变写 getRandomLengthStr
fun getRandomLengthStr2(string: String): String = string * (1..2).random()

fun main() {
    val str = "wer23aa"
    println(str.length)
    println(StringUtil.lettersCount(str))
    println(str.lettersCount())

    println(Money(10).plus(Money(20))) //实际调用函数， 对应的语法糖 +
    println(Money(10) + Money(20))

    println(Money(10) + 30)

    val str2 = "abc" * 3
    println(str2)
}

