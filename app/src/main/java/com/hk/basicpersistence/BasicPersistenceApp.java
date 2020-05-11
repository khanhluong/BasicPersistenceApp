package com.hk.basicpersistence;

import android.app.Application;

public class BasicPersistenceApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();
    }
}
