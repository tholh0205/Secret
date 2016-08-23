package com.secret;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by tholh on 8/23/16.
 */
public class ApplicationLoader extends Application {

    public static Context applicationContext;
    public static Handler applicationHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        applicationHandler = new Handler(applicationContext.getMainLooper());
    }
}
