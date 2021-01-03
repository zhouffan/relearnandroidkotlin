package org.fw.relearn_android_kotlin._android._12.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/3 23:00
 *    desc   :
 *
 *    1 单例类
 *    2 每次返回新的 LiveData
 *
 *    version: 1.0
 */
object Repository {
    fun getUser(userId: String): LiveData<User>{
        val liveData = MutableLiveData<User>()
        liveData.value = User("","xx  $userId",1)
        return liveData
    }
}