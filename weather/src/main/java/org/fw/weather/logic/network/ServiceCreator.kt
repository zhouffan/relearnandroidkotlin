package org.fw.weather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/5 21:56
 *    desc   :
 *    version: 1.0
 */

object ServiceCreator {
    private const val BASE_URL= "https://xxxxx"

    private val retorfit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retorfit.create(serviceClass)
    //泛型实化
    inline fun <reified T> create() = create(T::class.java)

}