package com.jlnu.lang.realtimeinfo.application;

import android.app.Application;

/**
 * Created by qn on 2017/2/26.
 */

public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstance() {
        if (instance != null) {
            return instance;
        }
        return new App();
    }
}
