package org.fw.relearn_android_kotlin._kotlin

import org.fw.relearn_android_kotlin._android._03.adapter.MsgAdapter
import java.lang.Exception

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/26 15:56
 *    desc   :
 *    version: 1.0
 */

private class Test03{
    /**
     * 1 lateinit延迟初始化
     *
     */
    lateinit var adapter: MsgAdapter

    fun test(){
        /**
         * 2 !::adapter.isInitialized，判断是否已经初始化，避免重复
         *
         */
        if(!::adapter.isInitialized){
            adapter = MsgAdapter(arrayListOf())
        }
    }
}
//==================================================================================================密封类  sealed class
interface Result
class Success(val msg:String): Result
class Failure(val error: Exception): Result

fun getResultMsg(result: Result) = when(result){
    is Success -> result.msg
    is Failure -> result.error.toString()
    //必须需要考虑else
    else -> throw IllegalArgumentException()
}
/**
 * 1 密封类是可继承的类
 * 2 密封类及其子类只能定义在同一个文件的顶层位置。不能嵌套
 * 3 原理：实现了密封了的所有子类，编译器要求必须判断，否则报错。因此就不用考虑else
 *
 */
sealed class Result2
class Success2(val msg:String): Result2()
class Failure2(val error: Exception): Result2()

fun getResultMsg2(result: Result2) = when(result){
    is Success2 -> result.msg
    is Failure2 -> result.error.toString()
}


