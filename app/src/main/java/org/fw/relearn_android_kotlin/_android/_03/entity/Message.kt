package org.fw.relearn_android_kotlin._android._03.entity

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/26 15:02
 *    desc   : 消息实体类
 *    version: 1.0
 */
class Message(val content: String, val type: Int) {

    companion object{
        const val TYPE_RECEIVED = 0
        const val TYPE_SEND = 1

    }
}