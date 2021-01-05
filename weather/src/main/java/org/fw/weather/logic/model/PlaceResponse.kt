package org.fw.weather.logic.model

import com.google.gson.annotations.SerializedName

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/5 21:50
 *    desc   :
 *    version: 1.0
 */

data class PlaceResponse (val status: String, val places: List<Place>){
    data class Place(val name: String, val location: Location, @SerializedName("formatted_address")val address: String){

    }
    data class Location(val lng: String, val lat: String){

    }
}

