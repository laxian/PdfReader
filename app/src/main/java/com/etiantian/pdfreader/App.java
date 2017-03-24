package com.etiantian.pdfreader;

import android.app.Application;

/**
 * Created by zhouweixian on 2017/3/24
 */

public class App extends Application {
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
