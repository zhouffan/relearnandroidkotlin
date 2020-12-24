# relearn-android-kotlin

## 1.1 Android 版本
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
