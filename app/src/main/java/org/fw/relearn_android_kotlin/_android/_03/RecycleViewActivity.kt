package org.fw.relearn_android_kotlin._android._03

import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_recycle_view.*
import org.fw.relearn_android_kotlin.BaseActivity
import org.fw.relearn_android_kotlin.R
import org.fw.relearn_android_kotlin._android._03.adapter.RVFruitAdapter
import org.fw.relearn_android_kotlin._android._03.entity.Fruit
import java.lang.StringBuilder

class RecycleViewActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)

        val fruits = ArrayList<Fruit>()
        repeat(15){
            fruits.add(Fruit(getRandomStr("title1"), R.drawable.ic_launcher_foreground))
            fruits.add(Fruit(getRandomStr("title2"), R.drawable.ic_launcher_foreground))
            fruits.add(Fruit(getRandomStr("title3"), R.drawable.ic_launcher_foreground))
            fruits.add(Fruit(getRandomStr("title4"), R.drawable.ic_launcher_foreground))
            fruits.add(Fruit(getRandomStr("title5"), R.drawable.ic_launcher_foreground))
        }

        recycleview.adapter = RVFruitAdapter(fruits)
        /**
         * 1 ListView的布局排列是由自身管理的。recycleview是由 LayoutManager管理的
         * 2 LinearLayoutManager第三个参数: 是否反转(不反转的垂直布局:数据从上到下加载，加载新数据，新数据在底部，滑动从下往上)
         *
         *
         */
//        recycleview.layoutManager = LinearLayoutManager(this)
//        recycleview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycleview.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun getRandomStr(string: String): String{
        val n = (1..20).random()
        val sb = StringBuilder()
        repeat(n){
            sb.append(string)
        }
        return sb.toString()
    }
}