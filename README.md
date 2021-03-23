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
- Material Design： 5.0开始.  
Toolbar/DrawerLayout(androidx库)/FloatingActionButton/MaterialCardView/SwipeRefreshLayout/
- jetpack: 基础/架构/行为/界面
- viewmodel：目的：专门分担activity部分工作，存放与界面相关的数据。 activity中能看到的数据都存于其中。  
特性：屏幕旋转不会丢失数据（activity会重新创建），只有activity退出时，才跟随activity一起销毁。  
```
class MainViewModel : ViewModel() { 
    var counter = 0 
}

val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
```
- lifecycle：目的-->让任何一个类都能轻松感知到Activity的生命周期
```
class MyObserver : LifecycleObserver { 
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun activityStart() {
        Log.d("MyObserver", "activityStart")
    } 
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun activityStop() {
        Log.d("MyObserver", "activityStop")
    } 
}

//观察LifecycleOwner的生命周期(在Activity中)
lifecycle.addObserver(MyObserver())

```
- LiveData：包含任何类型的数据，并在数据发生变化的时候通知给观察者。（响应式编程组件）
LiveData特别适合与ViewModel结合在一起使用，虽然它也可以单独用在别的地方，但是绝大多数情况下，它都是使用在ViewModel当中的。
```
class MainViewModel : ViewModel() { 
    val counter = MutableLiveData<Int>() 
    fun plusOne() {
        val count = counter.value ?: 0
        counter.value = count + 1
    } 
    fun clear() {
        counter.value = 0
    } 
}
//activity中， 当LiveData中的数据发生变化时，最新的数据将会实时更新到界面
viewModel.counter.observe(this, Observer { count ->
       infoText.text = count.toString() // 将最新数据更新到界面上
})

```
- Room：关系型数据库， ORM：对象关系映射
Entity：实体类 数据库中有一张对应的表，并且表中的列是根据实体类中的字段自动生成的。  
Dao ：对数据库的各项操作进行封装，在实际编程的时候，逻辑层就不需要和底层数据库打交道了，直接和Dao层进行交互即可。  
Database ：用于定义数据库中的关键信息，包括数据库的版本号、包含哪些实体类以及提供Dao层的访问实例。
```
@Entity
data class User(var firstName: String, var lastName: String, var age: Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Dao
interface UserDao { 
    @Insert
    fun insertUser(user: User): Long
    @Update
    fun updateUser(newUser: User)
    @Query("select * from User")
    fun loadAllUsers(): List<User>
    @Query("select * from User where age > :age")
    fun loadUsersOlderThan(age: Int): List<User>
    @Delete
    fun deleteUser(user: User)
    @Query("delete from User where lastName = :lastName")
    fun deleteUserByLastName(lastName: String): Int
}
@Database(version = 1, entities = [User::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object {
        private var instance: AppDatabase? = null
        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database").build().apply {
                instance = this
            }
        }
    }
}
```
- workManager: 很适合用于处理一些要求定时执行的任务，可以根据操作系统的版本自动选择底层是使用AlarmManager实现还是JobScheduler实现。还支持周期性任务、链式任务处理等功能。  
用法：  
1 定义一个后台任务，并实现具体的任务逻辑。  
2 配置该后台任务的运行条件和约束信息，并构建后台任务请求。  
3 将该后台任务请求传入WorkManager的enqueue()方法中，系统会在合适的时间运行。
```
//1
class SimpleWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        Log.d("SimpleWorker", "do work in SimpleWorker")
        return Result.success()
    }
}
//2
val request = OneTimeWorkRequest.Builder(SimpleWorker::class.java)
    .setInitialDelay(5, TimeUnit.MINUTES)
    .build()
//3
WorkManager.getInstance(context).enqueue(request)

```
- android 10.0以上， Settings→Display→Dark theme中对深色主题进行开启。  
最简单的一种适配方式就是使用Force Dark，它是一种能让应用程序快速适配深色主题，并且几乎不用编写额外代码的方式。
```
<resources>
    //自动使用深色主题
    <style name="AppTheme" parent="Theme.AppCompat.DayNight.NoActionBar">

    <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        //快速适配深色主题
        <item name="android:forceDarkAllowed">true</item>
    </style>
</resources>


//values-night目录   一旦用户开启了深色主题，系统就会去读取values-night/colors.xml
<resources>
    <color name="colorPrimary">#303030</color>
    <color name="colorPrimaryDark">#232323</color>
    <color name="colorAccent">#008577</color>
</resources>

```

- Tools→Kotlin→Show Kotlin Bytecode




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
- Serializable/Parcelable  
class Person : Serializable {}  
//2  
@Parcelize  
class Person(var name: String, var age: Int) : Parcelable






----
箭头是单向
UI控制层-->viewmodel层-->仓库层-->本地数据源---持久化文件
                             -->网络数据源---webservice






/**
 * 1 inflate 参数： viewgroup：加载的资源要依附的父布局； attachToRoot：资源是否要依附在父布局
 * 说明：要想使item的布局属性layout_width和layout_height生效，则必须指定父容器（一个控件在容器中的大小），对应的viewgroup指定  
 * (R.layout.fruit_item, parent, false)：表示item根节点有效，但是又不想处于某个容器中  
 */  
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {  
    val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)  
    ...  
}
















