package com.sgb.gank;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.sgb.gank.util.AppUtils;
import com.sgb.gank.util.BuglyUtils;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Created by panda on 16/9/2 下午4:05.
 */
public class GankApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static final String DEBUG = GankApplication.class.getSimpleName();

    private static GankApplication sInstance;

    public static GankApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

//        Realm.init(this);
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .name("gank.realm")
//                .build();
//        Realm.setDefaultConfiguration(config);

        String processName = AppUtils.getProcessName(this);
        if (BuildConfig.APPLICATION_ID.equalsIgnoreCase(processName)) {
            BuglyUtils.getInstance().initBugly(this);
            HermesEventBus.getDefault().init(this);
            Stetho.initializeWithDefaults(this);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        trackActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        trackActivity(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        trackActivity(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        trackActivity(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        trackActivity(activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        trackActivity(activity);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        trackActivity(activity);
    }

    private void trackActivity(Activity activity) {
        if (BuildConfig.DEBUG) {
            Log.d(DEBUG, Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + activity.getLocalClassName());
        }
    }
}
