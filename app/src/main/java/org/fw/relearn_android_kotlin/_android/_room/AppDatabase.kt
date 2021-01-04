package org.fw.relearn_android_kotlin._android._room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.fw.relearn_android_kotlin._android._room.upgrade.Book
import org.fw.relearn_android_kotlin._android._room.upgrade.BookDao

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/4 21:37
 *    desc   :
 *
 *    1 包含版本号/ 实体类
 *    2 提供Dao层的访问实例
 *
 *    version: 1.0
 */

//@Database(version = 1, entities = [Student::class])
//abstract class AppDatabase: RoomDatabase(){
//    abstract fun studentDao(): StudentDao
//
//    companion object{
//        private var instance: AppDatabase? = null
//
//        @Synchronized
//        fun getDatabase(context: Context): AppDatabase{
//            instance?.let {
//                return it
//            }
//            return databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database")
////                .allowMainThreadQueries()//仅仅在测试中使用，可以在主线程中使用
////                .fallbackToDestructiveMigration()//升级时会将当前数据库销毁，再重新创建
//                .build().apply {
//                    instance = this
//                }
//        }
//    }
//
//}

/**
 * 数据库升级，增加Book表， 从1 --> 2
 *
 */
//@Database(version = 2, entities = [Student::class, Book::class])
//abstract class AppDatabase: RoomDatabase(){
//    abstract fun studentDao(): StudentDao
//    abstract fun bookDao(): BookDao
//
//    companion object{
//        /**
//         * 新增表 Book
//         */
//        val migration_1_2 = object : Migration(1, 2){
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("create table Book(id integer primary key autoincrement not null, name text not null, pages integer not null)")
//            }
//
//        }
//
//        private var instance: AppDatabase? = null
//
//        @Synchronized
//        fun getDatabase(context: Context): AppDatabase{
//            instance?.let {
//                return it
//            }
//            return databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database")
////                .allowMainThreadQueries()//仅仅在测试中使用，可以在主线程中使用
////                .fallbackToDestructiveMigration()//升级时会将当前数据库销毁，再重新创建
//                .addMigrations(migration_1_2)
//                .build().apply {
//                    instance = this
//                }
//        }
//    }
//
//}

/**
 * 对Book 表新增一个字段  2 --> 3
 *
 */
@Database(version = 3, entities = [Student::class, Book::class])
abstract class AppDatabase: RoomDatabase(){
    abstract fun studentDao(): StudentDao
    abstract fun bookDao(): BookDao

    companion object{
        /**
         * 新增表 Book
         */
        private val migration_1_2 = object : Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("create table Book(id integer primary key autoincrement not null, name text not null, pages integer not null)")
            }

        }

        /**
         * book 表新增字段 author
         */
        private val migration_2_3 = object : Migration(2, 3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table Book add column author text not null default 'unknown'")
            }

        }

        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase{
            instance?.let {
                return it
            }
            return databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database")
//                .allowMainThreadQueries()//仅仅在测试中使用，可以在主线程中使用
//                .fallbackToDestructiveMigration()//升级时会将当前数据库销毁，再重新创建
                .addMigrations(migration_1_2, migration_2_3)
                .build().apply {
                    instance = this
                }
        }
    }

}