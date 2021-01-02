package org.fw.relearn_android_kotlin._kotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.UriMatcher
import android.util.Log
import androidx.core.content.edit
import com.google.android.material.chip.ChipDrawable
import kotlinx.coroutines.*
import org.fw.relearn_android_kotlin._android._09.ServiceActivity
import org.fw.relearn_android_kotlin._android._10.*
import org.fw.relearn_android_kotlin.util.LogShow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/2 22:39
 *    desc   :
 *
 *    1 协程： 轻量级线程 （原始线程需要依靠操作系统 调度 完成线程之间的切换），kotlin在编程语言层面实现不同协程之间的切换
 *    2 切换： 挂起，转而执行其他线程
 *    3 作用： 在单线程中模拟多线程效果，   挂起/恢复，由编程语言控制，和操作系统无关
 *            eg: 开启10万个线程， kotlin运行效率优 （不需要系统参与，所以效率很高）
 *
 *
 *    version: 1.0
 */


//========================================分割线============================================例二
fun method1(){
    GlobalScope.launch {
        println("1111111111")
        /**
         * 1 delay函数是一个非阻塞式的挂起函数。只在在 协程作用域 和 其他挂起函数中 调用。
         * 2 sleep函数会阻塞当前线程，及当前线程的所有协程也会被阻塞。
         *
         */
        delay(2000)
        println("2222222222")
    }
    Thread.sleep(1000)
}
fun method2(){
    /**
     * 1 测试环境下使用
     */
    runBlocking {
        println("1- 1111111111")
        delay(2000)
        println("1- 2222222222")
    }
}
fun method3(){
    runBlocking {
        /**
         * 1 launch 不同于 GlobalScope.launch（与线程类似，永远是顶层协程）
         * 2 在协程的作用域中才能调用
         * 3 在当前协程的作用域下创建子协程（子协程特点：如果外层作用域的协程结束，该作用域的 所有子协程一同结束）
         *
         */
        launch {
            println("1- 1111111111")
            delay(1000)
            println("1- 2222222222")
        }
        launch {
            println("2- 1111111111")
            delay(1000)
            println("2- 2222222222")
        }
    }
}

fun method4(){
    runBlocking {
        val start = System.currentTimeMillis()
        repeat(10000){
            println("......")
        }
        val end = System.currentTimeMillis()
        println(end - start)

        delay(1000)

        printDot()
    }
}
suspend fun printDot(){
    val start = System.currentTimeMillis()

    val end = System.currentTimeMillis()
    println(end - start)
    delay(1000)

    coroutineScope {
        println("111")
        delay(1000)
        println("222")
    }
    /**
     * 被挂起，等待子协程执行完毕
     */
    println("end")
}
/**
 * 1 没有协程作用域
 * 2 suspend关键字，将函数声明成挂起函数， 挂起函数可以相互调用
 * 3 无法提供协程作用域， 无法调用launch函数
 *   解决：借助coroutineScope函数，是一个挂起函数，在其他任何挂起函数中可以调用。 特点：继承外部协程的作用域并创建一个子协程
 *
 * 4 coroutineScope函数(将外部协程挂起了) 与 runBlocking函数类似， 保证作用域内所有代码和子协程执行完毕前，外部协程一直挂起
 *   解释： coroutineScope函数：只会阻塞当前协程，即不影响其他协程，也不影响线程。  ----性能无影响
 *         runBlocking函数： 会挂起外部线程 （如果在主线程中使用，则会页面卡死）   ----不推荐
 *
 * 5 GlobalScope.launch / runBlocking 可以在任何地方调用  （GlobalScope.launch创建的是顶层协程--取消线程时不好维护（activit中取消请求，要遍历所有），runBlocking阻塞线程。 因此都不建议使用）
 *   coroutineScope函数 只能在 协程作用域 和挂起函数中 调用
 *   launch函数         只能在 协程作用域 中调用
 *
 * 6 async函数：在协程作用域中调用，会创建一个新的子协程并返回Deferred对象， 调用该对象的await()方法可返回执行结果。 一直会阻塞协程，指导返回结果:method7()
 *
 * 7 withContext()函数： 挂起函数， 可以理解成async函数的简化版
 *
 */
//实际项目中使用协程
fun method5(){
    val job = Job()
    val scope = CoroutineScope(job) //此为函数
    scope.launch {
        print("xx1")
    }
    scope.launch {
        print("xx2")
    }
    //同一作用域的协程 取消 所有
    job.cancel()
}

/**
 * await() 函数一直会阻塞
 *
 */
fun method6(){
    runBlocking {
        val result = async {
            5 + 5
        }.await()
        println(result)
    }
}

/**
 * await()阻塞后， 会依次执行。   效率低下
 */
fun method7(){
    runBlocking {
        val start = System.currentTimeMillis()
        val result1 = async {
            println("========1")
            delay(1000)
            5 + 5
        }.await()
        println("========2")
        val result2 = async {
            println("========3")
            delay(1000)
            3 + 5
        }.await()
        val end = System.currentTimeMillis()
        println("===> ${end - start}")
        println("===> $result1  $result2")
    }
}

/**
 * 改进 ----  await()写到调用时
 */
fun method8(){
    runBlocking {
        val start = System.currentTimeMillis()
        val result1 = async {
            println("========1")
            delay(1000)
            5 + 5
        }
        println("========2")
        val result2 = async {
            println("========3")
            delay(1000)
            3 + 5
        }
        val end = System.currentTimeMillis()
        println("===> ${end - start}")
        println("===> ${result1.await()}  ${result2.await()}")
    }
}

/**
 * 1 withContext()函数：作用域构建器， 挂起函数， 可以理解成async函数的简化版
 * 2 执行时，将外部协程挂起
 * 3 Dispatchers.Default: 默认低并发
 *   Dispatchers.IO：     高并发       ---执行的代码大多数在阻塞和等待中
 *   Dispatchers.Main：   不会开启子线程---只能在Android主线程中使用
 * 4 除 coroutineScope函数  其他都可以指定该值
 *
 */
fun method9(){
    runBlocking {
        val res = withContext(Dispatchers.Main){
            delay(1000)
            5 + 5
        }
        println(res)
    }
}

//=============应用=====================================================suspendCoroutine:在协程作用域或者挂起函数中调用
fun method10(){
    HttpUtil.toHttpRequest("", object : HttpCallbackListener{
        override fun onFinish(response: String) {
            TODO("Not yet implemented")
        }
        override fun onError(e: Exception) {
            TODO("Not yet implemented")
        }
    })

    //使用改写
    runBlocking {
        try {
            val response = request("http://wwwwww")
        } catch (e: Exception) {
            //
        }
    }
}
//封装
suspend fun request(url: String): String{
    /**
     * 1 request是一个挂起函数
     * 2 suspendCoroutine函数 会被立刻挂起，Lambda代码会在普通线程中执行
     * 3 resume恢复被挂起的协程
     * 4 结合协程使用
     *
     * 整个过程在子线程中执行
     *
     */
    return suspendCoroutine{
        continuation -> HttpUtil.toHttpRequest(url, object : HttpCallbackListener{
        override fun onFinish(response: String) {
            continuation.resume(response)
        }

        override fun onError(e: Exception) {
            continuation.resumeWithException(e)
        }

    })
    }
}
//=============应用==========
suspend fun method11(){
    val appService = ServiceCreator.create2<AppService>()
    appService.getAppData().enqueue(object : Callback<List<App>>{
        override fun onFailure(call: Call<List<App>>, t: Throwable) {
            TODO("Not yet implemented")
        }

        override fun onResponse(call: Call<List<App>>, response: Response<List<App>>) {
            TODO("Not yet implemented")
        }
    })

    //使用改进的
    try {
        val resp = appService.getAppData().await()
    } catch (e: Exception) {
    }
}
suspend fun <T> Call<T>.await():T{
    return suspendCoroutine {
        //Call的上下文
        enqueue(object : Callback<T>{
            override fun onFailure(call: Call<T>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                if (body != null) {
                    it.resume(body)
                }
            }

        })
    }
}


fun main() {
    method9()

}