package org.fw.relearn_android_kotlin._android._room.upgrade

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/4 22:40
 *    desc   :
 *
 *
 *    1 需求： 新增该表
 *
 *    version: 1.0
 */
@Entity
data class Book(val name: String, val pages: Int, var author:String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}