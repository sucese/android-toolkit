显示系统中全部Android平台

```
android list targets
```

显示系统中全部AVD

```
android list avd
```

创建AVD

```
android create avd --name 名称 --target 平台编号
```

启动AVD

```
emulator -avd 名称 -sdcard ~/名称.img (-skin 1280x800)
```

删除AVD

```
android delete avd --name 名称
```

创建SDCard

```
mksdcard 1024M ~/名称.img
```

启动DDMS

```
ddms
```

显示当前运行的全部设备

```
adb devices
```

对某一模拟器执行命令

```
abd -s 模拟器编号 命令
```

安装应用程序

```
adb install -r 应用程序.apk
```

获取模拟器中的文件

```
adb pull <remote> <local>
```

向模拟器中写文件

```
adb push <local> <remote>
```

进入模拟器的shell模式

```
adb shell
```

启动SDK，文档，实例下载管理器

```
android
```

缷载apk包

```
adb uninstall apk包的主包名
```

在命令行中查看LOG信息

```
adb logcat -v time | grep TAG
```

查看更详细的打印信息

```
adb bugreport > main.log
```


获取设备的ID和序列号：

```
adb get-product 
adb get-serialno
```

查看APK签名

```
keytool -list -v -keystore release.jks
```

查看子命令

adb shell dumpsys | grep "DUMP OF SERVICE"

adb shell dumpsys activity  显示Activity信息
adb shell dumpsys cpuinfo 查看CPU信息
adb shell dumpsys window  查看键盘、窗口和它们的关系
adb shell dumpsys meminfo  查看内存信息
adb shell dumpsys alarm  查看Alarm信息
adb shell dumpsys statusbar 查看状态栏信息
adb shell dumpsys usagestats 查看每个界面启动时间
