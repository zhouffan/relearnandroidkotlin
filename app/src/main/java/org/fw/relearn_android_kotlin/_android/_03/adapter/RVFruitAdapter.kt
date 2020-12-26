package org.fw.relearn_android_kotlin._android._03.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fruit_item.view.*
import org.fw.relearn_android_kotlin.R
import org.fw.relearn_android_kotlin._android._03.entity.Fruit
import org.fw.relearn_android_kotlin.util.LogUtil

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/26 11:30
 *    desc   :
 *    version: 1.0
 */
class RVFruitAdapter(private val fruits: List<Fruit>) : RecyclerView.Adapter<RVFruitAdapter.ViewHolder>() {

    /**
     * RecyclerView.ViewHolder 自带有部分属性, 如：itemView
     *
     */
    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val fruitImage: ImageView = view.imageView
        val fruitText: TextView = view.textView
    }

    /**
     * 1 inflate 参数： viewgroup：加载的资源要依附的父布局； attachToRoot：资源是否要依附在父布局
     * 说明：要想使item的布局属性layout_width和layout_height生效，则必须指定父容器（一个控件在容器中的大小），对应的viewgroup指定
     *      (R.layout.fruit_item, parent, false)：表示item根节点有效，但是又不想处于某个容器中
     *
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        LogUtil.log(" ${javaClass.simpleName}   onCreateViewHolder  viewType:$viewType")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fruit_item, parent, false)
        var viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            var fruit = fruits[viewHolder.adapterPosition]
            Toast.makeText(parent.context, "===> $fruit" , Toast.LENGTH_SHORT).show()
        }
        return viewHolder
    }

    override fun getItemCount() = fruits.size

    /**
     * 1 会复用，重复刷新数据
     *
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        LogUtil.log(" ${javaClass.simpleName}   onBindViewHolder  position:$position")
        var fruit = fruits[position]
        holder.fruitImage.setImageResource(fruit.img)
        holder.fruitText.text = "$position    $fruit.title"

    }
}