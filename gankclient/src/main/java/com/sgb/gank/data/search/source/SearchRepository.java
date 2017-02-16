package com.sgb.gank.data.search.source;

import com.sgb.gank.data.search.module.SearchListObj;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposables;
import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * Created by panda on 2017/2/13 上午11:35.
 */
public class SearchRepository implements SearchDataSource {

    private static SearchRepository sInstance;

    private SearchDataSource mRemoteDataSource;

    private SearchRepository(SearchDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
    }

    public static SearchRepository getInstance(SearchDataSource remoteDataSource) {
        if (sInstance == null) {
            sInstance = new SearchRepository(remoteDataSource);
        }
        return sInstance;
    }

    @Override
    public Flowable<List<SearchListObj>> getSearchList(String keyword) {

//        mRemoteDataSource.getSearchList(keyword)
//                .flatMap(new Function<List<SearchListObj>, Publisher<List<SearchListObj>>>() {
//                    @Override
//                    public Publisher<List<SearchListObj>> apply(List<SearchListObj> searchList) throws Exception {
//                        Flowable.fromIterable(searchList)
//                                .doOnNext(new Consumer<SearchListObj>() {
//                                    @Override
//                                    public void accept(SearchListObj searchListObj) throws Exception {
//
//                                    }
//                                });
//                    }
//                });

        return mRemoteDataSource.getSearchList(keyword);
    }


    private Flowable<Realm> getRealm() {
        return Flowable.create(new FlowableOnSubscribe<Realm>() {
            @Override
            public void subscribe(FlowableEmitter<Realm> emitter) throws Exception {
                Realm observableRealm = Realm.getDefaultInstance();

                final RealmChangeListener<Realm> listener = new RealmChangeListener<Realm>() {
                    @Override
                    public void onChange(Realm realm) {
                        emitter.onNext(realm);
                    }
                };
                emitter.setDisposable(Disposables.fromRunnable(new Runnable() {
                    @Override
                    public void run() {
                        observableRealm.removeChangeListener(listener);
                        observableRealm.close();
                    }
                }));
                observableRealm.addChangeListener(listener);
                emitter.onNext(observableRealm);
            }
        }, BackpressureStrategy.LATEST);
    }


    @Override
    public void saveSearchList(String keyword, List<SearchListObj> list) {
        mRemoteDataSource.saveSearchList(keyword, list);
    }

    @Override
    public void getSearchList(String keyword, SearchCallback callback) {
        mRemoteDataSource.getSearchList(keyword, new SearchCallback() {
            @Override
            public void onSuccess(List<SearchListObj> list) {
                callback.onSuccess(list);

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

}
