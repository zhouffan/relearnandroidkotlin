package org.fw.relearn_android_kotlin.util

import android.util.Log

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/24 23:30
 *    desc   :
 *    version: 1.0
 */
object LogUtil {
    private const val tag = "LogUtil=====> "

    fun log(msg: String){
        Log.i(tag, msg)
    }
}