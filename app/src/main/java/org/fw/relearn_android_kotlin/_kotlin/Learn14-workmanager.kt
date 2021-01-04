package org.fw.relearn_android_kotlin._kotlin

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.work.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.parcel.Parcelize
import org.fw.relearn_android_kotlin.BaseActivity
import org.fw.relearn_android_kotlin.MyApp
import org.fw.relearn_android_kotlin.util.LogShow
import java.io.Serializable
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit
import kotlin.math.max

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/4 23:30
 *    desc   :
 *
 *    1 写法固定， 每个后台任务继承Worker类， dowork中处理后台逻辑, 不会运行在主线程中，可以执行延时操作
 *    2 Result.success()/ Result.failure()/ Result.retry()
 *
 *    3 workmanager 在定制手机系统中，容易被一键杀死， 非白名单应用 （无法接收广播，无法运行workmanager的后台任务），  因此不要用于开发核心功能
 *
 *
 *    version: 1.0
 */

class Simpleworker(context: Context, params: WorkerParameters): Worker(context, params){
    override fun doWork(): Result {
        "do work in this ....".LogShow()
        return Result.success()
    }

}

fun test14(activity: AppCompatActivity){
    //构建
    val request = OneTimeWorkRequest.Builder(Simpleworker::class.java).build()
    //周期性  不能低于15分钟
    val request2 = PeriodicWorkRequest.Builder(Simpleworker::class.java, 15, TimeUnit.MINUTES).build()

    WorkManager.getInstance(MyApp.app).enqueue(request)

    //==============================分割线====================================================
    //让后台任务在指定的延迟时间后运行
    val request3 = OneTimeWorkRequest.Builder(Simpleworker::class.java)
        .setInitialDelay(5, TimeUnit.MINUTES) //5分钟后运行
        .addTag("request3") //加标签 --> 用于取消任务
        .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS)//延迟重试  最短不少于10秒钟     LINEAR：线性延迟，  EXPONENTIAL：指数延迟
        .build()

    WorkManager.getInstance(MyApp.app).cancelAllWorkByTag("request3") //根据标签取消
    //==============================分割线====================================================
    /**
     * 监听 Result.success()/ Result.failure()
     */
    WorkManager.getInstance(MyApp.app).getWorkInfoByIdLiveData(request.id)
        .observe(activity){
          if(it.state == WorkInfo.State.SUCCEEDED){
              "SUCCEEDED".LogShow()
          }else if(it.state == WorkInfo.State.FAILED){
              "FAILED".LogShow()
          }
        }

    //==============================分割线====================================================
    /**
     * 1 多个独立任务： 先同步，再压缩，再上传
     * 2 因此执行， 失败停止
     */
    val req1 = OneTimeWorkRequest.Builder(Simpleworker::class.java).build()
    val req2 = OneTimeWorkRequest.Builder(Simpleworker::class.java).build()
    val req3 = OneTimeWorkRequest.Builder(Simpleworker::class.java).build()
    WorkManager.getInstance(MyApp.app)
        .beginWith(req1)
        .then(req2)
        .then(req3)
        .enqueue()
}


fun main() {


}

