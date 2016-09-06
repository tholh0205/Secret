package com.secret.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.secret.R;
import com.secret.actionbar.ActionBar;
import com.secret.ui.dialogs.SetLocationDialog;
import com.secret.ui.drawables.BackDrawable;

/**
 * Created by ThoLH on 9/2/16.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener {

    private View mRootView;
    private ActionBar actionBar;
    private ImageView imgAvatar;
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtPhone;
    private TextView tvShowHidePassword;
    private TextView btnSignUp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.sign_up_fragment, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actionBar = (ActionBar) view.findViewById(R.id.secret_action_bar);
        actionBar.setBackButtonDrawable(new BackDrawable());
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                super.onItemClick(id);
                if (id == android.R.id.home) {
                    getActivity().finish();
                }
            }
        });
        imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
        edtUsername = (EditText) view.findViewById(R.id.edt_username);
        edtPassword = (EditText) view.findViewById(R.id.edt_password);
        edtPassword.setTransformationMethod(new PasswordTransformationMethod());
        edtPhone = (EditText) view.findViewById(R.id.edt_phone);
        tvShowHidePassword = (TextView) view.findViewById(R.id.tv_show_hide_password);
        tvShowHidePassword.setOnClickListener(this);
        btnSignUp = (TextView) view.findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(this);
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
        } else if (view == btnSignUp) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new SetLocationDialog(getActivity()).show();
    }
}
