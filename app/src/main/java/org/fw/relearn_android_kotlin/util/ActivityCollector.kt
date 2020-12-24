package org.fw.relearn_android_kotlin.util

import android.app.Activity

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/24 21:47
 *    desc   :
 *    version: 1.0
 */
object ActivityCollector {
    val activities = ArrayList<Activity>()

    fun addActivity(activity: Activity){
        activities.add(activity)
    }

    fun removeActivity(activity: Activity){
        activities.remove(activity)
    }

    fun finishAll(){
        for (activity in activities) {
            activity.finish()
        }
        activities.clear()
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}