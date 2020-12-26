package org.fw.relearn_android_kotlin._android._03.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import org.fw.relearn_android_kotlin.R
import org.fw.relearn_android_kotlin._android._03.entity.Fruit
import org.fw.relearn_android_kotlin.util.LogUtil

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/24 23:06
 *    desc   :
 *    version: 1.0
 */
class FruitAdapter(activity: Activity, val resourceId: Int, val data: List<Fruit>) : ArrayAdapter<Fruit>(activity, resourceId, data) {

    /**
     * listview 复用item
     * 1 优化一：每次都将布局重新加载了一遍， 滚动时则有性能问题。 convertView == null判断
     * 2 优化二：每次getview()会调用findViewById获取控件实例。  新增ViewHolder
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        LogUtil.log("getView  position:$position")
        val view: View
        val viewHolder: ViewHolder
        //优化 一
//        val view = convertView ?: LayoutInflater.from(context).inflate(resourceId, parent, false)
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(resourceId, parent, false)
            var imageView = view.findViewById<ImageView>(R.id.imageView)
            var textView = view.findViewById<TextView>(R.id.textView)
            viewHolder = ViewHolder(imageView, textView)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }


        val fruit = getItem(position)
        fruit?.let {
            viewHolder.imageView.setImageResource(fruit.img)
            viewHolder.textView.text = fruit.title
        }
        return view
    }

    inner class ViewHolder(val imageView: ImageView, val textView: TextView)
}