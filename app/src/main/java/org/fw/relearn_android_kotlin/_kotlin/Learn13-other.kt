package org.fw.relearn_android_kotlin._kotlin

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.parcel.Parcelize
import org.fw.relearn_android_kotlin.MyApp
import java.io.Serializable
import java.lang.RuntimeException
import kotlin.math.max

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/3 20:59
 *    desc   :
 *
 *
 *
 *    version: 1.0
 */

/**
 * 进行序列化
 *
 */
class Person1(val name: String): Serializable{
    val id = 0
}

@Parcelize
class Person3(val name: String): Parcelable{
    val id = 0
}

fun main() {
    val intent: Intent? = null
    val p1 = intent?.getSerializableExtra("person") as Person2
    val p2 = intent.getParcelableExtra("person") as Person3

}


object LogUtil{
    private const val VERBOSE = 1
    private const val DEBUG = 2
    private const val INFO = 3
    private const val WARN = 4
    private const val ERROR = 5
    private var level = VERBOSE

    fun v(tag: String, msg: String){
        if(level <= VERBOSE){
            Log.v(tag, msg)
        }
    }
    fun d(tag: String, msg: String){
        if(level <= DEBUG){
            Log.d(tag, msg)
        }
    }
    fun i(tag: String, msg: String){
        if(level <= INFO){
            Log.i(tag, msg)
        }
    }
    fun w(tag: String, msg: String){
        if(level <= WARN){
            Log.w(tag, msg)
        }
    }
    fun e(tag: String, msg: String){
        if(level <= ERROR){
            Log.e(tag, msg)
        }
    }
}