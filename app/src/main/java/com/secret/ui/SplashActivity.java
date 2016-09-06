package com.secret.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.secret.R;
import com.secret.Utils;
import com.secret.actionbar.BaseFragment;
import com.secret.ui.widgets.LayoutHelper;

/**
 * Created by ThoLH on 8/29/16.
 */
public class SplashActivity extends BaseFragment {

    private Thread mThread;

    @Override
    protected View createView(Context context) {
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setBackgroundColor(Color.parseColor("#4a90e2"));
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.splash_logo);
        imageView.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        frameLayout.addView(imageView);
        fragmentView = frameLayout;
        return super.createView(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mThread != null) {
            return;
        }
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Utils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (getParentActivity() != null && !getParentActivity().isFinishing()) {
                                presentFragment(new IntroActivity(), true, true);
                            }
                            mThread = null;
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        mThread.start();
    }
}
