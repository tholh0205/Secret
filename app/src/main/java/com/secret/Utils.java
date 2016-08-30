package com.secret;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * Created by ThoLH on 10/02/2015.
 */
public class Utils {

    private static DisplayMetrics displayMetrics;
    private static float density;
    public static Point displaySize;
    public static int statusBarHeight;

    static {
        Resources res = ApplicationLoader.applicationContext.getResources();
        displayMetrics = res.getDisplayMetrics();
        density = displayMetrics.density;
        displaySize = new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
        int resId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resId);
        }
    }

    public static boolean isTablet() {
        return false;
    }

    public static final void setLayerType(View view, int layerType, Paint paint) {
        if (Build.VERSION.SDK_INT >= 11) {
            view.setLayerType(layerType, paint);
        }
    }

    public static float getPixelsInCM(float cm, boolean isX) {
        return (cm / 2.54f) * (isX ? Utils.displayMetrics.xdpi : Utils.displayMetrics.ydpi);
    }

    public static void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (view.getWindowToken() != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static void showKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static int dp(float dp) {
        if (dp == 0) {
            return 0;
        }
        return (int) Math.ceil(Utils.density * dp);
    }

    public static int getViewInset(View view) {
        if (view == null || Build.VERSION.SDK_INT < 21 || view.getHeight() == Utils.displaySize.y || view.getHeight() == Utils.displaySize.y - statusBarHeight) {
            return 0;
        }
        try {
            Field mAttachInfoField = View.class.getDeclaredField("mAttachInfo");
            mAttachInfoField.setAccessible(true);
            Object mAttachInfo = mAttachInfoField.get(view);
            if (mAttachInfo != null) {
                Field mStableInsetsField = mAttachInfo.getClass().getDeclaredField("mStableInsets");
                mStableInsetsField.setAccessible(true);
                Rect insets = (Rect) mStableInsetsField.get(mAttachInfo);
                return insets.bottom;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getUsedMemorySize() {

        long freeSize = 0L;
        long totalSize = 0L;
        long usedSize = -1L;
        try {
            Runtime info = Runtime.getRuntime();
            freeSize = info.freeMemory();
            totalSize = info.totalMemory();
            usedSize = totalSize - freeSize;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usedSize;
    }

    public static long getMaxHeapSize() {
        long maxHeapSize = -1;
        try {
            maxHeapSize = Runtime.getRuntime().maxMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxHeapSize;
    }

    public static void runOnUIThread(Runnable r) {
        runOnUIThread(r, 0);
    }

    public static void runOnUIThread(Runnable r, long delay) {
        if (delay <= 0) {
            ApplicationLoader.applicationHandler.post(r);
        } else {
            ApplicationLoader.applicationHandler.postDelayed(r, delay);
        }
    }

    public static void cancelRunOnUIThread(Runnable r) {
        ApplicationLoader.applicationHandler.removeCallbacks(r);
    }

    public static boolean isNetworkAvailable(boolean showMess) {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) ApplicationLoader.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = false;
        }
        return status;
    }

}
