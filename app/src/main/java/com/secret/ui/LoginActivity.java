package com.secret.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.secret.R;
import com.secret.Theme;
import com.secret.Utils;
import com.secret.actionbar.ActionBar;
import com.secret.actionbar.BaseFragment;
import com.secret.model.Album;
import com.secret.model.AlbumResponse;
import com.secret.network.ApiClient;
import com.secret.network.ApiInterface;
import com.secret.ui.widgets.LayoutHelper;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ThoLH on 8/23/16.
 */
public class LoginActivity extends BaseFragment implements View.OnClickListener {
    private int[] mIntroImages;
    private FrameLayout mBottomFunctionsLayout;
    private ViewPager mIntroPager;
    private TextView btnLogin;
    private TextView btnFacebook;
    private TextView btnGooglePlus;
    private LinearLayout mBottomPages;

    private CallbackManager callbackManager;

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Profile profile = Profile.getCurrentProfile();
            Utils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    if (getParentActivity() != null) {
                        Intent intent = new Intent(getParentActivity(), SignUpActivity.class);
                        startActivityForResult(intent, 101);
                    }
                }
            });
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException e) {
            if (e instanceof FacebookAuthorizationException) {
                LoginManager.getInstance().logOut();
            }
        }
    };


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
        FacebookSdk.sdkInitialize(getParentActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
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
        mBottomFunctionsLayout = new FrameLayout(context) {
            @Override
            protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    if (child.getVisibility() == GONE) {
                        continue;
                    }

                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) child.getLayoutParams();

                    int width = child.getMeasuredWidth();
                    int height = child.getMeasuredHeight();
                    int childLeft;
                    int childTop;

                    int gravity = lp.gravity;
                    if (gravity == -1) {
                        gravity = Gravity.TOP | Gravity.LEFT;
                    }

                    final int absoluteGravity = gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
                    final int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;

                    switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
                        case Gravity.CENTER_HORIZONTAL:
                            childLeft = (right - left - width) / 2 + lp.leftMargin - lp.rightMargin;
                            break;
                        case Gravity.RIGHT:
                            childLeft = right - width - lp.rightMargin;
                            break;
                        case Gravity.LEFT:
                        default:
                            childLeft = lp.leftMargin;
                    }

                    switch (verticalGravity) {
                        case Gravity.TOP:
                            childTop = lp.topMargin;
                            break;
                        case Gravity.CENTER_VERTICAL:
                            childTop = (bottom - top - height) / 2 + lp.topMargin - lp.bottomMargin;
                            break;
                        case Gravity.BOTTOM:
                            childTop = (bottom - top) - height - lp.bottomMargin;
                            break;
                        default:
                            childTop = lp.topMargin;
                    }
                    if (child == btnFacebook || child == btnGooglePlus) {
                        childTop = getMeasuredHeight() / 2 - height - Utils.dp(8);
                    } else if (child == btnLogin) {
                        childTop = (getMeasuredHeight() / 2 + Utils.dp(8));
                    }
                    child.layout(childLeft, childTop, childLeft + width, childTop + height);
                }
            }
        };

        mBottomFunctionsLayout.setBackgroundResource(R.drawable.default_background);
        mBottomFunctionsLayout.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 180, Gravity.LEFT | Gravity.BOTTOM));
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

        btnFacebook = new TextView(context);
        btnFacebook.setText("Facebook");
        btnFacebook.setAllCaps(false);
        btnFacebook.setTextColor(Color.BLACK);
        btnFacebook.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_facebook, 0, 0, 0);
        btnFacebook.setCompoundDrawablePadding(Utils.dp(5));
        btnFacebook.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        btnFacebook.setPadding(Utils.dp(28), 0, Utils.dp(28), 0);
        btnFacebook.setGravity(Gravity.CENTER);
        btnFacebook.setBackgroundResource(R.drawable.button_white_with_radius);
        btnFacebook.setLayoutParams(LayoutHelper.createFrame(160, 43, Gravity.LEFT | Gravity.BOTTOM, 14, 0, 14, 0));
        LoginManager.getInstance().registerCallback(callbackManager, callback);
        btnFacebook.setOnClickListener(this);
        mBottomFunctionsLayout.addView(btnFacebook);

        btnGooglePlus = new TextView(context);
        btnGooglePlus.setText("Google");
        btnGooglePlus.setAllCaps(false);
        btnGooglePlus.setTextColor(Color.BLACK);
        btnGooglePlus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_google_plus, 0, 0, 0);
        btnGooglePlus.setCompoundDrawablePadding(Utils.dp(5));
        btnGooglePlus.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        btnGooglePlus.setPadding(Utils.dp(28), 0, Utils.dp(28), 0);
        btnGooglePlus.setBackgroundResource(R.drawable.button_white_with_radius);
        btnGooglePlus.setGravity(Gravity.CENTER);
        btnGooglePlus.setLayoutParams(LayoutHelper.createFrame(160, 43, Gravity.RIGHT | Gravity.BOTTOM, 14, 0, 14, 0));
        btnGooglePlus.setOnClickListener(this);
        mBottomFunctionsLayout.addView(btnGooglePlus);

        btnLogin = new TextView(context);
        btnLogin.setText("Login");
        btnLogin.setTextColor(Color.WHITE);
        btnLogin.setAllCaps(false);
        btnLogin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        btnLogin.setGravity(Gravity.CENTER);
        btnLogin.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 43, Gravity.BOTTOM, 14, 0, 14, 0));
        btnLogin.setBackgroundResource(R.drawable.button_transparent_white_border_with_radius);
        mBottomFunctionsLayout.addView(btnLogin);
        btnLogin.setOnClickListener(this);
        fragmentView = frameLayout;
        return super.createView(context);
    }

    @Override
    public void onClick(View view) {
        if (view == btnFacebook) {
            LoginManager.getInstance().logInWithReadPermissions(getParentActivity(), Arrays.asList("public_profile", "user_friends", "email"));
        } else if (view == btnGooglePlus) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<AlbumResponse> call = apiService.getAlbumsByPage(1, 20);
            call.enqueue(new Callback<AlbumResponse>() {
                @Override
                public void onResponse(Call<AlbumResponse> call, Response<AlbumResponse> response) {
                    List<Album> albums = response.body().getData();
                    Log.d("ThoLH", "getAlbumsByPage " + albums.size());
                }

                @Override
                public void onFailure(Call<AlbumResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else if (view == btnLogin) {

        }
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


    @Override
    public void onActivityResultFragment(int requestCode, int resultCode, Intent data) {
        super.onActivityResultFragment(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
