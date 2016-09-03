package com.secret.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.secret.R;

/**
 * Created by ThoLH on 9/2/16.
 */
public class SignUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, new SignUpFragment()).commit();
        }
    }
}
