package com.studyxiao.diarynew.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by 李 on 2017/1/26.
 */
@SuppressLint({ "ResourceAsColor", "DrawAllocation" })

public class LinedEditText extends EditText {

    public LinedEditText(Context context) {

        super(context);
        initPaint();

    }

    public LinedEditText(Context context, AttributeSet attrs) {

        super(context, attrs);
        initPaint();

    }

    public LinedEditText(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        initPaint();

    }

    private void initPaint() {

    }

    @Override protected void onDraw(Canvas canvas) {

        Paint mPaint = new Paint();

        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setColor(Color.LTGRAY);

        PathEffect effects = new DashPathEffect(new float[]{5,5,5,5},5);

        mPaint.setPathEffect(effects);

        int left = getLeft();

        int right = getRight();

        int height = getHeight();

        int paddingTop = getPaddingTop();

        int paddingBottom = getPaddingBottom();

        int paddingLeft = getPaddingLeft();

        int paddingRight = getPaddingRight();

        int lineHeight = getLineHeight();//一行文字所占高度

        int spcingHeight = (int) getLineSpacingExtra();//行间距

        int count = (height-paddingTop-paddingBottom) / lineHeight;//多少行文字

        for (int i = 0; i < count; i++) {

            //lineHeight * (i+1) + paddingTop - spcingHeight，这个值就正好贴着文字底划线。
            int baseline = lineHeight * (i+1) + paddingTop - spcingHeight + 5;//决定文字与线之间的距离（高度）

            canvas.drawLine(paddingLeft, (int)(baseline * 1.0), right-paddingRight * 2, (int)(baseline * 1.0), mPaint);

        }
        super.onDraw(canvas);

    }

}
