package org.fw.relearn_android_kotlin

import android.app.Application

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/28 22:45
 *    desc   :
 *    version: 1.0
 */
class MyApp : Application(){

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object{
        private var app: Application? = null
        fun get(): Application{
            return app!!
        }
    }
}