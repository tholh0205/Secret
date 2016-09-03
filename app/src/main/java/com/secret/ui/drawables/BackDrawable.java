package com.secret.ui.drawables;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.animation.DecelerateInterpolator;

import com.secret.Utils;

/**
 * Created by ThoLH on 9/1/16.
 */
public class BackDrawable extends Drawable {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean reverseAngle = false;
    private float currentRotation = 1;

    public BackDrawable() {
        super();
        paint.setColor(0xff9b9b9b);
        paint.setStrokeWidth(Utils.dp(2));
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(getIntrinsicWidth() / 2, getIntrinsicHeight() / 2);
        canvas.rotate(currentRotation * (reverseAngle ? -180 : 180));
        canvas.drawLine(-Utils.dp(9), 0, Utils.dp(9) - Utils.dp(1) * currentRotation, 0, paint);
        float endYDiff = Utils.dp(5) * (1 - Math.abs(currentRotation)) - Utils.dp(0.5f) * Math.abs(currentRotation);
        float endXDiff = Utils.dp(9) - Utils.dp(0.5f) * Math.abs(currentRotation);
        float startYDiff = Utils.dp(5) + Utils.dp(3.5f) * Math.abs(currentRotation);
        float startXDiff = -Utils.dp(9) + Utils.dp(8.5f) * Math.abs(currentRotation);
        canvas.drawLine(startXDiff, -startYDiff, endXDiff, -endYDiff, paint);
        canvas.drawLine(startXDiff, startYDiff, endXDiff, endYDiff, paint);
        canvas.restore();
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    public int getIntrinsicWidth() {
        return Utils.dp(24);
    }

    @Override
    public int getIntrinsicHeight() {
        return Utils.dp(24);
    }
}
