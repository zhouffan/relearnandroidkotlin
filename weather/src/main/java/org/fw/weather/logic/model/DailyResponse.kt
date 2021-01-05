package org.fw.weather.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/5 23:12
 *    desc   :
 *    version: 1.0
 */
data class DailyResponse(val status: String, val result: Result) {

    data class LifeDescription(val desc: String)
    data class LifetIndex(val coldRisk:List<LifeDescription>, val carWashing: List<LifeDescription>, val ultraviolet: List<LifeDescription>, val dressing: List<LifeDescription>)
    data class Skycon(val value: String, val date: Date)
    data class Temperature(val max: Float, val min: Float)
    data class Daily(val temperature: List<Temperature>, val skycon: List<Skycon>, @SerializedName("life_index")val lifeIndex: LifetIndex)
    data class Result(val daily: Daily)
}