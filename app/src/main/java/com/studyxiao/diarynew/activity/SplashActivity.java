package com.studyxiao.diarynew.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.studyxiao.diarynew.activity.base.BaseActivity;

/**
 * Created by Studyxiao on 17/2/25.
 */

public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.startActivity(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.startActivity(SplashActivity.this);
                finish();
            }
        },2000);//延时2s
    }
}
