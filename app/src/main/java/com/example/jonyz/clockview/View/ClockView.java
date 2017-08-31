package com.example.jonyz.clockview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

/**
 * Created by JonyZ on 2017/8/31.
 */

public class ClockView extends View {

    private Paint mPaint;
    private int r;
    private int r2;
    private String TAG = ClockView.class.getSimpleName();

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        mPaint = new Paint();
        r = getMeasuredWidth() / 2;
        r2 = getMeasuredWidth() / 2 - 3;
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(0, 0, r, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(0, 0, r2, mPaint);
        //画表盘
        prinCircle(canvas);
        //画时针
        printHour(canvas);
        canvas.restore();
        postInvalidateDelayed(1000);
    }

    /**
     * 画时针
     *
     * @param canvas
     */
    private void printHour(Canvas canvas) {
        int mPointEndLength = 50;
        //画一条线，选择线五分之一处，作为旋转中心。
        mPaint = new Paint();
    /*    mPaint.setColor(Color.BLACK);
        //时针
        mPaint.setStrokeWidth(10);
        canvas.drawLine(0,0,r-400,r-300,mPaint);
        //分针
        mPaint.setStrokeWidth(5);
        canvas.drawLine(0,0,r-300,r-300,mPaint);
        //秒针
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.RED);
        canvas.drawLine(0,0,r-200,r-300,mPaint);*/
        // drawRect(r.left, r.top, r.right, r.bottom, paint);
        //获取当前小时，分钟，秒钟
        Calendar calendar = Calendar.getInstance();
        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        Log.i(TAG, "mHour: " + mHour);
        int mMinute = calendar.get(Calendar.MINUTE);
        Log.i(TAG, "mMinute: " + mMinute);
        int mSecond = calendar.get(Calendar.SECOND);
        Log.i(TAG, "mSecond: " + mSecond);
        //获取时针canvas选择角度
        float angleHour = (mHour % 12) * 360 / 12 + mMinute * 1 / 2;
        Log.i(TAG, "angleHour: " + angleHour);
        float angleMinu = 6 * mMinute;
        Log.i(TAG, "angleMinu: " + angleMinu);
        int angleSec = 6 * mSecond;
        Log.i(TAG, "angleSec: " + mSecond);

        //时针
        canvas.save();
        canvas.rotate(angleHour);
        int mHourWidth = 20;
        RectF rectF = new RectF(-mHourWidth / 2, -r * 1 / 2, mHourWidth / 2, mPointEndLength);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(20);
        // mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(rectF, 40, 40, mPaint);
        canvas.restore();
        //分针
        canvas.save();
        canvas.rotate(angleMinu);
        int mMinuteWidth = 10;
        RectF minurectF = new RectF(-mMinuteWidth / 2, -r * 3.5f / 5, mMinuteWidth / 2, mPointEndLength);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
        canvas.drawRoundRect(minurectF, 40, 40, mPaint);
        canvas.restore();
        //秒针
        canvas.save();
        canvas.rotate(angleSec);
        int mSecondWidth = 5;
        RectF secondRectf = new RectF(-mSecondWidth / 2, -r + 15, mSecondWidth / 2, mPointEndLength);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        canvas.drawRoundRect(secondRectf, 40, 40, mPaint);
        //旋转圆点
        canvas.save();
        mPaint.setColor(Color.RED);
        canvas.drawCircle(0, 0, 10, mPaint);
        canvas.restore();
    }

    /**
     * 绘制表盘刻度
     *
     * @param canvas
     */
    private void prinCircle(Canvas canvas) {
        canvas.save();
        int lineWidth = 0;
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) { //整点
                mPaint.setStrokeWidth(3);
                mPaint.setColor(Color.BLACK);
                mPaint.setTextSize(30);
                lineWidth = 60;
                //表盘上面数字 -mRadius + DptoPx(5) + lineWidth + (textBound.bottom - textBound.top)
                String text = ((i / 5) == 0 ? 12 : (i / 5)) + "";
                Rect textBound = new Rect();    //将数字装在矩形区域内，对整个矩形区域进行旋转
                mPaint.getTextBounds(text, 0, text.length(), textBound);
                mPaint.setColor(Color.BLACK);
                canvas.save();
                canvas.translate(0, -r + 10 + lineWidth + (textBound.bottom - textBound.top));
                canvas.rotate(-6 * i);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawText(text, -(textBound.right - textBound.left) / 2, textBound.bottom, mPaint);
                canvas.restore();
            } else { //非整点
                lineWidth = 40;
                mPaint.setColor(Color.BLACK);
                mPaint.setStrokeWidth(1);
            }
            canvas.drawLine(0, -r + 10, 0, -r + 10 + lineWidth, mPaint);
            canvas.rotate(6);
        }
        canvas.restore();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeaSize(1000, widthMeasureSpec);
        int heigth = getMeaSize(1000, heightMeasureSpec);
        if (width > heigth) {
            width = heigth;
        } else {
            heigth = width;
        }
        setMeasuredDimension(width, heigth);

    }

    public int getMeaSize(int defaultSize, int measureSpec) {

        int size = 0;
        int measuSize = MeasureSpec.getSize(measureSpec);
        int Mode = MeasureSpec.getMode(measureSpec);
        switch (Mode) {
            case MeasureSpec.UNSPECIFIED:
                size = Math.min(measuSize, defaultSize);
                break;
            case MeasureSpec.AT_MOST:
                size = Math.min(measuSize, defaultSize);
                break;
            case MeasureSpec.EXACTLY:
                size = Math.min(measuSize, defaultSize);
                break;
        }
        return size;
    }
}
