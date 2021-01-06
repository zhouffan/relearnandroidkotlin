package org.fw.weather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import org.fw.weather.MyApp
import org.fw.weather.logic.model.PlaceResponse

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/6 22:04
 *    desc   :
 *    version: 1.0
 */
object PlaceDao {
    fun savePlace(place: PlaceResponse.Place){
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): PlaceResponse.Place{
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, PlaceResponse.Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = MyApp.instance.getSharedPreferences("weather", Context.MODE_PRIVATE)
}