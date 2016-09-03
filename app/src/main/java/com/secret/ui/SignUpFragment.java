package com.secret.ui;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.secret.R;
import com.secret.Utils;
import com.secret.actionbar.ActionBar;
import com.secret.ui.drawables.BackDrawable;
import com.secret.ui.widgets.LayoutHelper;

/**
 * Created by ThoLH on 9/2/16.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener {

    private FrameLayout mRootView;
    private ActionBar actionBar;
    private TextView createTitle;
    private ImageView imgAvatar;
    private TextView tvUsername;
    private EditText edtUsername;
    private TextView tvPassword;
    private EditText edtPassword;
    private TextView tvPhone;
    private EditText edtPhone;
    private TextView tvShowHidePassword;
    private TextView btnSignUp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = new FrameLayout(getActivity()) {

            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = MeasureSpec.getSize(heightMeasureSpec);
                setMeasuredDimension(width, height);
                final int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = getChildAt(i);
                    if (child.getVisibility() == GONE) {
                        continue;
                    }
                    if (child == edtUsername || child == edtPassword || child == edtPhone) {
                        int editWidth = width - Utils.dp(60) - (Utils.dp(16) * 3);
                        measureChildWithMargins(child, MeasureSpec.makeMeasureSpec(editWidth, MeasureSpec.getMode(widthMeasureSpec)), 0, heightMeasureSpec, 0);
                    } else {
                        measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                    }
                }
            }

            @Override
            protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                int childCount = getChildCount();
                int actionBarHeight = 0;
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
                    if (child == actionBar) {
                        actionBarHeight = height;
                    } else if (child == createTitle) {
                        childTop += actionBarHeight + Utils.dp(16);
                    } else if (child == imgAvatar) {
                        childTop += createTitle.getBottom() + Utils.dp(28f);
                    } else if (child == tvUsername) {
                        childTop += imgAvatar.getTop() + Utils.dp(4);
                        childLeft += imgAvatar.getRight() + Utils.dp(16);
                    } else if (child == edtUsername) {
                        childTop += tvUsername.getBottom();
                        childLeft += imgAvatar.getRight() + Utils.dp(16);
                    } else if (child == tvPassword) {
                        childLeft = tvUsername.getLeft();
                        childTop = edtUsername.getBottom() + Utils.dp(28);
                    } else if (child == tvShowHidePassword) {
                        childLeft = getMeasuredWidth() - width - Utils.dp(16);
                        childTop = tvPassword.getTop();
                    } else if (child == edtPassword) {
                        childLeft = tvUsername.getLeft();
                        childTop = tvPassword.getBottom();
                    } else if (child == tvPhone) {
                        childLeft = tvUsername.getLeft();
                        childTop = edtPassword.getBottom() + Utils.dp(28);
                    } else if (child == edtPhone) {
                        childLeft = tvUsername.getLeft();
                        childTop = tvPhone.getBottom();
                    }

                    child.layout(childLeft, childTop, childLeft + width, childTop + height);
                }
            }
        };
        mRootView.setBackgroundColor(Color.WHITE);
        actionBar = new ActionBar(getActivity());
        actionBar.setBackButtonDrawable(new BackDrawable());
        actionBar.setBackgroundColor(Color.WHITE);
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                super.onItemClick(id);
                if (id == android.R.id.home) {
                    getActivity().finish();
                }
            }
        });
        mRootView.addView(actionBar);
        createTitle = new TextView(getActivity());
        createTitle.setTextColor(0xff222222);
        createTitle.setText("Create a Sella username");
        createTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        createTitle.setIncludeFontPadding(false);
        createTitle.setTypeface(createTitle.getTypeface(), Typeface.BOLD);
        createTitle.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.LEFT, 16, 0, 16, 0));
        mRootView.addView(createTitle);

        imgAvatar = new ImageView(getActivity());
        imgAvatar.setImageResource(R.drawable.ic_photo_camera);
        imgAvatar.setLayoutParams(LayoutHelper.createFrame(60, 60, Gravity.LEFT, 16, 0, 16, 0));
        mRootView.addView(imgAvatar);

        tvUsername = new TextView(getActivity());
        tvUsername.setTextColor(0xff3e88de);
        tvUsername.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
        tvUsername.setText("Username");
        tvUsername.setIncludeFontPadding(false);
        tvUsername.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        mRootView.addView(tvUsername);

        edtUsername = new EditText(getActivity());
        edtUsername.setIncludeFontPadding(false);
        edtUsername.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        edtUsername.setTextColor(0xff222222);
        edtUsername.setHint("Username");
        edtUsername.setBackgroundResource(R.drawable.edit_text_bg);
        edtUsername.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.LEFT, 0, 0, 0, 16));
        mRootView.addView(edtUsername);

        tvPassword = new TextView(getActivity());
        tvPassword.setTextColor(0xff3e88de);
        tvPassword.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
        tvPassword.setText("Password");
        tvPassword.setIncludeFontPadding(false);
        tvPassword.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        mRootView.addView(tvPassword);

        tvShowHidePassword = new TextView(getActivity());
        tvShowHidePassword.setTextColor(0xff3e88de);
        tvShowHidePassword.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
        tvShowHidePassword.setText("Show");
        tvShowHidePassword.setIncludeFontPadding(false);
        tvShowHidePassword.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        mRootView.addView(tvShowHidePassword);
        tvShowHidePassword.setOnClickListener(this);

        edtPassword = new EditText(getActivity());
        edtPassword.setIncludeFontPadding(false);
        edtPassword.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        edtPassword.setTextColor(0xff222222);
        edtPassword.setHint("Enter password");
        edtPassword.setTransformationMethod(new PasswordTransformationMethod());
        edtPassword.setBackgroundResource(R.drawable.edit_text_bg);
        edtPassword.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.LEFT, 0, 0, 0, 16));
        mRootView.addView(edtPassword);

        tvPhone = new TextView(getActivity());
        tvPhone.setTextColor(0xff3e88de);
        tvPhone.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
        tvPhone.setText("Mobile number");
        tvPhone.setIncludeFontPadding(false);
        tvPhone.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        mRootView.addView(tvPhone);

        edtPhone = new EditText(getActivity());
        edtPhone.setIncludeFontPadding(false);
        edtPhone.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        edtPhone.setTextColor(0xff222222);
        edtPhone.setHint("Enter mobile number");
        edtPhone.setBackgroundResource(R.drawable.edit_text_bg);
        edtPhone.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.LEFT, 0, 0, 0, 16));
        mRootView.addView(edtPhone);

        btnSignUp = new TextView(getActivity());
        btnSignUp.setBackgroundResource(R.drawable.button_blue_with_radius);
        btnSignUp.setText("Sign Up");
        btnSignUp.setTextColor(0xffffffff);
        btnSignUp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        btnSignUp.setGravity(Gravity.CENTER);
        btnSignUp.setAllCaps(false);
        btnSignUp.setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, 43, Gravity.LEFT | Gravity.BOTTOM, 16, 0, 16, 16));
        mRootView.addView(btnSignUp);
        btnSignUp.setOnClickListener(this);

        return mRootView;
    }

    @Override
    public void onClick(View view) {
        if (view == tvShowHidePassword) {
            if (edtPassword != null) {
                if (edtPassword.getTransformationMethod() instanceof PasswordTransformationMethod) {
                    edtPassword.setTransformationMethod(null);
                    tvShowHidePassword.setText("Hide");
                } else {
                    edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                    tvShowHidePassword.setText("Show");
                }
                edtPassword.setSelection(edtPassword.getText().length());
            }
        }
    }
}
