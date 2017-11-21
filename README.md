# Android多行文字滚动(比如中奖信息滚动)

## 1、GIF图
**滚动**

![滚动](https://github.com/pirrip90/LineScrollView/blob/master/screen/screen1.gif)

------

**清空和新增**

![清空和新增](https://github.com/pirrip90/LineScrollView/blob/master/screen/screen2.gif)

------

**更改滚动信息**

![更改滚动信息](https://github.com/pirrip90/LineScrollView/blob/master/screen/screen3.gif)


## 2、方法属性
|方法名|参数|描述|
|:---:|:---:|:---:|
| textColor | color |文字颜色(默认为黑色)
| textSize | float |文字大小(默认大小为16sp)
| textSpace | integer |行的上下间隙(默认10dp)
| scrollLines | integer |滚动需达到的行数(默认5行)
| scrollScreenTime | integer |滚动一屏(当前View的大小)所需时间,单位为毫秒(默认是5000毫秒)

## 3、方法
|属性名|参数类型|描述|
|:---:|:---:|:---:|
| setLines | List<String> |设置滚动内容(会清除之前的滚动内容)
| clear | 无  |清空滚动内容
| addLines | List<String> |在现有滚动内容的基础上增加滚动信息(多行)
| addLine | String |在现有滚动内容的基础上增加滚动信息(一行)

## 4、xml使用例子
```xml
    <com.xubo.linescrollviewlib.LineScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:textColor="#FFFFFF"
        app:textSize="17"
        app:textSpace="8dp"
        app:scrollLines="6"
        app:scrollScreenTime="2000"
        />
```
## 5、xml使用注意事项
- **textSize**
>textSize属性是float类型，例子中也并没写17sp，所以文字大小是多少sp就写数字多少，支持浮点型
- **scrollScreenTime**
>scrollScreenTime表示滚动显示的一屏数据所需的时间，所以时间设置越短，滚动越快。注意时间单位是毫秒
- **layout_height**
>layout_height只需设置成wrap_content即可。不管LineScrollView高度设置多大，LineScrollView都会自动计算自己所需大小，设置其他其实毫无意义

## 6、gradle
Add the dependency:
```gradle
dependencies {
    ...
    
    implementation 'com.xubo.linescrollviewlib:LineScrollView:1.0.1'
}
```
**Android Studio 3.0以上可以使用implementation代替compile,低于3.0依然使用compile**


## 7、代码编译失败？
1.删除linescrollviewlib的build.gradle里`apply plugin: 'com.novoda.bintray-release'`下面所有groovy代码(包括这一行)
>工程是没有bintray.properties文件,无法读取bintray.properties信息

2.root目录里，删除gradle.properties里的`systemProp.http.proxyHost=127.0.0.1`和`systemProp.http.proxyPort=1080`
>gradle.properties里的代理信息不是你本机代理配置,请删除或改成你本机代理地址和端口

3.Android Studio更新到3.0





