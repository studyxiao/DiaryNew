package com.studyxiao.diarynew.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

import cc.trity.floatingactionbutton.FloatingActionsMenu;

/**
 * Created by Studyxiao on 17/2/25.
 */

public class ScrollAwareFABMenuBehavior extends CoordinatorLayout.Behavior<FloatingActionsMenu> {


    private boolean isAnimationOut = false; //是否正在消失

    private final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    public ScrollAwareFABMenuBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionsMenu child, View directTargetChild, View target, int nestedScrollAxes) {
        //垂直方向滚动则监听
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionsMenu child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        //下滑
        if ((dyConsumed > 0 || dyUnconsumed>0)&& !this.isAnimationOut && child.getVisibility() == View.VISIBLE) {
            //下滑并且child可见，则隐藏child
            if (child.isExpanded()) {
                child.collapse();
            }
            //执行隐藏动画
            animateOut(child);
            //上滑
        } else if ((dyConsumed < 0 || dyUnconsumed<0)&& child.getVisibility() != View.VISIBLE) {
            //执行显示动画
            animateIn(child);
        }
    }

    private void animateOut(View view) {
        ViewCompat.animate(view).translationY(1000)
                .setInterpolator(INTERPOLATOR)
                .withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        ScrollAwareFABMenuBehavior.this.isAnimationOut = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        ScrollAwareFABMenuBehavior.this.isAnimationOut = false;
                        view.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        ScrollAwareFABMenuBehavior.this.isAnimationOut = false;
                    }
                }).start();
    }

    private void animateIn(View view) {
        view.setVisibility(View.VISIBLE);
        ViewCompat.animate(view)
                .translationY(0)
                .setInterpolator(INTERPOLATOR)
                .withLayer()
                .setListener(null)
                .start();
    }
}