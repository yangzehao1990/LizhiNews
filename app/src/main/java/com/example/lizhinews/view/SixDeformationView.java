package com.example.lizhinews.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 定义启动页的六边形
 */
public class SixDeformationView extends TextView
{
    private int mWidth; //宽
    private int mHeight; //高
    private int mLength; //中心到边的长度


    private Paint mImagePaint;

    private float mA, mB, mC;


    public SixDeformationView(Context context)
    {
        super(context);
    }

    public SixDeformationView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        mWidth = getWidth();
        mHeight = getHeight();

        mLength = mWidth / 2;  //得到中心

        double radian = 30 * Math.PI / 180;  //得到六边形每个角的弧度

        mA = (float) (mLength * Math.sin(radian));
        mB = (float) (mLength * Math.cos(radian));
        mC = (mHeight - 2 * mB) / 2;

        if (null == mImagePaint)
        {
            mImagePaint = new Paint();
            mImagePaint.setAntiAlias(true);
            mImagePaint.setStyle(Paint.Style.STROKE);
            mImagePaint.setColor(Color.WHITE);
            mImagePaint.setTypeface(Typeface.DEFAULT_BOLD);
        }
        //画六边形
        Path path = new Path();
        path.moveTo(getWidth(), getHeight() / 2);
        path.lineTo(getWidth() - mA, getHeight() - mC);
        path.lineTo(getWidth() - mA - mLength, getHeight() - mC);
        path.lineTo(0, getHeight() / 2);
        path.lineTo(mA, mC);
        path.lineTo(getWidth() - mA, mC);
        path.close();


        canvas.drawPath(path, mImagePaint);
    }
}
