package com.studyxiao.diarynew.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Studyxiao on 17/2/25.
 */

public class LinedTextView extends TextView {
    public LinedTextView(Context context) {
        super(context);
    }

    public LinedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LinedTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint mPaint = new Paint();

        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setColor(Color.LTGRAY);

        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 5);

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

        int count = (height - paddingTop - paddingBottom) / lineHeight;//多少行文字

        for (int i = 0; i < count; i++) {

            //lineHeight * (i+1) + paddingTop - spcingHeight，这个值就正好贴着文字底划线。
            int baseline = lineHeight * (i + 1) + paddingTop - spcingHeight + 5;//决定文字与线之间的距离（高度）

            canvas.drawLine(paddingLeft, (int) (baseline * 1.0), right - paddingRight * 2, (int) (baseline * 1.0), mPaint);

        }
        super.onDraw(canvas);

    }
}
