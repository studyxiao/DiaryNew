package com.studyxiao.diarynew.activity.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.studyxiao.diarynew.util.LogUtils;

import java.lang.reflect.Field;

/**
 * Created by Studyxiao on 17/2/22.
 */

public class BaseActivity extends AppCompatActivity {

    public String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getComponentName().getShortClassName();
//        setStatusBar(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.e(TAG,"onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.e(TAG,"onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.e(TAG,"onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.e(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.e(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.e(TAG,"onDestroy");
    }

    /**
     *
     * @param navi true不设置导航栏/false设置导航栏
     */
    public void setStatusBar(boolean navi){
        //api>21
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            if (navi){
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.setNavigationBarColor(Color.TRANSPARENT);
            }
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
            |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){

            if (navi){
                //半透明导航栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
            //半透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 获得状态栏高度
     * @param context
     * @return
     */
    public int getStatusBarHeight(Context context){
        int statusBarHeight = 0;
        try{
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object obj = clazz.newInstance();
            Field field = clazz.getField("status_bar_height");
            int temp = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(temp);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }


}
