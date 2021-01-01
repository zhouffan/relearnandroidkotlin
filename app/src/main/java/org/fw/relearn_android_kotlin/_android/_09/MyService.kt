package org.fw.relearn_android_kotlin._android._09

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import org.fw.relearn_android_kotlin.util.LogShow

class MyService : Service() {
    private val myBinder = MyBinder()
    class MyBinder: Binder(){
        fun startDownload(){
            "startDownload ".LogShow()
        }

        fun getProgress(): Int{
            "getProgress ".LogShow()
            return 0
        }

    }
    override fun onBind(intent: Intent): IBinder {
        return myBinder
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}