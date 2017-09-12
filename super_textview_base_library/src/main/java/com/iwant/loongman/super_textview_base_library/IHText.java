package com.iwant.loongman.super_textview_base_library;

import android.graphics.Canvas;
import android.util.AttributeSet;

public interface IHText {
    void init(HTextView hTextView, AttributeSet attrs, int defStyle);

    void animateText(CharSequence text);

    void onDraw(Canvas canvas);

    void setAnimationListener(AnimationListener listener);
}
