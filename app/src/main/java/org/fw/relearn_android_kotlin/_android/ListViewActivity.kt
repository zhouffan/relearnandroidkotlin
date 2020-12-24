package org.fw.relearn_android_kotlin._android

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import org.fw.relearn_android_kotlin.R
import org.fw.relearn_android_kotlin._android.adapter.FruitAdapter
import org.fw.relearn_android_kotlin._android.entity.Fruit
import org.fw.relearn_android_kotlin.util.LogUtil

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/24 21:52
 *    desc   :
 *    version: 1.0
 */
class ListViewActivity : AppCompatActivity() {
    /**
     * 单例类启动本页面
     */
    companion object{
        fun actionStart(context: Context, bundle: Bundle?){
            val intent = Intent(context, ListViewActivity::class.java)
            bundle?.let { intent.putExtras(it) }
            context.startActivity(intent)
        }
    }

    val data = listOf("aaaa","bbbbb","cccc","aaaa","bbbbb","cccc","aaaa","bbbbb","cccc","aaaa","bbbbb","cccc","aaaa","bbbbb","cccc","aaaa","bbbbb","cccc","aaaa","bbbbb","cccc","aaaa","bbbbb","cccc")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var key = intent.extras?.getString("key")
        var btn = findViewById<Button>(R.id.btn)
        btn.text = key
        btn.setOnClickListener {
            val intent = Intent()
            intent.putExtra("key_return", "aaaaa")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        val listView = findViewById<ListView>(R.id.listview)

        //自带样式
//        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)

        val fruits = ArrayList<Fruit>()
        repeat(5){
            fruits.add(Fruit("title1", R.drawable.ic_launcher_foreground))
            fruits.add(Fruit("title2", R.drawable.ic_launcher_foreground))
            fruits.add(Fruit("title3", R.drawable.ic_launcher_foreground))
            fruits.add(Fruit("title4", R.drawable.ic_launcher_foreground))
            fruits.add(Fruit("title5", R.drawable.ic_launcher_foreground))
        }
        listView.adapter = FruitAdapter(this, R.layout.fruit_item, fruits)
        listView.setOnItemClickListener { parent, view, position, id ->
            LogUtil.log(""+position)
            val fruit = fruits[position]
            Toast.makeText(this, fruit.title, Toast.LENGTH_SHORT).show()
        }
    }
}