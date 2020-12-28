package org.fw.relearn_android_kotlin.util

import android.content.Context
import android.widget.Toast
import org.fw.relearn_android_kotlin.MyApp

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/28 22:42
 *    desc   :
 *    version: 1.0
 */

fun String.showToast(){
    Toast.makeText(MyApp.get(), this, Toast.LENGTH_SHORT).show()
}

fun String.LogShow(){
    android.util.Log.i("test=====>", this)
}