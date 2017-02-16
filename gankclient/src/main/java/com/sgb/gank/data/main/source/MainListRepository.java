package com.sgb.gank.data.main.source;

import android.support.annotation.StringDef;

import com.sgb.gank.data.main.module.MainListResBody;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by panda on 2016/11/17 上午10:58.
 */
public class MainListRepository implements MainListDataSource {

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

    private static MainListRepository sInstance;

    private final MainListDataSource mLocalDataSource;
    private final MainListDataSource mRemoteDataSource;

    private MainListRepository(MainListDataSource mLocalDataSource, MainListDataSource mRemoteDataSource) {
        this.mLocalDataSource = mLocalDataSource;
        this.mRemoteDataSource = mRemoteDataSource;
    }

    public static MainListRepository getInstance(MainListDataSource localDataSource, MainListDataSource remoteDataSource) {
        if (sInstance == null) {
            sInstance = new MainListRepository(localDataSource, remoteDataSource);
        }
        return sInstance;
    }


    @Override
    public void getDatas(@Category String category, LoadDatasCallback callback) {
        switch (category) {
            case CATEGORY_ANDROID:
                break;
            case CATEGORY_IOS:
                break;
            case CATEGORY_MEITU:
                break;
            default:
                break;
        }
    }

    @Override
    public void saveDatas(String category, List<MainListResBody.ResultsObj> list) {

    }

}
