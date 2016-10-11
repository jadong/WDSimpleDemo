package com.dong.app;

import android.app.Application;
import android.content.Context;

import com.antfortune.freeline.FreelineCore;

/**
 * Created by zengwendong on 16/7/19.
 */
public class WDApplication extends Application {

    private static Application instance;
    private static Context context;

    public static Application getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FreelineCore.init(this);
        instance = this;
        context = getApplicationContext();
    }

}
