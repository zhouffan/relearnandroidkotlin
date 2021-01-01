package org.fw.relearn_android_kotlin._android._09

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import org.fw.relearn_android_kotlin.R
import org.fw.relearn_android_kotlin.util.LogShow

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/1 22:22
 *    desc   :
 *    需要声明权限  <uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>
 *
 *    version: 1.0
 */
class ForegroundService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        "".LogShow()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            var notificationChannel = NotificationChannel(
                "myservice",
                "前台service",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(notificationChannel)
        }
        val intent = Intent(this, ServiceActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, 0)
        val notification = NotificationCompat.Builder(this, "myservice").setContentTitle("this is title")
            .setContentText("this is text")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.big_image))
            .setContentIntent(pi)
            .build()
        startForeground(1, notification)

    }
}