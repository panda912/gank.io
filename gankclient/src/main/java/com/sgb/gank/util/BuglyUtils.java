package com.sgb.gank.util;

import android.content.Context;

import com.sgb.gank.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by panda on 16/9/18 上午11:56.
 */
public class BuglyUtils {

    private static volatile BuglyUtils sInstance;

    private BuglyUtils() {
    }

    public static BuglyUtils getInstance() {
        if (sInstance == null) {
            synchronized (BuglyUtils.class) {
                if (sInstance == null) {
                    sInstance = new BuglyUtils();
                }
            }
        }
        return sInstance;
    }

    public void initBugly(Context context) {
        CrashReport.initCrashReport(context.getApplicationContext(), "900054026", BuildConfig.DEBUG);
    }
}
