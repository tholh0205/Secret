package com.secret;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;

/**
 * Created by tholh on 8/30/16.
 */
public class Theme {

    private static Paint maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public static Drawable createSelectorDrawable(final int width, int color, boolean masked, boolean border, boolean radius) {
        if (Build.VERSION.SDK_INT >= 21) {
            GradientDrawable maskDrawable = null;
            if (masked) {
                maskPaint.setColor(0xffffffff);
                maskDrawable = new GradientDrawable() {
                    @Override
                    public void draw(Canvas canvas) {
                        android.graphics.Rect bounds = getBounds();
                        canvas.drawCircle(bounds.centerX(), bounds.centerY(), width == 0 ? Utils.dp(18f) : bounds.width(), maskPaint);
                    }

                    @Override
                    public void setAlpha(int alpha) {

                    }

                    @Override
                    public void setColorFilter(ColorFilter colorFilter) {

                    }

                    @Override
                    public int getOpacity() {
                        return 0;
                    }
                };
                if (border) {
                    maskDrawable.setShape(GradientDrawable.RECTANGLE);
                    maskDrawable.setStroke(Utils.dp(1f), 0xffffffff);
                }
                if (radius) {
                    maskDrawable.setShape(GradientDrawable.RECTANGLE);
                    maskDrawable.setCornerRadius(Utils.dp(8f));
                }
            }
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{new int[]{}},
                    new int[]{color}
            );
            return new RippleDrawable(colorStateList, null, maskDrawable);
        } else {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(color));
            stateListDrawable.addState(new int[]{android.R.attr.state_focused}, new ColorDrawable(color));
            stateListDrawable.addState(new int[]{android.R.attr.state_selected}, new ColorDrawable(color));
            stateListDrawable.addState(new int[]{android.R.attr.state_activated}, new ColorDrawable(color));
            stateListDrawable.addState(new int[]{}, new ColorDrawable(0x00000000));
            return stateListDrawable;
        }
    }

    public static Drawable createBarSelectorDrawable(int color, boolean masked) {
        return createSelectorDrawable(0, color, masked, false, false);
    }

}
