package org.fw.weather.logic

import android.content.Context
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.fw.weather.logic.model.PlaceResponse
import org.fw.weather.logic.model.Weather
import org.fw.weather.logic.network.WeatherNetwork
import retrofit2.awaitResponse
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext
import kotlin.math.ln

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/5 22:07
 *    desc   :
 *
 *
 *    ================================== 对 Repository 继续封装 ==================================
 *
 *
 *    version: 1.0
 */

object Repository2 {
    /**
     * 1 liveData 是 lifecycle-livedata-ktx提供的功能， 自动构建并返回liveData
     * 2 它的函数中提供类一个挂起函数的上下文，可以调用其他挂起函数。 如searchPlaces
     * 3 emit 将包装结果发出去， 类似livedata的 setValue()通知数据变化
     * 4 Dispatchers.IO 子线程中
     *
     */
    fun searchPlaces(query: String) = fire(Dispatchers.IO){
        val searchPlaces = WeatherNetwork.searchPlaces(query)
        if(searchPlaces.status == "ok"){
            val places = searchPlaces.places
            Result.success(places)
        }else{
            Result.failure(RuntimeException("respose status is ${searchPlaces.status}"))
        }
    }

    /**
     * 1 coroutineScope 创建一个协程作用域  (async函数必须在协程作用域内才能调用)
     * 2 并列执行2次请求=====》协程 async函数，分别调用await()
     *
     */
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        //并列执行2次请求=====》协程 async函数，分别调用await()
        coroutineScope {
            val deferredRealtime = async {
                WeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                WeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeRes = deferredRealtime.await()
            val dailyRes = deferredDaily.await()
            if(realtimeRes.status == "ok" && dailyRes.status == "ok"){
                val weather = Weather(realtimeRes.result.realtime, dailyRes.result.daily)
                Result.success(weather)
            }else{
                Result.failure(RuntimeException("${realtimeRes.status} === ${dailyRes.status}"))
            }
        }
    }

    /**
     * 1 抽取方法
     * 2 定义高阶函数， 先调用 liveData函数，统一进行try
     * 3 liveData函数代码快中拥有 挂起函数上下文。 Lambda表达式也必须要挂起函数，因此block前新增 suspend
     *
     */
    private fun <T> fire(context: CoroutineContext, block: suspend ()->Result<T>) = liveData<Result<T>> {
        val result = try {
            block()
        }catch (e: Exception){
            Result.failure<T>(e)
        }
        emit(result)
    }

}