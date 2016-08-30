package com.secret.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.secret.R;
import com.secret.Theme;
import com.secret.Utils;
import com.secret.actionbar.ActionBar;
import com.secret.actionbar.BaseFragment;
import com.secret.ui.widgets.LayoutHelper;

/**
 * Created by ThoLH on 8/23/16.
 */
public class LoginActivity extends BaseFragment {
    private int[] mIntroImages;
    private FrameLayout mBottomFunctionsLayout;
    private ViewPager mIntroPager;
    private Button btnLogin;
    private Button btnFacebook;
    private Button btnGooglePlus;
    private LinearLayout mBottomPages;

    @Override
    protected boolean onFragmentCreate() {
        mIntroImages = new int[3];
        mIntroImages[0] = R.drawable.img_intro_1;
        mIntroImages[1] = R.drawable.img_intro_2;
        mIntroImages[2] = R.drawable.img_intro_3;
        return super.onFragmentCreate();
    }

    @Override
    protected View createView(Context context) {
        FrameLayout frameLayout = new FrameLayout(context) {
            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = MeasureSpec.getSize(heightMeasureSpec);
                setMeasuredDimension(width, height);
                int bottomFunctionHeight = 0;
                if (mBottomFunctionsLayout != null && mBottomFunctionsLayout.getVisibility() != GONE) {
                    measureChildWithMargins(mBottomFunctionsLayout, widthMeasureSpec, 0, heightMeasureSpec, 0);
                    bottomFunctionHeight = mBottomFunctionsLayout.getMeasuredHeight();
                }
                if (mIntroPager != null && mIntroPager.getVisibility() != GONE) {
                    mIntroPager.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height - bottomFunctionHeight, MeasureSpec.AT_MOST));
                }

            }
        };
        mIntroPager = new ViewPager(context);
        mIntroPager.setPageMargin(0);
        mIntroPager.setAdapter(new IntroAdapter());
        mIntroPager.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.TOP));
        mIntroPager.setBackgroundResource(R.drawable.intro_background);
        frameLayout.addView(mIntroPager);
        mBottomFunctionsLayout = new FrameLayout(context);
        mBottomFunctionsLayout.setBackgroundResource(R.drawable.default_background);
        mBottomFunctionsLayout.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 200, Gravity.LEFT | Gravity.BOTTOM));
        frameLayout.addView(mBottomFunctionsLayout);
        mBottomPages = new LinearLayout(context);
        mBottomPages.setOrientation(LinearLayout.HORIZONTAL);
        for (int i = 0; i < mIntroImages.length; i++) {
            View view = new View(context);
            view.setLayoutParams(LayoutHelper.createLinear(9, 9, 5, 0, 5, 0));
            mBottomPages.addView(view);
        }
        mBottomPages.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 10, 0, 0));
        mBottomFunctionsLayout.addView(mBottomPages);

        btnFacebook = new Button(context);
        btnFacebook.setText("Facebook");
        btnFacebook.setAllCaps(false);
        btnFacebook.setTextColor(Color.BLACK);
        btnFacebook.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_facebook, 0, 0, 0);
        btnFacebook.setCompoundDrawablePadding(Utils.dp(5));
        btnFacebook.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        btnFacebook.setPadding(Utils.dp(28), 0, Utils.dp(28), 0);
        btnFacebook.setBackgroundResource(R.drawable.facebook_button_bg);
        btnFacebook.setLayoutParams(LayoutHelper.createFrame(160, 43, Gravity.LEFT | Gravity.BOTTOM, 14, 0, 14, 110));
        mBottomFunctionsLayout.addView(btnFacebook);

        btnGooglePlus = new Button(context);
        btnGooglePlus.setText("Google");
        btnGooglePlus.setAllCaps(false);
        btnGooglePlus.setTextColor(Color.BLACK);
        btnGooglePlus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_google_plus, 0, 0, 0);
        btnGooglePlus.setCompoundDrawablePadding(Utils.dp(5));
        btnGooglePlus.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        btnGooglePlus.setPadding(Utils.dp(28), 0, Utils.dp(28), 0);
        btnGooglePlus.setBackgroundResource(R.drawable.facebook_button_bg);
        btnGooglePlus.setLayoutParams(LayoutHelper.createFrame(160, 43, Gravity.RIGHT | Gravity.BOTTOM, 14, 0, 14, 110));
        mBottomFunctionsLayout.addView(btnGooglePlus);

        btnLogin = new Button(context);
        btnLogin.setText("Login");
        btnLogin.setTextColor(Color.WHITE);
        btnLogin.setAllCaps(false);
        btnLogin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        btnLogin.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 43, Gravity.BOTTOM, 14, 0, 14, 40));
        btnLogin.setBackgroundResource(R.drawable.login_button_bg);
        mBottomFunctionsLayout.addView(btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentFragment(new MainActivity());
            }
        });
        fragmentView = frameLayout;
        return super.createView(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getParentActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getParentActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResultFragment(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResultFragment(requestCode, permissions, grantResults);
    }

    private class IntroAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mIntroImages.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            FrameLayout frameLayout = new FrameLayout(container.getContext());
            ImageView imageView = new ImageView(container.getContext());
            imageView.setImageResource(mIntroImages[position]);
            imageView.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM));
            frameLayout.addView(imageView);
            container.addView(frameLayout);
            return frameLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            int count = mBottomPages.getChildCount();
            for (int a = 0; a < count; a++) {
                View child = mBottomPages.getChildAt(a);
                if (a == position) {
                    child.setBackgroundResource(R.drawable.intro_indicator_selected);
                } else {
                    child.setBackgroundResource(R.drawable.intro_indicator_unselected);
                }
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            if (observer != null) {
                super.unregisterDataSetObserver(observer);
            }
        }
    }
}
