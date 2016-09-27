package com.demo.freecats;

import android.app.Application;
import android.content.Context;

/**
 * BaseApplication for project
 */
public class BaseApplication extends Application {
    private static Context gContext;

    @Override
    public void onCreate() {
        super.onCreate();
        gContext = this.getApplicationContext();
    }

    public static Context getGlobalContext() {
        return gContext;
    }

}
