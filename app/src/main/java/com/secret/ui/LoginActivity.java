package com.secret.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.secret.actionbar.ActionBar;
import com.secret.actionbar.BaseFragment;
import com.secret.ui.widgets.LayoutHelper;

/**
 * Created by ThoLH on 8/23/16.
 */
public class LoginActivity extends BaseFragment {

    @Override
    protected View createView(Context context) {
        FrameLayout frameLayout = new FrameLayout(context);
        actionBar = new ActionBar(context);
        actionBar.setBackgroundColor(Color.DKGRAY);
        actionBar.setTitle(getClass().getSimpleName());
        actionBar.setBackButtonImage(android.R.drawable.ic_menu_close_clear_cancel);
        actionBar.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.LEFT | Gravity.TOP));
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                super.onItemClick(id);
                if (id == android.R.id.home) {
                    finishFragment();
                }
            }
        });
        frameLayout.addView(actionBar);
        Button btnLogin = new Button(context);
        btnLogin.setText("Login");
        btnLogin.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        frameLayout.addView(btnLogin);
        frameLayout.setBackgroundColor(Color.WHITE);
        fragmentView = frameLayout;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentFragment(new MainActivity());
            }
        });
        return super.createView(context);
    }

}
