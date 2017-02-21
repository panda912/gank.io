package com.sgb.gank.data.main.source;

import com.sgb.gank.data.main.module.MainListResBody;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * Created by panda on 2016/11/17 上午10:58.
 */
public class MainListRepository implements MainDataSource {
    private static MainListRepository sInstance;

    private final MainDataSource mLocalDataSource;
    private final MainDataSource mRemoteDataSource;

    private MainListRepository(MainDataSource mLocalDataSource, MainDataSource mRemoteDataSource) {
        this.mLocalDataSource = mLocalDataSource;
        this.mRemoteDataSource = mRemoteDataSource;
    }

    public static MainListRepository getInstance(MainDataSource localDataSource, MainDataSource remoteDataSource) {
        if (sInstance == null) {
            sInstance = new MainListRepository(localDataSource, remoteDataSource);
        }
        return sInstance;
    }

    @Override
    public Flowable<List<MainListResBody.ResultsObj>> getDatas(@MainConstant.Category String category, int count, int reqPage) {
        switch (category) {
            case MainConstant.CATEGORY_ANDROID:
                Flowable<List<MainListResBody.ResultsObj>> local = mLocalDataSource.getDatas(category, count, reqPage);
                Flowable<List<MainListResBody.ResultsObj>> remote = mRemoteDataSource.getDatas(category, count, reqPage)
                        .doOnNext(new Consumer<List<MainListResBody.ResultsObj>>() {
                            @Override
                            public void accept(List<MainListResBody.ResultsObj> list) throws Exception {
                                mLocalDataSource.saveDatas(category, list);
                            }
                        });
                return Flowable.concat(remote, local)
                        .take(1);
            case MainConstant.CATEGORY_IOS:
                return mRemoteDataSource.getDatas(category, count, reqPage);
            case MainConstant.CATEGORY_MEITU:
                return mRemoteDataSource.getDatas(category, count, reqPage);
            default:
                return mRemoteDataSource.getDatas(category, count, reqPage);
        }
    }

    @Override
    public void saveDatas(String category, List<MainListResBody.ResultsObj> list) {

    }

}
