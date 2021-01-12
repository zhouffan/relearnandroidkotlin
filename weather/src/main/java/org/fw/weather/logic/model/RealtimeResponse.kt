package org.fw.weather.logic.model

import com.google.gson.annotations.SerializedName

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/5 23:09
 *    desc   :
 *    version: 1.0
 */
data class RealtimeResponse(val status: String, val result: Result) {

    /**
     * 内部数据模型，  可以避免与外部冲突
     */
    data class Result(val realtime: Realtime)
    data class Realtime(val skycon: String, val temperature: Float, @SerializedName("air_quality")val airQuality: AirQuality)
    data class AirQuality(val aqi: AQI)
    data class AQI(val chn: Float)
}