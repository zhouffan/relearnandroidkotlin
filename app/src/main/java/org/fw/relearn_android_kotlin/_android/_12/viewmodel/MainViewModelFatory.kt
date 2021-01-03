package org.fw.relearn_android_kotlin._android._12.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/3 21:48
 *    desc   :
 *    version: 1.0
 */
class MainViewModelFatory(private val countReserved: Int): ViewModelProvider.Factory {
    /**
     * create 与 activity生命周期无关
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(countReserved) as T
    }
}