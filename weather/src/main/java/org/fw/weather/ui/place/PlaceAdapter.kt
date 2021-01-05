package org.fw.weather.ui.place

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.fw.weather.R
import org.fw.weather.logic.model.Place

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/5 22:35
 *    desc   :
 *    version: 1.0
 */
class PlaceAdapter(private val placeList: List<Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>(){

    inner class ViewHolder(view: View) :RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    /**
     * 1 inflate 参数： viewgroup：加载的资源要依附的父布局； attachToRoot：资源是否要依附在父布局
     * 说明：要想使item的布局属性layout_width和layout_height生效，则必须指定父容器（一个控件在容器中的大小），对应的viewgroup指定
     *      (R.layout.fruit_item, parent, false)：表示item根节点有效，但是又不想处于某个容器中
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = placeList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }


}