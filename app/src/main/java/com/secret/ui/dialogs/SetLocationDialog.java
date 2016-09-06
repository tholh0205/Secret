package com.secret.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.secret.R;

/**
 * Created by ThoLH on 9/6/16.
 */
public class SetLocationDialog extends Dialog {

    public SetLocationDialog(Context context) {
        super(context);
    }

    public SetLocationDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SetLocationDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_location_dialog_layout);
    }
}
