package com.secret;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.secret.actionbar.ActionBarLayout;
import com.secret.actionbar.BaseFragment;
import com.secret.ui.LoginActivity;
import com.secret.ui.widgets.LayoutHelper;

import java.util.ArrayList;

public class LaunchActivity extends Activity implements ActionBarLayout.ActionBarLayoutDelegate {

    private ActionBarLayout actionBarLayout;

    private static ArrayList<BaseFragment> mainFragmentStack = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        actionBarLayout = new ActionBarLayout(this);
        setContentView(actionBarLayout, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        actionBarLayout.init(mainFragmentStack);
        actionBarLayout.setDelegate(this);
        if (actionBarLayout.fragmentsStack.isEmpty()) {
            actionBarLayout.addFragmentToStack(new LoginActivity());
        }

        actionBarLayout.showLastFragment();
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
        return false;
    }

    @Override
    public void onRebuildAllFragments(ActionBarLayout layout) {

    }
}
