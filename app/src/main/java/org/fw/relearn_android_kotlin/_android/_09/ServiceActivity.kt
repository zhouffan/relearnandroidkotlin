package org.fw.relearn_android_kotlin._android._09

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import kotlinx.android.synthetic.main.activity_service.*
import org.fw.relearn_android_kotlin.R
import org.fw.relearn_android_kotlin.util.LogShow
import kotlin.concurrent.thread


/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/1 21:46
 *    desc   :
 *    1 service 默认在主线程
 *    2 从8.0开始，需要报错前台可见状态，才能稳定运行，进入后台，随时可能被回收。
 *    3 长期在后台执行任务： 前台Service 和WorkManager
 *
 *    4 前台service和普通service区别，前台service一直会有一个正在运行的图标，在系统的状态栏显示
 *
 *    version: 1.0
 */


class ServiceActivity : AppCompatActivity() {
    private val connection = object :ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("Not yet implemented")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MyService.MyBinder
            binder.startDownload()
            binder.getProgress()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        MyThread().start()
        Thread(MyThread2()).start()
        //等价匿名函数
        Thread{
            "MyThread2  run".LogShow()
        }.start()
        //使用内置的顶层函数,  不用写start
        thread {
            "MyThread3  run".LogShow()
        }



        //service 绑定
        btn1.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
        btn2.setOnClickListener {
            unbindService(connection)
        }
    }

    class MyThread: Thread(){
        override fun run() {
            super.run()
            "MyThread  run".LogShow()
        }
    }
    class MyThread2: Runnable{
        override fun run() {
            "MyThread2  run".LogShow()
        }

    }
}