package com.vance.asminject;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.vance.asminject.block.AppBlockCanaryContext;
import com.github.moduth.blockcanary.BlockCanary;


public class MyApplication extends Application {
    private static Context sContext;

    public static Context getAppContext() {
        return sContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

       // BlockCanary.install(this, new AppBlockCanaryContext()).start();
//        new MyBlockCanary().start();
        MatrixInitTask.init(this);

    }
}
