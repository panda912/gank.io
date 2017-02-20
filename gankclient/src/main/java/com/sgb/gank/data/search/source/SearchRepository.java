package com.sgb.gank.data.search.source;

import com.sgb.gank.data.search.module.SearchListObj;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * Created by panda on 2017/2/13 上午11:35.
 */
public class SearchRepository implements SearchDataSource {

    private static SearchRepository sInstance;

    private SearchDataSource mRemoteDataSource;
    private SearchDataSource mLocalDataSource;

    private SearchRepository(SearchDataSource remoteDataSource, SearchDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    public static SearchRepository getInstance(SearchDataSource remoteDataSource, SearchDataSource localDataSource) {
        if (sInstance == null) {
            sInstance = new SearchRepository(remoteDataSource, localDataSource);
        }
        return sInstance;
    }

    @Override
    public Flowable<List<SearchListObj>> getSearchList(String keyword) {
        return mRemoteDataSource.getSearchList(keyword)
                .doOnNext(new Consumer<List<SearchListObj>>() {
                    @Override
                    public void accept(List<SearchListObj> searchList) throws Exception {
                        mLocalDataSource.saveSearchList(keyword, searchList);
                    }
                });
    }

    @Override
    public void saveSearchList(String keyword, List<SearchListObj> list) {
//        mLocalDataSource.saveSearchList(keyword, list);
    }

}
