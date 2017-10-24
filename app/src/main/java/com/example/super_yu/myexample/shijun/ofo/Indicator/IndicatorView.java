package com.example.super_yu.myexample.shijun.ofo.Indicator;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.super_yu.myexample.R;

/**
 * Created by shijun on 2017/10/24.
 */

public class IndicatorView extends View {
    private ViewPager mViewPager;

    private Type indicatorType;

    private int indicatorGap;

    private Paint dotPaint;

    private Paint textPaint;

    private int indicatorRadius;

    private int indicatorTextSize;

    private int indicatorStrokeWidth;

    private int indicatorNormalColor;

    private int indicatorSelectedColor;

    private int indicatorCount;

    private int width;

    private int height;

    private int mCurSelectedPosition;

    private boolean isIndicatorFillBackground;

    private int indicatorSelectedTextColor;

    private int indicatorSelectedBackgroundColor;


    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(context, attrs);
        init();
    }

    public enum Type {
        CIRCLE,
        NUMBER,
        LETTER
    }

    /**
     * 获取自定义属性
     */
    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);
        int type = typedArray.getInt(R.styleable.IndicatorView_indicatorType, 1);
        if (type == 1) {
            indicatorType = Type.CIRCLE;
            indicatorRadius = (int) typedArray.getDimension(R.styleable.IndicatorView_indicatorRadius, dp2Px(4));
        } else if (type == 2) {
            indicatorType = Type.NUMBER;
            indicatorRadius = (int) typedArray.getDimension(R.styleable.IndicatorView_indicatorRadius, dp2Px(10));
        } else if (type == 3) {
            indicatorType = Type.LETTER;
            indicatorRadius = (int) typedArray.getDimension(R.styleable.IndicatorView_indicatorRadius, dp2Px(10));
        }
        indicatorGap = (int) typedArray.getDimension(R.styleable.IndicatorView_indicatorGap, dp2Px(12));
        indicatorTextSize = (int) typedArray.getDimension(R.styleable.IndicatorView_indicatorTextSize, sp2Px(14));
        indicatorSelectedTextColor = typedArray.getColor(R.styleable.IndicatorView_indicatorSelectedTextColor, 0xffffffff);
        indicatorStrokeWidth = (int) typedArray.getDimension(R.styleable.IndicatorView_indicatorStrokeWidth, dp2Px(1));
        indicatorNormalColor = typedArray.getColor(R.styleable.IndicatorView_indicatorNormalColor, 0xff999999);
        indicatorSelectedColor = typedArray.getColor(R.styleable.IndicatorView_indicatorSelectedColor, 0xffffffff);
        isIndicatorFillBackground = typedArray.getBoolean(R.styleable.IndicatorView_indicatorFillBackground, false);
        indicatorSelectedBackgroundColor = typedArray.getColor(R.styleable.IndicatorView_indicatorSelectedBackgroundColor, 0xff666666);
        typedArray.recycle();
    }

    private void init() {
        // 绘制圆点
        dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 绘制文本
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(indicatorTextSize);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(width, height);
        int width = getWidthSize(widthMeasureSpec);
        int height = getHeightSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int getHeightSize(int heightMeasureSpec) {
        int size = 0;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            size = heightSize;
        } else {
            // 高度为上下padding + indicator指示器高度
            size = getPaddingTop() + getPaddingBottom() + 2 * indicatorRadius
                    + indicatorStrokeWidth;
        }
        return size;
    }

    private int getWidthSize(int widthMeasureSpec) {
        int size = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            size = widthSize;
        } else {
            size = getPaddingLeft() + getPaddingRight() + indicatorRadius * 2 * indicatorCount
                    + indicatorStrokeWidth * indicatorCount + indicatorGap * (indicatorCount - 1);
        }
        return size;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (Type.CIRCLE == indicatorType) {
            drawCircle(canvas);
        } else {
            drawWithText(canvas);
        }
    }


    /**
     * 绘制圆点型指示器
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        for (int i = 0; i < indicatorCount; i++) {
            float centerX = getPaddingLeft() + (2 * i + 1) * indicatorRadius + i * indicatorGap;
            float centerY = getPaddingTop() + indicatorRadius;
            if (i == mCurSelectedPosition) {
                dotPaint.setColor(indicatorSelectedColor);
            } else {
                dotPaint.setColor(indicatorNormalColor);
            }
            dotPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(centerX, centerY, indicatorRadius, dotPaint);
        }
    }

    /**
     * 绘制数字型指示器
     *
     * @param canvas
     */
    private void drawWithText(Canvas canvas) {
        for (int i = 0; i < indicatorCount; i++) {
            float centerX = getPaddingLeft() + (2 * i + 1) * (indicatorRadius + indicatorStrokeWidth / 2f)
                    + i * indicatorGap;
            float centerY = getPaddingTop() + indicatorRadius + indicatorStrokeWidth / 2f;
            if (isIndicatorFillBackground) {
                if (i == mCurSelectedPosition) {
                    dotPaint.setColor(indicatorSelectedBackgroundColor);
                    dotPaint.setStyle(Paint.Style.FILL);
                    textPaint.setColor(indicatorSelectedTextColor);
                } else {
                    dotPaint.setColor(indicatorNormalColor);
                    dotPaint.setStyle(Paint.Style.STROKE);
                    dotPaint.setStrokeWidth(indicatorStrokeWidth);
                    textPaint.setColor(indicatorNormalColor);
                }
            } else {
                if (i == mCurSelectedPosition) {
                    dotPaint.setColor(indicatorSelectedColor);
                    textPaint.setColor(indicatorSelectedColor);
                } else {
                    dotPaint.setColor(indicatorNormalColor);
                    textPaint.setColor(indicatorNormalColor);
                }
                // 绘制边框
                dotPaint.setStyle(Paint.Style.STROKE);
                dotPaint.setStrokeWidth(indicatorStrokeWidth);
            }
            //
            canvas.drawCircle(centerX, centerY, indicatorRadius, dotPaint);
            // 绘制文本
            float baseline = centerY - (textPaint.descent() + textPaint.ascent()) / 2f;
            if (Type.NUMBER == indicatorType) {
                canvas.drawText(String.valueOf(i + 1), centerX, baseline, textPaint);
            } else if (Type.LETTER == indicatorType) {
                canvas.drawText(String.valueOf((char) ('A' + i)), centerX, baseline, textPaint);
            }
        }
    }

    /**
     * 对外提供的方法，绑定 viewpager
     *
     * @param viewPager
     */
    public void setupViewPager(ViewPager viewPager) {
        if (viewPager == null) {
            return;
        }
        mViewPager = viewPager;
        indicatorCount = mViewPager.getAdapter().getCount();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (listener != null) {
                    listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (listener != null) {
                    listener.onPageSelected(position);
                }
                mCurSelectedPosition = position;
                invalidate();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (listener != null) {
                    listener.onPageScrollStateChanged(state);
                }
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float downX = 0;
        float downY = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                handleClick(downX, downY);
                break;

        }
        return super.onTouchEvent(event);
    }


    private void handleClick(float downX, float downY) {
        for (int i = 0; i < indicatorCount; i++) {
            float centerX = getPaddingLeft() + (2 * i + 1) * (indicatorRadius + indicatorStrokeWidth / 2f)
                    + i * indicatorGap;
            float centerY = getPaddingTop() + indicatorRadius + indicatorStrokeWidth / 2f;
            double distance = Math.sqrt((downX - centerX) * (downX - centerX) + (downY - centerY) * (downY - centerY));
            if (distance < indicatorRadius) {
                mCurSelectedPosition = i;
                Log.i("debug", "i = " + i);
                mViewPager.setCurrentItem(i, false);
                invalidate();
            }
        }
    }


    OnIndicatorChangedListener listener;


    interface OnIndicatorChangedListener {
        void onPageScrolled(int position, float positinoOffset, int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }

    public void setListener(OnIndicatorChangedListener listener) {
        this.listener = listener;
    }


    private int dp2Px(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }


    private int sp2Px(int spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getResources().getDisplayMetrics());
    }
}
