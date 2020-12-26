package org.fw.relearn_android_kotlin._android._03.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.msg_left_item.view.*
import kotlinx.android.synthetic.main.msg_right_item.view.*
import org.fw.relearn_android_kotlin.R
import org.fw.relearn_android_kotlin._android._03.entity.Message

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/26 15:05
 *    desc   :
 *    version: 1.0
 */
//sealed class MsgViewHolder(view: View): RecyclerView.ViewHolder(view)
//class LeftViewHolder(view: View): MsgViewHolder(view){
//    val leftMsg: TextView = view.leftMsg
//}
//
//class RightViewHolder(view: View): RecyclerView.ViewHolder(view){
//    val rightMsg: TextView = view.rightMsg
//}
class MsgAdapter(private val msgList: List<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    //使用密封类替换， 密封类及子类只能定义在同一个文件的顶层位置
    inner class LeftViewHolder(view: View): RecyclerView.ViewHolder(view){
        val leftMsg: TextView = view.leftMsg
    }

    inner class RightViewHolder(view: View): RecyclerView.ViewHolder(view){
        val rightMsg: TextView = view.rightMsg
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Message.TYPE_RECEIVED){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.msg_left_item, parent, false)
            LeftViewHolder(view)
        }else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.msg_right_item, parent, false)
            RightViewHolder(view)
        }
    }

    override fun getItemCount() = msgList.size

    override fun getItemViewType(position: Int): Int {
        val message = msgList[position]
        return message.type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = msgList[position]
        when(holder){
            is LeftViewHolder -> holder.leftMsg.text = message.content
            is RightViewHolder -> holder.rightMsg.text = message.content
        }
    }
}