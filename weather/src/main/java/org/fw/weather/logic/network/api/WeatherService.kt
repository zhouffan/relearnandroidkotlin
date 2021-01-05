package org.fw.weather.logic.network.api

import org.fw.weather.MyApp
import org.fw.weather.logic.model.DailyResponse
import org.fw.weather.logic.model.PlaceResponse
import org.fw.weather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/5 21:54
 *    desc   :
 *    version: 1.0
 */
interface WeatherService {
    @GET("v2.5/${MyApp.token}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng")lng: String, @Path("lat")lat: String): Call<RealtimeResponse>

    @GET("v2.5/${MyApp.token}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng")lng: String, @Path("lat")lat: String): Call<DailyResponse>

}