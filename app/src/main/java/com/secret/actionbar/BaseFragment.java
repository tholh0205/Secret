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
    protected ActionBar actionBar;
    private Dialog visibleDialog;
    //private Activity activity;
    private Bundle arguments;
    protected boolean swipeBackEnabled = true;

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

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
        if (actionBar != null) {
            actionBar.onPause();
        }

        if (visibleDialog != null && visibleDialog.isShowing()) {
            visibleDialog.dismiss();
            visibleDialog = null;
        }
    }

    public void onStop() {
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


}
