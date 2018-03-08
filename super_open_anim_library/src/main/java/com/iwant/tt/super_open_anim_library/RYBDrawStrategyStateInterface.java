package com.iwant.tt.super_open_anim_library;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

interface RYBDrawStrategyStateInterface {

   /**
    * 绘制图标
    * @param canvas 画布
    * @param fraction 完成时间
    * @param drawable 图标素材
    * @param colorOfIcon 绘制颜色
    * @param widthAndHeightOfView view的宽高
    */
   void drawIcon(Canvas canvas, float fraction, Drawable drawable, int colorOfIcon,
                 WidthAndHeightOfView widthAndHeightOfView);
}
