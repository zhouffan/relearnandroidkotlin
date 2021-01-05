package org.fw.weather

import android.app.Application

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/5 21:44
 *    desc   :
 *    version: 1.0
 */
class MyApp :Application(){

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object{
        lateinit var instance: MyApp
        const val token = "xxxxxxxxx"
    }
}