package org.fw.relearn_android_kotlin._android._12

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import org.fw.relearn_android_kotlin.util.LogShow

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/3 22:02
 *    desc   :
 *    version: 1.0
 */
/**
 * lifecycle.currentState当前状态
 */
class MyObserver(private val lifecycle: Lifecycle): LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun activityStart(){
        "activityStart   ${lifecycle.currentState}".LogShow()
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun activityStop(){
        "activityStop".LogShow()
    }
}