package org.fw.relearn_android_kotlin._android._03

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import org.fw.relearn_android_kotlin.R
import org.fw.relearn_android_kotlin._kotlin.testRoot

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/24 21:39
 *    desc   :
 *    1 singletop/singletask/singleInstance     singleInstance:应用a页面1-->应用b页面1-->应用a页面2。  此时按返回键：从应用a页面2会返回到应用a页面1
 *    2 javaClass获取当前实例的class对象
 *
 *    version: 1.0
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        print("xxxx")
        testRoot()
        Log.i("=====>", "" + javaClass.simpleName)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("title")
                setMessage("message")
                setCancelable(false)
                setPositiveButton("ok"){
                    dialog, which ->
                    run {
                        Toast.makeText(this@MainActivity, dialog.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                setNegativeButton("cancel"){
                    dialog, which ->
                    run { Toast.makeText(this@MainActivity, ""+which, Toast.LENGTH_SHORT).show() }
                }
                show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_item -> Toast.makeText(this, "add ", Toast.LENGTH_SHORT).show()
            R.id.toView -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://www.baidu.com")
                startActivity(intent)
            }
            R.id.ToActivity -> {
                val intent = Intent(this, ListViewActivity::class.java)
                intent.putExtra("key", "xxxxxxx")
                startActivityForResult(intent, 1)

                ListViewActivity.actionStart(this, null)
            }
            R.id.toDial -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:10086")
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1 -> if (resultCode == Activity.RESULT_OK){
                var ret = data?.extras?.getString("key_return")
                Toast.makeText(this, ret, Toast.LENGTH_SHORT).show()
            }
        }
    }
}