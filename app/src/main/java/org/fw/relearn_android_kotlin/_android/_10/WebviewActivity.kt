package org.fw.relearn_android_kotlin._android._10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_webview.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.fw.relearn_android_kotlin.R
import org.fw.relearn_android_kotlin.util.LogShow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.CacheResponse
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class WebviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        webview.settings.javaScriptEnabled = true
//        webview.settings.userAgentString = "User-Agent:Android"
//        webview.settings.allowFileAccess = true;
//        webview.settings.allowContentAccess = true;
        webview.webViewClient = WebViewClient()
        webview.loadUrl("https://www.baidu.com")

//        request()
        sendRequestWithOkhttp()

        HttpUtil.toHttpRequest("https://www.baidu.com", object : HttpCallbackListener{
            override fun onFinish(response: String) {
                response.LogShow()
            }

            override fun onError(e: Exception) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun retrofitHttp(){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://128.0.0.1")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(AppService::class.java)

        val appService2 = ServiceCreator.create(AppService::class.java)
        //泛型实化
        val appService3 = ServiceCreator.create2<AppService>()

        var appData = appService.getAppData()
        appData.enqueue(object :Callback<List<App>>{
            override fun onFailure(call: Call<List<App>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<List<App>>, response: Response<List<App>>) {
                response.body().toString().LogShow()
            }

        })
    }



    private fun sendRequestWithOkhttp(){
        thread {
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url("https://www.baidu.com")
                    .build()
                val response = client.newCall(request).execute()
                val s = response.body?.string()

                runOnUiThread {
                    text.text = "=== $s"
                }
            } catch (e: Exception) {
            } finally {
            }
        }
    }

    private fun request(){
        thread {
            val response = StringBuffer()
            var connection: HttpURLConnection? = null
            try {
                val url = URL("https://www.baidu.com")
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                var inputStream = connection.inputStream
                var bufferedReader = BufferedReader(InputStreamReader(inputStream))
                bufferedReader.use {
                    it.forEachLine {it2->
                        response.append(it2)
                    }
                }
                runOnUiThread {
                    text.text = response
                }
            } catch (e: Exception) {
            } finally {
                connection?.disconnect()
            }

        }
    }
}
//========================================分割线============================================retrofit封装
/**
 *
 */
object ServiceCreator{
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://128.0.0.1")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
//    val appService = retrofit.create(AppService::class.java)
    fun <T> create(serviceClass: Class<T>): T= retrofit.create(serviceClass)
    /**
     * 改进  -- 泛型实化  ， 使用T::class.java 这种语法
     */
    inline fun <reified T> create2(): T= retrofit.create(T::class.java)

}


object HttpUtil{
    fun toHttpRequest(url: String, listener: HttpCallbackListener){
        thread {
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url("https://www.baidu.com")
                    .build()
                val response = client.newCall(request).execute()
                val s = response.body?.string()

                s?.let { listener.onFinish(it) }
            } catch (e: Exception) {
                listener.onError(e)
            } finally {
            }
        }
    }
}
interface HttpCallbackListener{
    fun onFinish(response: String)
    fun onError(e: Exception)
}