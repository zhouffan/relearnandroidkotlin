package org.fw.weather.logic.model

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/5 23:17
 *    desc   :
 *    version: 1.0
 */
data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily) {
}