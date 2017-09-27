package com.example.super_yu.myexample.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.super_yu.myexample.R;

/**
 * 圆形 加载
 */

public class CircleLoadingView extends View {

    private Paint mPaint;
    private RectF mRectF;

    private int mRatio = 60;    // a ratio used to control rotational speed

    private float mArcSpacing;  // the spacing between two section arc
    private float mArcWidth;    // the arc paint stroke width
    private float mArcRadian;   // the radian of a single section arc
    private int mStartColor;
    private int mEndColor;

    private int mWidth;
    private int mHeight;

    private float maxAngle = 0;

    public CircleLoadingView(Context context) {
        this(context, null);
        Log.d("w", "CircleLoadingView(Context context)");
    }

    public CircleLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        Log.d("w", "CircleLoadingView(Context context, @Nullable AttributeSet attrs)");
    }

    public CircleLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d("w", "CircleLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)");
        initAttrs(context, attrs);
    }

    public void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleLoadingView);
        mStartColor = typedArray.getColor(R.styleable.CircleLoadingView_circleStartColor,
                ContextCompat.getColor(context, R.color.colorPrimary));
        mEndColor = typedArray.getColor(R.styleable.CircleLoadingView_circleEndColor,
                ContextCompat.getColor(context, R.color.colorPrimaryDark));
        mArcSpacing = typedArray.getInteger(R.styleable.CircleLoadingView_circleArcSpacing, 10);
        mArcWidth = typedArray.getInteger(R.styleable.CircleLoadingView_circleArcWidth, 5);
        mArcRadian = typedArray.getInteger(R.styleable.CircleLoadingView_circleArcWidth, 5);
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mArcWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        Log.d("w", "initAttrs(Context context, AttributeSet attrs)");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = getWidth();
        mHeight = getHeight();
        mRectF = new RectF(mArcWidth, mArcWidth, mWidth - mArcWidth, mHeight - mArcWidth);

        Shader shader = new LinearGradient(0f, 0f, mWidth, mHeight,
                mStartColor, mEndColor, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
        Log.d("w", "onSizeChanged(int w, int h, int oldw, int oldh)");
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (maxAngle <= 360) {
            float angle = 0;
            canvas.rotate(mRatio * maxAngle / 360, mWidth / 2, mHeight / 2);
            canvas.drawArc(mRectF, maxAngle, 360 - maxAngle, false, mPaint);
            while (angle <= maxAngle) {
                float length = mArcRadian * angle / maxAngle;
                canvas.drawArc(mRectF, 0, length, false, mPaint);
                canvas.rotate(mArcSpacing, mWidth / 2, mHeight / 2);
                angle += mArcSpacing;
            }
            Log.d("w", "onDraw maxAngle = " + maxAngle);
        } else {
            float angle = 0;
            canvas.rotate(mRatio + mRatio * (maxAngle - 360) / 360, mWidth / 2, mHeight / 2);
            canvas.drawArc(mRectF, 0, maxAngle - 360, false, mPaint);
            canvas.rotate(maxAngle - 360, mWidth / 2, mHeight / 2);
            while (angle <= 720 - maxAngle) {
                float length = mArcRadian * angle / (720 - maxAngle);
                canvas.drawArc(mRectF, 0, length, false, mPaint);
                canvas.rotate(mArcSpacing, mWidth / 2, mHeight / 2);
                angle += mArcSpacing;
            }
            if (maxAngle > 720) {
                maxAngle = (int) (Math.random() * 600);
                postInvalidateDelayed((int) (Math.random() * 100));
            }
            Log.d("w", "onDraw else maxAngle = " + maxAngle);
        }
        if (maxAngle <= 720) {
            maxAngle += mArcSpacing;
            Log.d("w", "onDraw " + maxAngle);
            postInvalidateDelayed(30);
        }
    }

}
