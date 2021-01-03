package org.fw.relearn_android_kotlin._kotlin

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import org.fw.relearn_android_kotlin.MyApp
import java.lang.RuntimeException
import kotlin.math.max

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2021/1/3 20:59
 *    desc   :
 *
 *    1 KTX库：简化许多API的用法而设计的
 *
 *
 *    version: 1.0
 */

fun method11_01(){
    val a = 10
    val b = 12
    val larger = max(a, b)

    //3个数
    val c = 30
    val larger2 = max(a, max(b, c))
    //多个数比较，封装
    var larger3 = maxNums(a, b, c)
    val a1 = 1.2
    val b1 = 2.3
    val c1 = 1.4
    var larger4 = maxNums2(a1, b1, c1)

    1.showToast(MyApp.get())

    val view: View? = null
    view?.showSnackbar("xxxxxx", "ok", Snackbar.LENGTH_LONG){
        //点击后，xxx

    }
}
//========================================分割线============================================封装====多个值比较
/**
 * 比较多个数
 */
fun maxNums(vararg nums: Int): Int{
    var maxNum = Int.MIN_VALUE
    for(num in nums){
        maxNum = max(maxNum, num)
    }
    return maxNum
}

/**
 * 数字比较，必须实现Comparable
 */
fun <T: Comparable<T>> maxNums2(vararg nums: T): T{
    if(nums.isEmpty()){
        throw RuntimeException("params can not be empty.")
    }
    var maxNum = nums[0]
    for(num in nums){
        if(num > maxNum){
            maxNum = num
        }
    }
    return maxNum
}
//========================================分割线============================================封装====默认值
fun String.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(context, this, duration).show()
}
fun Int.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(context, this, duration).show()
}
//========================================分割线============================================封装====默认值+高阶函数
fun View.showSnackbar(text:String, actionText:String? = null, duration: Int = Snackbar.LENGTH_SHORT, block: (() -> Unit)? = null){
    val snackbar = Snackbar.make(this, text, duration)
    if(actionText != null && block != null){
        snackbar.setAction(actionText){
            block()
        }
    }
    snackbar.show()
}



fun main() {


}