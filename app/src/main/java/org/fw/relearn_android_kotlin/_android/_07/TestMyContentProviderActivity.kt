package org.fw.relearn_android_kotlin._android._07

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.contentValuesOf
import kotlinx.android.synthetic.main.activity_test_my_content_provider.*
import org.fw.relearn_android_kotlin.R
import org.fw.relearn_android_kotlin.util.LogShow

class TestMyContentProviderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_my_content_provider)

        queryDataBtn.setOnClickListener {
            val uri = Uri.parse("content://com.fuwa.app.provider/book")
            contentResolver.query(uri, null, null, null, null)?.apply {
                while (moveToNext()){
                    val name = getString(getColumnIndex("name"))
                    val author = getString(getColumnIndex("author"))
                    "$name   $author".LogShow()
                }
            }
        }

        addDataBtn.setOnClickListener {
            val uri = Uri.parse("content://com.fuwa.app.provider/book")
//            author text, price real, pages integer, name text
            repeat(10){
                val values = contentValuesOf(
                    "name" to "zzz$it", "author" to "zhoudafu$it", "pages" to 11+it, "price" to 22.2)
                val newUri = contentResolver.insert(uri, values)
                val bookId = newUri?.pathSegments?.get(1)
                "bookId:$bookId".LogShow()
            }
        }
    }
}