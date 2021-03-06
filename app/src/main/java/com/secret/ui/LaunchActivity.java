package com.secret.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Window;

import com.secret.GlobalVars;
import com.secret.R;
import com.secret.actionbar.ActionBarLayout;
import com.secret.actionbar.BaseFragment;
import com.secret.ui.widgets.InsetsLayout;
import com.secret.ui.widgets.LayoutHelper;

import java.util.ArrayList;

public class LaunchActivity extends Activity implements ActionBarLayout.ActionBarLayoutDelegate {

    private ActionBarLayout actionBarLayout;

    private static ArrayList<BaseFragment> mainFragmentStack = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.bg_transparent);

        actionBarLayout = new ActionBarLayout(this);
        if (GlobalVars.useOccupyStatusBar) {
            InsetsLayout layout = new InsetsLayout(this);
            layout.addView(actionBarLayout);
            setContentView(layout);
        } else {
            setContentView(actionBarLayout, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        }
        actionBarLayout.init(mainFragmentStack);
        actionBarLayout.setDelegate(this);
        if (actionBarLayout.fragmentsStack.isEmpty()) {
            actionBarLayout.addFragmentToStack(new SplashActivity());
        }

        actionBarLayout.showLastFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actionBarLayout.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        actionBarLayout.onPause();
    }

    @Override
    public void onBackPressed() {
        actionBarLayout.onBackPressed();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        actionBarLayout.onLowMemory();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (actionBarLayout.fragmentsStack.size() != 0) {
            BaseFragment fragment = actionBarLayout.fragmentsStack.get(actionBarLayout.fragmentsStack.size() - 1);
            fragment.onActivityResultFragment(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (actionBarLayout.fragmentsStack.size() != 0) {
            BaseFragment fragment = actionBarLayout.fragmentsStack.get(actionBarLayout.fragmentsStack.size() - 1);
            fragment.onRequestPermissionsResultFragment(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        super.onActionModeStarted(mode);
        if (Build.VERSION.SDK_INT >= 23 && mode.getType() == ActionMode.TYPE_FLOATING) {
            return;
        }
        actionBarLayout.onActionModeStarted(mode);
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);
        if (Build.VERSION.SDK_INT >= 23 && mode.getType() == ActionMode.TYPE_FLOATING) {
            return;
        }
        actionBarLayout.onActionModeFinished(mode);
    }

    @Override
    public boolean onPreIme() {
        return false;
    }

    @Override
    public boolean needPresentFragment(BaseFragment fragment, boolean removeLast, boolean forceWithoutAnimation, ActionBarLayout layout) {
        return true;
    }

    @Override
    public boolean needAddFragmentToStack(BaseFragment fragment, ActionBarLayout layout) {
        return true;
    }

    @Override
    public boolean needCloseLastFragment(ActionBarLayout layout) {
        if (layout.fragmentsStack.size() <= 1) {
            finish();
            return false;
        }
        return true;
    }

    @Override
    public void onRebuildAllFragments(ActionBarLayout layout) {

    }
}
