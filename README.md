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



### 界面

主列表：展示日记信息，

详细页：展示日记详情，

编辑页：新增或修改日记。