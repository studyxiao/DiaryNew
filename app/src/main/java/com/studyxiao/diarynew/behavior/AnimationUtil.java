package com.studyxiao.diarynew.behavior;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by Studyxiao on 17/2/25.
 */

public class AnimationUtil {

    public static final int ANIMATE_IN = 0;//滑入
    public static final int ANIMATE_OUT = 1;//滑出

    private static boolean isAnimate; //是否正在执行动画

    private final static Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    /**
     * 滑出
     * @param view
     */
    private static void animateOut(View view) {
        ViewCompat.animate(view).translationY(1000)
                .setInterpolator(INTERPOLATOR)
                .withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        isAnimate = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        isAnimate = false;
                        view.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        isAnimate = false;
                    }
                }).start();
    }

    /**
     * 滑入
     * @param view
     */
    private static void animateIn(View view) {
        view.setVisibility(View.VISIBLE);
        ViewCompat.animate(view)
                .translationY(0)
                .setInterpolator(INTERPOLATOR)
                .withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        isAnimate = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        isAnimate = false;
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        isAnimate = false;
                    }
                })
                .start();
    }

    public synchronized static void animateInAndOut(View view, int flag) {
        if (!isAnimate) {
            switch (flag) {
                case ANIMATE_IN:
                    animateIn(view);
                    break;
                case ANIMATE_OUT:
                    animateOut(view);
                    break;
            }
        }
    }
}
