package org.fw.weather.logic.network

import org.fw.weather.logic.network.api.PlaceService
import org.fw.weather.logic.network.api.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/5 22:00
 *    desc   :
 *    version: 1.0
 */

object WeatherNetwork {
    private val placeService = ServiceCreator.create<PlaceService>()
    private val weatherService = ServiceCreator.create<WeatherService>()

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()
    //天气
    suspend fun getDailyWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat).await()
    suspend fun getRealtimeWeather(lng: String, lat: String) = weatherService.getRealtimeWeather(lng, lat).await()


    private suspend fun <T> Call<T>.await(): T{
        return suspendCoroutine {
            enqueue(object : Callback<T>{
                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if(body != null){
                        it.resume(body)
                    }else{
                        it.resumeWithException(RuntimeException("response body is null"))
                    }
                }

            })
        }
    }


}