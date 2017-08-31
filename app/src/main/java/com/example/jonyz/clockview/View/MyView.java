package com.example.jonyz.clockview.View;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义View
 * Created by JonyZ on 2017/8/30.
 */

public class MyView extends View {


    /**
     * 先测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=getMySize(100,widthMeasureSpec);
        int height=getMySize(100,heightMeasureSpec);
        if (width>height){
            width=height;
        }else {
            width=height;
        }
        setMeasuredDimension(width,height);
    }


    /**
     * 画
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画一个圆
        int r=getMeasuredWidth()/2;
        int centerX=getLeft()+r;
        int centerY=getTop()+r;
        Paint paint= new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(r,r,r,paint);
    }

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    /**
     * 获取测量的大小
     * @param defaultSize
     * @param measureSpec
     * @return
     */
    public int getMySize(int defaultSize, int measureSpec) {
        int size=0;
        int Mode = MeasureSpec.getMode(measureSpec);
        int Size=MeasureSpec.getSize(measureSpec);
        switch (Mode){
            case MeasureSpec.UNSPECIFIED://大小随便
                if (Size>defaultSize){
                    size=Size;
                }else {
                    size=defaultSize;
                }
                break;
            case MeasureSpec.AT_MOST:   //只有那么大
                size=Size;
                break;
            case MeasureSpec.EXACTLY:
                size=Size;
                break;

        }
        return size;

    }
}
