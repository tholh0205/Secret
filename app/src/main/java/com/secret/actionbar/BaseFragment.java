package com.secret.actionbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.nineoldandroids.animation.AnimatorSet;

/**
 * Created by tholh on 8/23/16.
 */
public class BaseFragment {

    protected View fragmentView;
    protected ActionBarLayout parentLayout;
    protected ActionBar actionBar;
    private Dialog visibleDialog;
    //private Activity activity;
    private Bundle arguments;
    protected boolean swipeBackEnabled = true;
    public boolean hasOwnBackground = false;
    protected boolean isFinished = false;

    public BaseFragment() {
    }

    public BaseFragment(Bundle arguments) {
        this.arguments = arguments;
    }

    protected boolean onFragmentCreate() {
        return true;
    }

    protected View createView(Context context) {
        return fragmentView;
    }

    public void onResume() {
        android.util.Log.d(getClass().getSimpleName(), "onResume");
    }

    public void onPause() {
        android.util.Log.d(getClass().getSimpleName(), "onPause");
        if (actionBar != null) {
            actionBar.onPause();
        }

        if (visibleDialog != null && visibleDialog.isShowing()) {
            visibleDialog.dismiss();
            visibleDialog = null;
        }
    }

    protected void clearViews() {
        //Destroy views
        if (actionBar != null) {
            ViewGroup parent = (ViewGroup) actionBar.getParent();
            if (parent != null) {
                parent.removeView(actionBar);
            }
            actionBar = null;
        }

        if (fragmentView != null) {
            ViewGroup parent = (ViewGroup) fragmentView.getParent();
            if (parent != null) {
                parent.removeView(fragmentView);
            }
            fragmentView = null;
        }
    }

    public void onFragmentDestroy() {
        isFinished = true;
    }

    public boolean onBackPressed() {
        return true;
    }

    public void onConfigurationChanged(Configuration newConfig) {

    }

    public void onLowMemory() {
    }

    public void onActivityResultFragment(int requestCode, int resultCode, Intent data) {

    }

    public void onRequestPermissionsResultFragment(int requestCode, String[] permissions, int[] grantResults) {

    }

    public void saveSelfArgs(Bundle args) {

    }

    public void restoreSelfArgs(Bundle args) {

    }

    public boolean needDelayOpenAnimation() {
        return false;
    }

    public AnimatorSet onCustomTransitionAnimation(boolean isOpen, Runnable endRunnable) {
        return null;
    }

    public void onTransitionAnimationStart(boolean isOpen, boolean isBackward) {

    }

    public void onTransitionAnimationEnd(boolean isOpen, boolean isBackward) {

    }

    public void showDialog(int id) {
        Dialog dialog = onCreateDialog(id);
        if (visibleDialog != null && visibleDialog.isShowing()) {
            visibleDialog.dismiss();
            visibleDialog = null;
        }
        if (dialog != null && !dialog.isShowing() /*&& activity != null && !activity.isFinishing()*/) {
            visibleDialog = dialog;
            visibleDialog.show();
        }
    }

    protected Dialog onCreateDialog(int id) {
        return null;
    }


    public void onBeginSlide() {

    }

    protected void setParentLayout(ActionBarLayout layout) {
        if (parentLayout != layout) {
            parentLayout = layout;
            if (fragmentView != null) {
                ViewGroup parent = (ViewGroup) fragmentView.getParent();
                if (parent != null) {
                    try {
                        parent.removeView(fragmentView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (parentLayout != null && parentLayout.getContext() != fragmentView.getContext()) {
                    fragmentView = null;
                }
            }
            if (actionBar != null) {
                ViewGroup parent = (ViewGroup) actionBar.getParent();
                if (parent != null) {
                    try {
                        parent.removeView(actionBar);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (parentLayout != null && parentLayout.getContext() != actionBar.getContext()) {
                    actionBar = null;
                }
            }
//            if (parentLayout != null && actionBar == null) {
//                actionBar = createActionBar(parentLayout.getContext());
//                actionBar.parentFragment = this;
//            }
        }
    }

    public boolean presentFragment(BaseFragment fragment) {
        return parentLayout != null && parentLayout.presentFragment(fragment);
    }

    public boolean presentFragment(BaseFragment fragment, boolean removeLast) {
        return parentLayout != null && parentLayout.presentFragment(fragment, removeLast);
    }

    public boolean presentFragment(BaseFragment fragment, boolean removeLast, boolean forceWithoutAnimation) {
        return parentLayout != null && parentLayout.presentFragment(fragment, removeLast, forceWithoutAnimation, true);
    }

    public Activity getParentActivity() {
        if (parentLayout != null) {
            return parentLayout.parentActivity;
        }
        return null;
    }

    public void startActivityForResult(final Intent intent, final int requestCode) {
        if (parentLayout != null) {
            parentLayout.startActivityForResult(intent, requestCode);
        }
    }

    public void finishFragment() {
        finishFragment(true);
    }

    public void finishFragment(boolean animated) {
        if (isFinished || parentLayout == null) {
            return;
        }
        parentLayout.closeLastFragment(animated);
    }

    public void removeSelfFromStack() {
        if (isFinished || parentLayout == null) {
            return;
        }
        parentLayout.removeFragmentFromStack(this);
    }
}
