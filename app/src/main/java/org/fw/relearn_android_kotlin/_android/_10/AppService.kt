package org.fw.relearn_android_kotlin._android._10

import android.graphics.pdf.PdfDocument
import retrofit2.Call
import retrofit2.http.*

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/2 22:16
 *    desc   :
 *    version: 1.0
 */
interface AppService {
    @GET("get_data")
    fun getAppData(): Call<List<App>>

    @GET("{page}/get2")
    fun get1(@Path("page") page: String): Call<App>

    @DELETE("get3")
    fun get3(@Query("page") page: String, @Query("size") size: String): Call<App>

    @Headers("User-Agent:okhttp", "Cache-Control:max-age=0")
    @POST("get4")
    fun get4(@Body page: App, @Header("User-Agent") userAgent: String): Call<App>
}