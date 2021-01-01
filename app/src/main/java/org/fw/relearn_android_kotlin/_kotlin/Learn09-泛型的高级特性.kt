package org.fw.relearn_android_kotlin._kotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.UriMatcher
import android.util.Log
import androidx.core.content.edit
import com.google.android.material.chip.ChipDrawable
import org.fw.relearn_android_kotlin._android._09.ServiceActivity
import org.fw.relearn_android_kotlin.util.LogShow
import java.lang.StringBuilder
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/1 22:31
 *    desc   :
 *
 *    1 对于List<String>，在运行时期，JVM不知道是哪种类型，只能识别List
 *    2 所有基于JVM的语言，泛型功能都是通过类型擦除机制来实现的，包括kotlin
 *
 *    3 泛型实化：条件一：inline关键字修饰
 *               条件二：泛型前加 reified关键字，表示要实化
 *
 *    4 泛型的协变/逆变 ？？？？
 *
 *
 *    version: 1.0
 */
class Test09{
    fun foo(){
        bar<String>()
    }
    private inline fun <T> bar(){
        //xxxxxx
    }
    //====内联函数，编译时 内联函数中的代码 自动替换到 被调用的地方======bar()函数中的代码可以获得泛型的实际类型，可以进行实化
    fun foo2(){
        //xxxxxx
    }
    //===============inline 和 reified=================================
    /**
     * T.class在java中是不合法的，但在kotlin中可以
     */
    inline fun <reified T> getGenericType() = T::class.java
    fun test(){
        val result = getGenericType<String>()
        val result1 = getGenericType<Int>()
        "$result   $result1".LogShow()  // java.lang.String   java.lang.Integer
    }
    //===============泛型实化 应用=================================
    fun test2(context: Context){
        val intent = Intent(context, ServiceActivity::class.java)
        context.startActivity(intent)
        //===等价于===
        startActivity<ServiceActivity>(context)
        //封装
        startActivity2<ServiceActivity>(context){
            putExtra("key1", 1)
            putExtra("key2", "2")
        }
    }
    //转换
    inline fun <reified T> startActivity(context: Context){
        val intent = Intent(context, T::class.java)
        context.startActivity(intent)
    }
    //封装,  含有Intent上下文
    inline fun <reified T> startActivity2(context: Context, block: Intent.()->Unit){
        val intent = Intent(context, T::class.java)
        //执行匿名函数
        intent.block()
        context.startActivity(intent)
    }

}




//========================================分割线============================================例二


fun main() {

}