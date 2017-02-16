package com.sgb.gank.data.search.source;

import com.sgb.gank.data.search.module.SearchListObj;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by panda on 2017/2/13 上午11:36.
 */
public interface SearchDataSource {

    interface SearchCallback {
        void onSuccess(List<SearchListObj> list);

        void onFailure(String msg);
    }

    @Deprecated
    void getSearchList(String keyword, SearchCallback callback);

    Flowable<List<SearchListObj>> getSearchList(String keyword);

    void saveSearchList(String keyword, List<SearchListObj> list);

}
