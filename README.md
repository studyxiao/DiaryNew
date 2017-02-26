# DiaryNew
前两天看到一篇文章，觉得自己可以试一试实现，于是就有个这个，根据[Android 一款十分简洁、优雅的日记 APP](https://gold.xitu.io/entry/58abfb10ac502e00697ac1a0)修改而来。日记本，文字记录，实现简单的本地存储、查看。

### 用到的第三方库

```groovy
compile 'com.android.support:recyclerview-v7:25.1.1'
compile 'com.android.support:design:25.1.1'

//数据库相关
compile 'org.litepal.android:core:1.4.1'

//floatingActionMenu
compile 'cc.trity.floatingactionbutton:library:1.0.0'
```

### 部分用到的资源

icon : [谷歌官方icon](https://material.io/icons/)

背景图：[花瓣网](http://huaban.com/)

### 界面

主列表：展示日记信息，

详细页：展示日记详情，

编辑页：新增或修改日记。

![主界面](http://ociqp66l3.bkt.clouddn.com/%E4%B8%BB%E7%95%8C%E9%9D%A2.jpg)

![详情页面](http://ociqp66l3.bkt.clouddn.com/%E8%AF%A6%E6%83%85%E9%A1%B5%E9%9D%A2.jpg)

![编辑页面](http://ociqp66l3.bkt.clouddn.com/%E8%AF%A6%E6%83%85%E9%A1%B5%E9%9D%A2.jpg)

### 下一步

- 加入网络同步功能
- 改用MVP