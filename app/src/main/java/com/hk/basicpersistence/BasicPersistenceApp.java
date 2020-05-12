package com.hk.basicpersistence;

import android.app.Application;

import com.hk.basicpersistence.db.AppDatabase;

import com.example.android.persistence.DataRepository;

public class BasicPersistenceApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
