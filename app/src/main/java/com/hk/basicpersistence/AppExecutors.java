package com.hk.basicpersistence;

import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.os.Handler;

public class AppExecutors {

    private final Executor mDiskIO;
    private final Executor mNetworkIO;
    private final Executor mMainThread;

    public AppExecutors(Executor mDiskIO, Executor mNetworkIO, Executor mMainThread) {
        this.mDiskIO = mDiskIO;
        this.mNetworkIO = mNetworkIO;
        this.mMainThread = mMainThread;
    }

    public AppExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3), new MainThreadExecutor());
    }

    public Executor diskIO(){
        return mDiskIO;
    }

    public Executor getNetworkIO() {
        return mNetworkIO;
    }

    public Executor getMainThread() {
        return mMainThread;
    }

    private static class MainThreadExecutor implements Executor {

        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {

        }
    }
}
