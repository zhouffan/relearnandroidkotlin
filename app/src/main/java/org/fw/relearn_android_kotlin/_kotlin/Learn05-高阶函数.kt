package org.fw.relearn_android_kotlin._kotlin

import java.lang.StringBuilder

/**
 *    author : 进击的巨人
 *    e-mail : zhouffan@qq.com
 *    date   : 2020/12/26 21:52
 *    desc   :
 *    version: 1.0
 */

/**
 * 1 标准函数：run/apply；  函数式API：map/filter
 * 2 高阶函数：函数的参数式 一个函数； 或者返回值是一个函数。    （函数中，包含其他函数）
 *   函数语法： （String, Int）-> Unit       （参数，参数）-> 返回值
 * 3 函数引用方式的写法  ::plus
 *
 */
//eg
fun example(func: (String, Int)->Unit){
    func("aa", 1)
}

fun num1AndNum2(num1: Int, num2: Int, operation: (Int, Int)->Int): Int{
    return operation(num1, num2)
}
fun plus(num1: Int, num2: Int): Int{
    return num1 + num2
}

/**
 * 1 解释一： 给StringBuilder定义一个 build 扩展函数
 * 2 解释二： 函数为高阶函数， 参数是一个函数
 * 3 解释二： StringBuilder. 语法结构 这才是定义高阶函数完整的语法规则，在函数类型前加上 ClassName. 表示这个函数类型定义在哪个类中
 *
 * 为什么将函数类型定义到 StringBuilder中？    答：调用build函数时，传入Lambda时会自动拥有StringBuilder的上下文。 这个也是apply函数的实现方式。
 * apply可以作用在所有类上，build只能作用在StringBuilder上（借助泛型实现可作用在所有类上）
 */
fun StringBuilder.build(block: StringBuilder.()->Unit): StringBuilder{
    block()
    return this
}
fun testBuild(){
    val list = listOf("aa", "bb", "cc")
    val result = StringBuilder().build {
        //没有StringBuilder.   就不能调用append方法
        append("======")
        for (i in list){
            append(i).append("\n")
        }
    }
    println(result)
}

// 高阶函数原理：kotlin编译成java字节码， java中没有高阶函数，编译器将高阶语法转换成java支持的语法结构
//public static int num1AndNum2(int num1, int num2, Function operation){
//    return (int) operation.invoke(num1, num2);
//}
//public static void main(){
//    int result = num1AndNum2(1, 2, new Function() {
//        @Override
//        public Integer invoke(Integer i1, Integer i2) {
//            return i1 + i2;
//        }
//    });
//}

//Lambda表达式 == 匿名函数， 每次会造成额外的内存和性能开销。为了解决这个问题，引入了内联函数
//================================================================================================内联函数
/**
 * 1 内联函数： 定义高阶函数时加上 inline关键字    目的：减少匿名类创建，减少运行开销。
 * 2 工作原理：编译时自动替换到调用它的地方，就不存在运行时的开销了。 完全消除Lambda匿名表达式带来的运行时开销。
 * 3 noinline: 在inline函数中，某个函数参数不会被内联
 * 4 nolinlline存在的价值：内联参的函数类型参数只允许传递给另一个内联函数（因为它会被替换，不是真正的参数属性）。 而noinline则是真实的参数。
 * 5 return：内联函数 全局返回； 非内联函数 局部返回
 *
 * 6 ============将高阶函数定义成内联函数是一种良好习惯=========
 * 7 crossinline:除了return，保留了内联函数的其他所有特性  （保证内联函数的lambda中一定不会使用return）
 *   在高阶函数中，创建联另外一个Lambda或者匿名类的实现，并调用函数类型参数。此时再将高阶申明成内联，会提示错误
 */
inline fun num1AndNum2_2(num1: Int, num2: Int, operation: (Int, Int)->Int, noinline operation2: (Int, Int) -> Int): Int{
    operation2(num1, num2)
    return operation(num1, num2)
}

//============================================================return：内联函数 全局返回； 非内联函数 局部返回
fun printString(string: String, block: (String)->Unit){
    println("printString  1")
    block(string)
    println("printString  2")
}
fun testPrintString(){
    println("testPrintString 1")
    printString("aaa"){
        println("lambda 1")
        return@printString
        println("lambda 2")
    }
    println("testPrintString 2")
}
inline fun printString2(string: String, block: (String)->Unit){
    println("printString2  1")
    block(string)
    println("printString2  2")
}
fun testPrintString2(){
    println("testPrintString 1")
    printString2("aaa"){
        println("lambda 1")
        //全局返回
        return
        println("lambda 2")
    }
    println("testPrintString 2")
}
//============================================================crossinline
inline fun testcl(block: () -> Unit){
    //匿名函数中调用了 函数类型参数
    val runnable = Runnable {
        //inline 和这里冲突
//        block()
    }
    runnable.run()
}
inline fun testcl2(crossinline block: () -> Unit){
    //匿名函数中调用了 函数类型参数
    val runnable = Runnable {
        //申明 crossinline
        block()
    }
    runnable.run()
}
fun testPrintString3(){
    println("testPrintString 1")
    testcl(){
        println("lambda 1")
        //返回
        return
        println("lambda 2")
    }
    testcl2(){
        println("lambda 1")
        //不能返回
//        return
        println("lambda 2")
    }
    println("testPrintString 2")
}


fun main() {


    example { s, i -> println("$s   $i") }

    //函数引用方式的写法
    num1AndNum2(10, 5, ::plus)
    num1AndNum2(20, 2){n1, n2->n1 - n2}

    testBuild()

    testPrintString()
    println()
    testPrintString2()
}