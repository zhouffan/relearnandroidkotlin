package org.fw.relearn_android_kotlin._android._room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/4 21:22
 *    desc   :
 *    version: 1.0
 */

@Entity
data class Student (val firstName: String, val lastName: String, val age: Int){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}