package com.sgb.gank.ui.main;

import com.sgb.gank.data.main.module.MainListResBody;
import com.sgb.gank.data.main.source.MainConstant;
import com.sgb.gank.data.main.source.MainListRepository;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by panda on 2017/2/21 下午2:20.
 */
public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mMainView;
    private MainListRepository mRepository;

    public MainPresenter(MainContract.View mMainView, MainListRepository repository) {
        this.mMainView = mMainView;
        this.mRepository = repository;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void loadDatas(@MainConstant.Category String category, int count, int reqPage) {
        mRepository.getDatas(category, count, reqPage)
                .subscribe(new Consumer<List<MainListResBody.ResultsObj>>() {
                    @Override
                    public void accept(List<MainListResBody.ResultsObj> list) throws Exception {
                        mMainView.showDatas(list);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mMainView.showError(throwable.getMessage());
                    }
                });
    }
}
