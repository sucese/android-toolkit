#一 华为手机

1. 华为手机无法显示log解决方案,.拨号界面输入(*#*#2846579#*#*) Service menu will appear.Go to "ProjectMenu" -> "Background Setting" -> "Log Setting"Open "Log switch" and set it to ON.Open "Log level setting" and set the log level you wish.

2. 华为手机无法打开USB调试的问题，插好数据线,拨号界面 输入 ##2846579## 进入工程模式projectmenu→3后台设置→4USB端口配置→Balong调试模式,点确定不要拔线,退出工程模式,直接重启手机,电脑中显示可移动磁盘(若仍未出现,重复步骤1、2)
这个是关闭USB调试的情况下电脑中使用手机的可移动磁盘的方法，使用后下拉菜单中usb选项也回来了。

3. 使用SnackBar的时候，不要使用view.getRootView()作为snackbar的view,华为荣耀7会出问题。

#二 小米手机

#三 魅族手机

1. 设置TextView单行显示的时候不要用Lines=1,而要用singleLine="true", 因为魅族部分手机在设置Lines=1的时候，然后TextView的值全为数字的时候会有问题.

#四 三星手机

#五 乐视手机

##5.1 mac系统下usb调试乐视手机

输入命令: system_profiler SPUSBDataType 打印设备信息

```
X600:

Product ID: 0x1768
Vendor ID: 0x2b0e
Version: ff.ff
Serial Number: 69ZH5LBAHMTOIFKZ
Speed: Up to 480 Mb/sec
Manufacturer: MediaTek
Location ID: 0x14100000 / 8
Current Available (mA): 500
Current Required (mA): 192
```

接下来 vim ~/.android/adb_usb.ini ，新开一行把 Vendor ID 加上。

最后 adb kill-server && adb devices 就可以 adb 调试了。