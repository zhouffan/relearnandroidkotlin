package org.fw.weather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import org.fw.weather.logic.Repository
import org.fw.weather.logic.model.PlaceResponse
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ln

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/5 23:53
 *    desc   :
 *    version: 1.0
 */
class WeatherViewModel: ViewModel() {
    private val locationLiveData = MutableLiveData<PlaceResponse.Location>()

    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    /**
     * 1 调用searchPlaces时，switchMap函数就会触发执行
     *
     */
    val weatherLiveData = Transformations.switchMap(locationLiveData){
        Repository.refreshWeather(it.lng, it.lat)
    }

    fun searchPlaces(lng: String, lat: String){
        locationLiveData.value = PlaceResponse.Location(lng, lat)
    }
}