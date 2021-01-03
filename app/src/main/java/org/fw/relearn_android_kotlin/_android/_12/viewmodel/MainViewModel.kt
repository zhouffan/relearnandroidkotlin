package org.fw.relearn_android_kotlin._android._12.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/3 21:33
 *    desc   :
 *
 *    1 LiveData: 在activity不可见时， 发生多次数据变化，当恢复时，以最后一次变化通知给观察者
 *
 *
 *    version: 1.0
 */
class MainViewModel(conuntReserved: Int): ViewModel() {
//    var counter = conuntReserved
//    fun add(){
//        counter++
//    }

    //改写一， 使用LiveData 观察者模式，异步将结果通知给activity
//    val counter = MutableLiveData<Int>()
//    init {
//        counter.value = conuntReserved
//    }
//
//    fun add(){
//        val count = counter.value ?: 0
//        counter.value = count + 1
//    }
//
//    fun clear(){
//        counter.value = 0
//    }

    //改写二， 只暴露不可变的LiveData给外部
    val _counter: LiveData<Int>
        get() = this.counter
    private val counter = MutableLiveData<Int>()
    init {
        counter.value = conuntReserved
    }

    fun add(){
        val count = counter.value ?: 0
        counter.value = count + 1
    }

    fun clear(){
        counter.value = 0
    }
}

/**
 * 使用map，  假设 对外只暴露user的姓名
 *
 */
class MainViewModel2(): ViewModel(){
    private val userLiveData = MutableLiveData<User>()

    /**
     * userLiveData数据发生变化时， map函数会监听到变化，并执行内部逻辑返回给userName
     */
    val userName: LiveData<String> = Transformations.map(userLiveData){
        "${it.firstName}   ${it.lastName}"
    }


    /**
     * 如果Repository 不调用wsitchMap，则每次返回一个新的LiveData对象
     */
    fun getUser(userId: String): LiveData<User>{
        return Repository.getUser(userId)
    }

    /**
     * 改写 getUser
     * 1 调用getUser2，仅仅知识设置值
     * 2 userIdLiveData数据变化，触发转换函数的 Repository.getUser(it)执行
     * 3 返回结果给 user
     *
     */
    private val userIdLiveData = MutableLiveData<String>()
    val user: LiveData<User> = Transformations.switchMap(userIdLiveData){
        Repository.getUser(it)
    }
    fun getUser2(userId: String){
        userIdLiveData.value = userId
    }

    /**
     * 如果是无参的，调用下面方式
     */
    fun getUser2_2(){
        userIdLiveData.value = userIdLiveData.value
    }
}







