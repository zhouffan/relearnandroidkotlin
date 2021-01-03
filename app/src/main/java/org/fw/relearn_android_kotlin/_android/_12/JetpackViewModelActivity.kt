package org.fw.relearn_android_kotlin._android._12

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.activity_jetpack_test.*
import org.fw.relearn_android_kotlin.R
import org.fw.relearn_android_kotlin._android._12.viewmodel.MainViewModel
import org.fw.relearn_android_kotlin._android._12.viewmodel.MainViewModel2
import org.fw.relearn_android_kotlin._android._12.viewmodel.MainViewModelFatory


/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/3 21:25
 *    desc   :
 *
 *    1 组件不依赖任何Android系统版本，是定义在AndroidX库中的，能向下兼容
 *    2 MVP/MVVM出现的原因：activity太重，负责逻辑处理，负责UI展示，处理网络回调等。
 *
 *    3 viewmodel生命周期：旋转屏幕时，不会改变，只有activity销毁时，viewmodel才会销毁
 *    4 通常 activity和fragment 都要创建对应的viewmodel。 如MainViewModel，继承ViewModel
 *
 *    5 viewModel 有独立的生命周期，长于activity。 如果在activity中创建viewmodel实例，每次都会执行onCreate创建新的viewmodel。 旋转屏幕时则无法保存数据
 *
 *    6 LiveData: jetpack提供的响应式编程组件，可存放任意类型数据。  数据变化时通知给观察者（//如果add函数是线程异步 ？   禁止把activity传到 viewModel中去）
 *
 *    version: 1.0
 */
class JetpackViewModelActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var sp: SharedPreferences
    lateinit var customObserver: CustomObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack_test)

        //生命周期监听    引入包：implementation group: 'androidx.lifecycle', name: 'lifecycle-extensions', version: '2.2.0'
        customObserver = CustomObserver()
        /**
         * AppCompatActivity和androidx.fragment.app.Fragment继承了LifecycleOwner接口
         */
        lifecycle.addObserver(MyObserver(lifecycle))


        sp = getPreferences(Context.MODE_PRIVATE)
        val countReserved = sp.getInt("count", 0)
        /**
         * 独立创建，单例，数据不丢失
         */
        viewModel = ViewModelProvider(this, MainViewModelFatory(countReserved)).get(MainViewModel::class.java)
        btn.setOnClickListener {
            //直接调用
//            viewModel.counter++
            //如果add函数是线程异步 ？   禁止把activity传到 viewModel中去
            viewModel.add()
            refreshCounter()
        }
        refreshCounter()


        //=================分割线=============或者使用livedata 改写================== 观察者模式， 数据改变时，会回调下面方法
        btn.setOnClickListener {
            viewModel.add()
        }
        /**
         * 此处 LifecycleOwner和Observer都是单抽象接口参数， 要么同时使用函数式API，要么都不使用。 第一个是this，第二个即无法使用函数式API
         */
        viewModel._counter.observe(this, Observer {
            info.text = it.toString()
        })
        //如果要使用函数式API写法， 则引入lifecycle-livedata-ktx
        viewModel._counter.observe(this){
            info.text = it.toString()
        }

        //=================================分割线=============================
        val viewModel2 = MainViewModel2()
        //如果Repository 不调用wsitchMap 每次调用getUser，都返回一个新的LiveData
        viewModel2.getUser("xxx").observe(this){
            //xxxx
        }
        //改正----方法触发和监听分开
        viewModel2.getUser2("xxx")
        viewModel2.user.observe(this){
            //xxxx
        }


    }

    private fun refreshCounter(){
        info.text = viewModel._counter.toString()
    }

    override fun onPause() {
        super.onPause()
        sp.edit {
            putInt("count", viewModel._counter.value ?: 0)
        }
    }


    override fun onStart() {
        super.onStart()
        //自定义
        customObserver.activityStart()
    }
}