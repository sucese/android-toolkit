# Android开发工具箱

Android开发工具箱包含两部分内容：开发工具代码与开发工具软件。　

## 开发工具代码

>Android开发工具箱提供日常开发中各种常用的工具类以及各种能提升开发效率与代码质量的第三方库的使用方法与技巧。

另外, Google也为我们提供了良好的编程工程[Guava](https://github.com/google/guava), 关于Guava的使用教程请参见[Android开发工具箱: Guava中文教程](https://github.com/guoxiaoxing/android-develop-toolkit/blob/master/doc/Android%E5%BC%80%E5%8F%91%E5%B7%A5%E5%85%B7%E7%AE%B1:%20Guava%E4%B8%AD%E6%96%87%E6%95%99%E7%A8%8B.md), 由于Guava很庞大
为了避免方法数上的瓶颈, 我们可以抽取出部分功能使用。

Android开发工具箱包含以下方面的内容:

app相关工具类

>app相关工具类主要包含类APK文件的操作、设备信息获取等功能。

bitmap相关工具类

>bitmap相关工具类包含计算图片大小、图片特殊效果处理等功能。

string相关工具类

>string相关工具类专门用来处理字符串拼接、裁减、替换等功能。

time相关工具类

>time相关工具类主要用来处理日期格式化、时间计算等功能。

## 开发工具软件

- [AAPT]()
- [Lint]()
- [ADB]()
- [9Patch]()
- [HierarchyViewer]()
- [UIAutomatorViewer]()
- [DDMLib]()
- [GPUProfiler]()
- [AllocationTracker]()


更多Mac下的开发效率工具请移步[Mac开发效率工具软件]()

## 开发工具

Java

```
brew cask install java
```

Android Studio

```
brew cask install android-studio
```

## 编译工具

Gradle 

清除库缓存

```
gradle --refresh-dependencies
```

查看库依赖

```
gradle app:dependencies
```



## 版本控制

## 调试工具

Debug工具

条件断点

临时断点

异常断点

日志断点

## 性能分析

- [TraceView]()
- [MemoryAnalysisTool]()
- [LeakCannary]()
- [AndroidDeviceMonitor]()
- [BattryPerformmance]()
- [AndroidCPUMonitor]()
- [TrepnProfiler]()

## 文档工具

文档编辑

Markdown：http://pad.haroopress.com/

文档生成

MkDocs：http://www.mkdocs.org/

## 博客工具

个人博客

Hexo：https://hexo.io/zh-cn/

开发论坛

NodeBB：https://community.nodebb.org/