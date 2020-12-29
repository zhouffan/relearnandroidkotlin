package org.fw.relearn_android_kotlin._android._07

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import org.fw.relearn_android_kotlin._android._06.MyDatabaseHelper

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/29 22:07
 *    desc   :
 *    version: 1.0
 */
class MyProvider : ContentProvider(){
    private val table1Dir = 0  //table1所有
    private val table1Item = 1 //table1单个
    private val table2Dir = 2  //table2所有
    private val table2Item = 3 //table1单个
    private val authority = "com.fuwa.app.provider"
    private var dbHelper: MyDatabaseHelper? = null

    override fun onCreate() = context?.let {
        dbHelper = MyDatabaseHelper(it, "BookStore.db", 2)
        true
    }?: false
//    override fun onCreate(): Boolean {
//        dbHelper = context?.let { MyDatabaseHelper(it, "BookStore.db", 2) }
//
//    }

    /**
     * 懒加载技术
     */
    private val uriMatcher by lazy {
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        //#：任意长度数字，  *：任意长度的任意字符
        uriMatcher.addURI(authority, "book", table1Dir)
        uriMatcher.addURI(authority, "book/#", table1Item)
        uriMatcher.addURI(authority, "category", table2Dir)
        uriMatcher.addURI(authority, "category/#", table2Item)
        uriMatcher
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        dbHelper?.let {
            val db = it.writableDatabase
            return when(uriMatcher.match(uri)){
                table1Dir, table1Item ->{
                    val newBookId = db.insert("Book", null, values)
                    Uri.parse("content://$authority/book/$newBookId")
                }
                table2Dir, table2Item -> {
                    val newCategoryId = db.insert("Category", null, values)
                    Uri.parse("content://$authority/category/$newCategoryId")
                }
                else -> null
            }
        }
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        dbHelper?.let {
            val db = it.readableDatabase
            val cursor = when(uriMatcher.match(uri)){
                table1Dir -> {
                    db.query("Book", projection, selection, selectionArgs, null, null, sortOrder)
                }
                table1Item -> {
                    val bookId = uri.pathSegments[1]
                    db.query("Book", projection, "id = ?", arrayOf(bookId), null, null, sortOrder)
                }
                table2Dir -> {
                    db.query("Category", projection, selection, selectionArgs, null, null, sortOrder)
                }
                table2Item -> {
                    val categoryId = uri.pathSegments[1]
                    db.query("Category", projection, "id = ?", arrayOf(categoryId), null, null, sortOrder)
                }
                else -> null
            }
            return cursor
        }
        return null
    }


    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    /**
     * 根据uri返回MIME类型
     * 1 vnd 开头
     * 2 uri以路径结尾： android.cursor.dir/
     *   uri以id结尾 ： android.cursor.item/
     * 3 最后vnd.<authority>.<path>
     *
     */
    override fun getType(uri: Uri) = when(uriMatcher.match(uri)){
        table1Dir -> "vnd.android.cursor.dir/vnd.$authority.book"
        table1Item -> "vnd.android.cursor.item/vnd.$authority.book"
        table2Dir -> "vnd.android.cursor.dir/vnd.$authority.category"
        table2Item -> "vnd.android.cursor.item/vnd.$authority.category"
        else -> null
    }
}