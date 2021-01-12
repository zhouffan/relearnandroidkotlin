# relearn-android-kotlin

## 1 Android
5.0系统之后改为ART运行环境， dalvik和art都是专门为移动设备定制的，针对手机内存/cpu性能有限等做了优化处理。 <br>
5.0后运行速度提升，基于最低版本5.0/ api 21 <br>
目前主流的设备分辨率目录：drawable-xxhdpi  
gradle：基于Groovy的领域特定语言（DSL）设置，摒弃了传动基于xml（Ant/Maven） <br>

> 编译型语言：一次性编写成计算机可识别的二进制文件，如c/c++。  
解释性语言：根据源代码解释成计算机可识别的二进制数据后，再执行。效率差。  
Java：编译成class文件（只有java虚拟机才能识别），虚拟机将class文件解析成计算机可识别的二进制数据后再执行。  
java  ----class----Java虚拟机（art android虚拟机）  
kotlin----class----Java虚拟机（art android虚拟机）  
所以第三方公司JetBrains设计出一门语言开发android。

kotlin（使用了更多规则的语法）和java 100%兼容----java可以转kotlin，kotlin不能转Java。

> kotlin完全抛弃了Java中的基本数据类型，全部使用对象类型。如 Int/Char...

- 标准广播/有序广播
- android 6.0运行时权限，使用中进行某一项授权  
Android 10系统为止所有的危险权限。 一共是11组30个权限：  
CALENDAR：READ_CALENDAR/WRITE_CALENDAR  
CALL_LOG：READ_CALL_LOG/WRITE_CALL_LOG/PROCESS_OUTGOING_CALLS  
CAMERA：CAMERA  
CONTACTS：READ_CONTACTS/WRITE_CONTACTS/GET_ACCOUNTS  
LOCATION：ACCESS_FINE_LOCATION/ACCESS_COARSE_LOCATION/ACCESS_BACKGROUND_LOCATION  
MICROPHONE：RECORD_AUDIO  
PHONE：READ_PHONE_STATE/READ_PHONE_NUMBERS/CALL_PHONE/ANSWER_PHONE_CALLS/ADD_VOICEMAIL/USE_SIP/ACCEPT_HANDOVER  
SENSORS：BODY_SENSORS  
ACTIVITY_RECOGNITION：ACTIVITY_RECOGNITION  
SMS：SEND_SMS/RECEIVE_SMS/READ_SMS/RECEIVE_WAP_PUSH/RECEIVE_MMS  
STORAGE：READ_EXTERNAL_STORAGE/WRITE_EXTERNAL_STORAGE/ACCESS_MEDIA_LOCATION
- android 8.0 引入类通知渠道： 每条通知对应一个渠道。是否响铃、是否振动或者是否要关闭这个渠道的通知。  
渠道创建后不能修改
- Android 7.0开始，直接使用本地真实路径的Uri被认为是不安全的，会抛出一个FileUriExposedException异常。额外配置一个Uri的共享路径
```
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
    <external-path name="my_images" path="/" />
</paths>

```
- 前台service：和普通Service最大的区别就在于，它会一直有一个正在运行的图标在系统的状态栏显示，下拉状态栏后可以看到更加详细的信息，非常类似于通知的效果。  
Android 9.0系统开始，申明 ： <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />





## 2 kotlin
```
类：open class XXX{}  
接口：interface xxx{}  
数据类：data class xxx(val xx: String)
单例类：object XXX{  
    fun xxx(){}  
}
```

相关知识点：
- 集合的函数式api： 集合中的map函数, Lambda写法
   ```
   val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape", "Watermelon")
   val newList = list.map({ fruit: String -> fruit.toUpperCase() })
   //val newList = list.map { it.toUpperCase() }

   ```
- java函数式API ： 调用Java方法，并且是单方法接口参数。如：OnClickListener  
  如：button.setOnClickListener { v -> ...}
- 标准函数with、run和apply
- 定义静态方法
- 使用密封类优化代码 ：  不用编写else条件  
sealed class Result  
class Success(val msg: String) : Result()  
class Failure(val error: Exception) : Result()
- 扩展函数: 扩展某个类的函数   fun ClassName.methodName(param1: Int, param2: Int): Int {...}
- 运算符重载: 任意两个对象进行相加等运算操作。 operator fun plus(obj: Obj): Obj { // 处理相加的逻辑}  
语法糖表达式	实际调用函数  
a + b	a.plus(b)  
a - b	a.minus(b)  
a * b	a.times(b)  
a / b	a.div(b)  
a % b	a.rem(b)  
a++	a.inc()  
a--	a.dec()  
+a	a.unaryPlus()  
-a	a.unaryMinus()  
!a	a.not()  
a == b	a.equals(b)  
a > b	a.compareTo(b)  
a < b  
a >= b  
a <= b  
a..b	a.rangeTo(b)  
a[b]	a.get(b)  
a[b] = c	a.set(b, c)  
a in b	b.contains(a)
- 高阶函数： 函数作为参数，或者返回值  
fun example(func: (String, Int) -> Unit) {  
    func("hello", 123)  
}
- 内联函数：运行时开销完全小时（性能优，习惯使用）,在定义高阶函数时加上inline
```
inline fun num1AndNum2(num1: Int, num2: Int, operation: (Int, Int) -> Int): Int {
    val result = operation(num1, num2)
    return result
}
```
- 泛型：泛型类/泛型方法。 class MyClass<T> {}  /  fun <T> method(param: T): T {}
- 类委托：委托给另一个类完成，弊端：委托类如果有很多方法
```
class MySet<T>(val helperSet: HashSet<T>) : Set<T> by helperSet {
    fun helloWorld() = println("Hello World")
    override fun isEmpty() = false
}
```
- 委托属性：字段的具体实现委托给另一个类完成
```
class MyClass { 
    //当调用p属性的时候会自动调用Delegate类的getValue()方法，当给p属性赋值的时候会自动调用Delegate类的setValue()方法。
    var p by Delegate() 
}

class Delegate { 
    var propValue: Any? = null 
    operator fun getValue(myClass: MyClass, prop: KProperty<*>): Any? {
        return propValue
    } 
    operator fun setValue(myClass: MyClass, prop: KProperty<*>, value: Any?) {
        propValue = value
    } 
} 
```
- infix函数: 构建更可读的语法， 去掉.() 等符号, 特殊的语法糖格式  
eg1: infix fun String.beginsWith(prefix: String) = startsWith(prefix)  
===> if ("Hello Kotlin" beginsWith "Hello") {...}  
eg2: infix fun <T> Collection<T>.has(element: T) = contains(element)  
===> val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape")  
      if (list has "Banana") {...}
- 泛型实化：Java中的泛型是通过类型擦除机制来实现的，而Kotlin却允许将内联函数中的泛型进行实化。  
必要条件：内联函数+reified关键字  
inline fun <reified T> getGenericType() = T::class.java  
eg: val result1 = getGenericType<String>()
- 协变和逆变 : 一个约定：in位置->参数（接收数据）， out位置-->返回值（输出数据）
```
//在泛型T的声明前面加上了一个out关键字。这就意味着现在T只能出现在out位置上，而不能出现在in位置上，同时也意味着SimpleData在泛型T上是协变的
class SimpleData<out T>(val data: T?) {
    fun get(): T? {
        return data
    }
}
//在泛型T的声明前面加上了一个in关键字。这就意味着现在T只能出现在in位置上，而不能出现在out位置上，同时也意味着Transformer在泛型T上是逆变的。
interface Transformer<in T> {
    fun transform(t: T): String
}

```
- 协程： 一种轻量级的线程， 仅在编程语言的层面就能实现不同协程之间的切换（线程：依靠操作系统的调度才能实现不同线程之间的切换）。  
-- GlobalScope.launch{} : 创建一个协程的作用域。  
-- runBlocking函数也可以用于启动一个协程: 作用域内的所有代码和子协程没有全部执行完之前一直阻塞当前线程。
-- launch函数可以用于创建多个协程
```
//创建了两个子协程
fun main() {
    runBlocking {
        launch {
            println("launch1")
            delay(1000)
            println("launch1 finished")
        }
        launch {
            println("launch2")
            delay(1000)
            println("launch2 finished")
        }
    }
}

```





----
箭头是单向
UI控制层-->viewmodel层-->仓库层-->本地数据源---持久化文件
                             -->网络数据源---webservice

























