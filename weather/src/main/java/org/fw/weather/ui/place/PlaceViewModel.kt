package org.fw.weather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import org.fw.weather.logic.Repository
import org.fw.weather.logic.model.PlaceResponse

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/5 22:20
 *    desc   :
 *    version: 1.0
 */
class PlaceViewModel: ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<PlaceResponse.Place>()

    /**
     * 1 调用searchPlaces时，switchMap函数就会触发执行
     *
     */
    val placeLiveData = Transformations.switchMap(searchLiveData){
        Repository.searchPlaces(it)
    }

    fun searchPlaces(query: String){
        searchLiveData.value = query
    }

}