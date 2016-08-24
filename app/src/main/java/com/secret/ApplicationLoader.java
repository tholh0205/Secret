package com.secret;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;

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

        ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(applicationContext);
        DiskCacheConfig.Builder diskCacheBuilder = DiskCacheConfig.newBuilder(applicationContext);
        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/Secret");
        diskCacheBuilder.setBaseDirectoryPath(file);
        diskCacheBuilder.setBaseDirectoryName("cache");
        configBuilder.setMainDiskCacheConfig(diskCacheBuilder.build());
        Fresco.initialize(applicationContext, configBuilder.build());
    }
}
