package com.studyxiao.diarynew.application;

import android.app.Application;

import org.litepal.LitePal;

/**
 * Created by Studyxiao on 17/2/22.
 */

public class MyApplication extends Application {


    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        //添加litepal支持，操作数据库
        LitePal.initialize(myApplication);
    }

    /**
     * 获得全局上下文
     * @return
     */
    public static MyApplication getInstance(){
        return myApplication;
    }
}
