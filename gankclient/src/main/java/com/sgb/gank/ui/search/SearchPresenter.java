package com.sgb.gank.ui.search;

import com.sgb.gank.data.search.module.SearchListObj;
import com.sgb.gank.data.search.source.SearchRepository;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by panda on 2016/11/16 上午11:39.
 */
public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mSearchView;

    private SearchRepository mRepository;

    public SearchPresenter(SearchContract.View searchView, SearchRepository repository) {
        mSearchView = searchView;
        mRepository = repository;
//        mSearchView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadResults(String keyword) {
        mRepository.getSearchList(keyword)
                .subscribe(new Consumer<List<SearchListObj>>() {
                    @Override
                    public void accept(List<SearchListObj> searchList) throws Exception {
                        if (!searchList.isEmpty()) {
                            mSearchView.showResults(searchList);
                        } else {
                            mSearchView.showNoResult();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mSearchView.showError(throwable.getMessage());
                    }
                });
    }

    @Override
    public void cancelLoadTask() {

    }

    @Override
    public void openDetail(SearchListObj obj) {
        mSearchView.showDetail(obj.url);
    }

}
