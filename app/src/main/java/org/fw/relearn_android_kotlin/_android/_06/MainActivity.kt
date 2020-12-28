package org.fw.relearn_android_kotlin._android._06

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main3.*
import org.fw.relearn_android_kotlin.R
import org.fw.relearn_android_kotlin.util.LogShow
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    lateinit var db: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        var load = load()
        if (load.isNotEmpty()){
            et_text.setText(load)
            et_text.setSelection(load.length)
        }

        btn.setOnClickListener {
            saveToSp()
        }

        //创建数据库
        var dbHelper = MyDatabaseHelper(this, "BookStore.db", 3)
        db = dbHelper.writableDatabase
        createBtn.setOnClickListener {
            dbHelper.writableDatabase
        }
        add.setOnClickListener {
            addData()
        }
        delete.setOnClickListener {
            deleteData()
        }
        search.setOnClickListener {
            searchData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        save(et_text)
    }

    /**
     * SharedPreference
     *
     */
    private fun saveToSp(){
        var editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
        editor.putString("key", et_text.text.toString())
        editor.apply()
    }

    /**
     * 文件读取
     *
     */
    private fun save(etText: EditText) {
        try {
            val openFileOutput = openFileOutput("data", Context.MODE_PRIVATE)
            val bufferedWriter = BufferedWriter(OutputStreamWriter(openFileOutput))
            //use 内置扩展函数， 执行完自动关闭流
            bufferedWriter.use {
                it.write(etText.text.toString())
            }
        } catch (e: Exception) {
        }
    }

    private fun load():String{
        val sb = StringBuilder()
        var openFileInput = openFileInput("data")
        var bufferedReader = BufferedReader(InputStreamReader(openFileInput))
        bufferedReader.use {
            bufferedReader.forEachLine {
                sb.append(it)
            }
        }

        return sb.toString()
    }


    /**
     * 增加
     */
    private fun addData(){
        repeat(10){
            val value = ContentValues().apply {
                put("name", "zzz-$it")
                put("author", "zzz2-$it")
                put("pages", 123+it)
                put("price", 44.5+it)
            }
            db.insert("Book", null, value)
        }

    }

    private fun deleteData(){
        db.delete("Book", "id > ?", arrayOf("18"))
    }

    private fun searchData(){
        val cursor = db.query("Book", null, null, null, null, null, null)
        if(cursor.moveToFirst()){
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val author = cursor.getString(cursor.getColumnIndex("author"))
                val pages = cursor.getInt(cursor.getColumnIndex("pages"))
                val price = cursor.getDouble(cursor.getColumnIndex("price"))

                "$id  $name  $author  $pages  $price".LogShow()
            }while (cursor.moveToNext())
        }
    }
}