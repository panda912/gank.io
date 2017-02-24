package com.sgb.gank.data.main.source;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by panda on 2017/2/21 下午2:10.
 */
public final class MainConstant {
    public static final String CATEGORY_ANDROID = "Android";
    public static final String CATEGORY_FRONTEND = "前端";
    public static final String CATEGORY_IOS = "iOS";
    public static final String CATEGORY_PIC = "福利";
    public static final String CATEGORY_VEDIO = "休息视频";
    public static final String CATEGORY_EXPAND_RES = "拓展资源";
    public static final String CATEGORY_RECOMMEND = "瞎推荐";
    public static final String CATEGORY_APP = "App";

    @StringDef({CATEGORY_ANDROID, CATEGORY_FRONTEND, CATEGORY_IOS, CATEGORY_VEDIO, CATEGORY_PIC, CATEGORY_EXPAND_RES, CATEGORY_RECOMMEND, CATEGORY_APP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Category {
    }

}
