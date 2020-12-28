package org.fw.relearn_android_kotlin._android._06

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import org.fw.relearn_android_kotlin.util.LogShow
import org.fw.relearn_android_kotlin.util.showToast

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/28 22:40
 *    desc   :
 *    version: 1.0
 */
class MyDatabaseHelper(val context: Context, name:String, version: Int) :
    SQLiteOpenHelper(context, name, null, version){

    private val createBoot = "create table Book(id integer primary key autoincrement, " +
            "author text, price real, pages integer, name text" +
            "category_id integer)"  //版本3升级一个 关联字段

    private val createCategory = "create table Category(id integer primary key autoincrement, " +
            "category_name text, category_code integer)"

    override fun onCreate(db: SQLiteDatabase) {
        "onCreate".LogShow()
        db.execSQL(createBoot)
        db.execSQL(createCategory)
        "successed".showToast()
    }

    /**
     * version + 1 才触发该方法调用
     *
     * 每升级一次数据库， onUpgrade必须要写一个if(oldVersion) 判断。 兼容1-->3  2-->3
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        "onUpgrade".LogShow()
//        db.execSQL("drop table if exists Book")
//        db.execSQL("drop table if exists Category")
//        onCreate(db)
        if(oldVersion <= 1){
            //...
        }
        if (oldVersion <= 2){
            db.execSQL("alter table Book add column category_id integer")
        }
    }

    /**
     *
     *
     */

}