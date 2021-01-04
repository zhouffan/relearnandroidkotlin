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
 *    1 DSL：领域特定语言  Domain Specific Language
 *
 *    version: 1.0
 */

//dependencies {
//    implementation 'com.squareup.okhttp3:okhttp:4.9.0'
//    implementation 'com.squareup.okio:okio:2.8.0'
//}

class Dependency{
    val libraries = ArrayList<String>()
    fun implementation(lib: String){
        libraries.add(lib)
    }
}

fun dependencies(block: Dependency.()->Unit): List<String>{
    val dependency = Dependency()
    dependency.block()
    return dependency.libraries
}

fun test(){
    dependencies {
        implementation("com.squareup.okhttp3:okhttp:4.9.0")
        implementation("com.squareup.okio:okio:2.8.0")
    }
}

fun main() {


}