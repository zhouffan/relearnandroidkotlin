package org.fw.weather.logic.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.fw.weather.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/5 21:56
 *    desc   :
 *    version: 1.0
 */

object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com"

    private fun getRetrofit(): Retrofit {
        //四个等级
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        
        val httpClient = OkHttpClient().newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(30 * 1000, TimeUnit.MILLISECONDS)
            .readTimeout(20 * 1000, TimeUnit.MILLISECONDS).build()

        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(httpClient)
            //增加 Gson 转换器
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> create(serviceClass: Class<T>): T = getRetrofit().create(serviceClass)

    //泛型实化
    inline fun <reified T> create() = create(T::class.java)


}