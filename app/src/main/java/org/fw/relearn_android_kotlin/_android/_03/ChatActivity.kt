package org.fw.relearn_android_kotlin._android._03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_chat.*
import org.fw.relearn_android_kotlin.R
import org.fw.relearn_android_kotlin._android._03.adapter.MsgAdapter
import org.fw.relearn_android_kotlin._android._03.entity.Message
import java.util.ArrayList

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/26 15:37
 *    desc   :
 *    version: 1.0
 */
class ChatActivity : AppCompatActivity() {
    private val messages = ArrayList<Message>()
    private lateinit var adapter: MsgAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initMsg()

        adapter = MsgAdapter(messages)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        send.setOnClickListener {
            val content = inputText.text.toString()
            if(content.isNotEmpty()){
                val msg = Message(content, Message.TYPE_SEND)
                messages.add(msg)
                /**
                 * 1 notifyDataSetChanged : 不管是新增/删除/修改，都会刷新所有可见的元素--->效率相对差一点。  相较于下面，没有插入动画等
                 * 2 notifyItemMoved:
                 * 3 notifyItemInserted/notifyItemChanged/notifyItemRemoved
                 * 4 notifyItemRangeInserted/notifyItemRangeChanged/notifyItemRangeRemoved : 从指定位置开始， 刷新指定个item
                 *
                 * notifyItemRangeRemoved（int startPosition,int itemcount） 调用后需要notifyItemRangeChanged（startPosition，allCount-startPosition）。否则出现位置异常。
                 */
                adapter.notifyItemInserted(messages.size - 1)
                recyclerView.scrollToPosition(messages.size - 1)
                inputText.setText("")
            }
        }
    }

    private fun initMsg(){
        messages.add(Message("11111", Message.TYPE_RECEIVED))
        messages.add(Message("2222", Message.TYPE_SEND))
        messages.add(Message("333", Message.TYPE_RECEIVED))
        messages.add(Message("42222", Message.TYPE_SEND))
        messages.add(Message("5333", Message.TYPE_RECEIVED))
        messages.add(Message("62222", Message.TYPE_SEND))
        messages.add(Message("7333", Message.TYPE_RECEIVED))
        messages.add(Message("82222", Message.TYPE_SEND))
        messages.add(Message("9333", Message.TYPE_RECEIVED))
    }
}