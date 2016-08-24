package com.secret.ui;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.secret.actionbar.ActionBar;
import com.secret.actionbar.BaseFragment;
import com.secret.ui.widgets.LayoutHelper;

/**
 * Created by tholh on 8/23/16.
 */
public class MainActivity extends BaseFragment {

    @Override
    protected View createView(Context context) {
        FrameLayout frameLayout = new FrameLayout(context);
        actionBar = new ActionBar(context);
        actionBar.setBackgroundColor(Color.DKGRAY);
        actionBar.setTitle(getClass().getSimpleName());
        actionBar.setBackButtonImage(android.R.drawable.ic_menu_close_clear_cancel);
        actionBar.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.LEFT | Gravity.TOP));
        frameLayout.addView(actionBar);

        SimpleDraweeView imageView = new SimpleDraweeView(context);
        imageView.setLayoutParams(LayoutHelper.createFrame(300, 300, Gravity.CENTER));
        frameLayout.addView(imageView);
        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setFadeDuration(1000)
                .build();
        imageView.setHierarchy(hierarchy);
        imageView.setImageURI("https://dyn0.media.forbiddenplanet.com/products/god%20of%20bloody%20war.jpg.jpg");

        Button btnLogin = new Button(context);
        btnLogin.setText("Main");
        btnLogin.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        frameLayout.addView(btnLogin);
        frameLayout.setBackgroundColor(Color.WHITE);
        fragmentView = frameLayout;
        return super.createView(context);
    }
}
