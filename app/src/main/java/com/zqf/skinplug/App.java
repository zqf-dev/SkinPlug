package com.zqf.skinplug;

import android.app.Application;

/**
 * Created by zqf on 2018/3/21.
 * Application
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getsInstance().init(this);
    }
}
