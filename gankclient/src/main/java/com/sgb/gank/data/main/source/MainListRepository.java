package com.sgb.gank.data.main.source;

import com.sgb.gank.data.main.module.MainListResBody;

import java.util.List;

import io.reactivex.Flowable;

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
        Flowable<List<MainListResBody.ResultsObj>> local = mLocalDataSource.getDatas(category, count, reqPage);
        Flowable<List<MainListResBody.ResultsObj>> remote = mRemoteDataSource.getDatas(category, count, reqPage)
                .doOnNext(list -> mLocalDataSource.saveDatas(category, list));
        return Flowable.concat(local, remote)
                .filter(list -> list != null && !list.isEmpty())
                .take(1);
    }

    @Override
    public void saveDatas(String category, List<MainListResBody.ResultsObj> list) {

    }

}
