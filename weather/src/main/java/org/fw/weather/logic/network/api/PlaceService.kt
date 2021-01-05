package org.fw.weather.logic.network.api

import org.fw.weather.MyApp
import org.fw.weather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/5 21:54
 *    desc   :
 *    version: 1.0
 */
interface PlaceService {
    @GET("v2/place?token=${MyApp.token}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}