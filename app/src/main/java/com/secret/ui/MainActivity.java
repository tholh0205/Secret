package com.secret.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
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

        final SimpleDraweeView imageView = new SimpleDraweeView(context);
        imageView.setLayoutParams(LayoutHelper.createFrame(300, 300, Gravity.CENTER));
        frameLayout.addView(imageView);
        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(context.getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setFadeDuration(1000)
                .build();
        //imageView.setHierarchy(hierarchy);
        Uri uri = Uri.parse("https://media3.giphy.com/media/JLQUx1mbgv2hO/200.gif");
        //Controller is required for controller the GIF animation, here I have just set it to autoplay as per the fresco guide.
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        //controller.setHierarchy(hierarchy);
        imageView.setController(controller);
        //imageView.setImageURI("https://dyn0.media.forbiddenplanet.com/products/god%20of%20bloody%20war.jpg.jpg");
        //imageView.setImageURI("https://media.giphy.com/media/YcnsqEiOUE1ag/giphy-downsized-large.gif");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animatable animatable = imageView.getController().getAnimatable();
                if (animatable != null) {
                    animatable.start();
                }
            }
        });

        Button btnLogin = new Button(context);
        btnLogin.setText("Main");
        btnLogin.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        frameLayout.addView(btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentFragment(new ServiceDetailActivity());
            }
        });
        frameLayout.setBackgroundColor(Color.WHITE);
        fragmentView = frameLayout;
        return super.createView(context);
    }
}
