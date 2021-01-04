package org.fw.relearn_android_kotlin._android._room.upgrade

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/4 22:42
 *    desc   :
 *
 *    1 新增对应dao
 *
 *    version: 1.0
 */

@Dao
interface BookDao {
    @Insert
    fun insert(book: Book): Long

    @Query("select * from book")
    fun loadAll():List<Book>
}