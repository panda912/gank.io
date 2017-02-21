package com.sgb.gank.data.main.source;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by panda on 2017/2/21 下午2:10.
 */
public final class MainConstant {
    public static final String CATEGORY_ANDROID = "Android";
    public static final String CATEGORY_IOS = "iOS";
    public static final String CATEGORY_VEDIO = "休息视频";
    public static final String CATEGORY_MEITU = "福利";
    public static final String CATEGORY_ZIYUANTUOZHAN = "拓展资源";
    public static final String CATEGORY_QIANDUAN = "前端";
    public static final String CATEGORY_XIATUIJIAN = "瞎推荐";
    public static final String CATEGORY_APP = "App";

    @StringDef({CATEGORY_ANDROID, CATEGORY_IOS, CATEGORY_VEDIO, CATEGORY_MEITU, CATEGORY_ZIYUANTUOZHAN, CATEGORY_QIANDUAN, CATEGORY_XIATUIJIAN, CATEGORY_APP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Category {
    }

}
