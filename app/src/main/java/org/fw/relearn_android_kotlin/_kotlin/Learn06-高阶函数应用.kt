package org.fw.relearn_android_kotlin._kotlin

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/29 00:02
 *    desc   :
 *    version: 1.0
 */

/**
 * 1 高阶函数非常适合简化各种API调用
 *
 */
fun spPut(context: Context){
    val editor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit()
    editor.putString("1","1")
    editor.putString("2","2")
    editor.apply()
}

/**
 * 1 添加扩展函数：open
 * 2 高阶函数：接收一个 函数类型 参数
 * 3 SharedPreferences.Editor.  拥有SharedPreferences.Editor的上下文
 *
 */
fun SharedPreferences.open(block: SharedPreferences.Editor.()->Unit){
    var edit = edit()
    edit.block() //？
    edit.apply()
}
fun spPut2(context: Context){
    context.getSharedPreferences("data", Context.MODE_PRIVATE).open {
        putString("1","1")
        putString("2","2")
    }
}
//========================================分割线=====================================================
/**
 * SharedPreferences自带
 */
fun test(context: Context){
    context.getSharedPreferences("data", Context.MODE_PRIVATE).edit {
        putString("1","1")
        putString("2","2")
    }
}

//========================================分割线=====================================================封装ContentValues
/**
 * 原始写法
 */
fun getContentValue(): ContentValues{
    val values = ContentValues()
    values.put("11", "11")
    values.put("22", 22)
    values.put("33", 33.3)
    return values
}

/**
 * vararg可变参数
 */
fun cvOf(vararg pair: Pair<String, Any?>): ContentValues{
    val cv = ContentValues()
    pair.forEach {
        var key = it.first
        var value = it.second
        when(value){
            //自动转型
            is Int -> cv.put(key, value)
            is Long -> cv.put(key, value)
            is Short -> cv.put(key, value)
            is Float -> cv.put(key, value)
            is Double -> cv.put(key, value)
            is Boolean -> cv.put(key, value)
            is String -> cv.put(key, value)
            is Byte -> cv.put(key, value)
            is ByteArray -> cv.put(key, value)
            null -> cv.putNull(key)
        }
    }

    return cv
}

/**
 * 写法完善一点
 */
fun cvOf2(vararg pair: Pair<String, Any?>) = ContentValues().apply {
    pair.forEach {
        var key = it.first
        var value = it.second
        when(value){
            //自动转型
            is Int -> put(key, value)
            is Long -> put(key, value)
            is Short -> put(key, value)
            is Float -> put(key, value)
            is Double -> put(key, value)
            is Boolean -> put(key, value)
            is String -> put(key, value)
            is Byte -> put(key, value)
            is ByteArray -> put(key, value)
            null -> putNull(key)
        }
    }
}

fun getContentValue2(): ContentValues?{
    cvOf("11" to "11", "22" to 22, "33" to 33.33)
    cvOf2("11" to "11", "22" to 22, "33" to 33.33)
    return null
}



fun main() {



}