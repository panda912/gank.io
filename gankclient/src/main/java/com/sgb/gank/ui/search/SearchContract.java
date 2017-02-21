package com.sgb.gank.ui.search;

import com.sgb.gank.ui.BasePresenter;
import com.sgb.gank.ui.BaseView;
import com.sgb.gank.data.search.module.SearchListObj;

import java.util.List;

/**
 * Created by panda on 2016/11/16 上午11:34.
 */
public class SearchContract {

    interface View extends BaseView<Presenter> {
        void showResults(List<SearchListObj> list);

        void showNoResult();

        void showError(String msg);

        void showDetail(String url);
    }

    interface Presenter extends BasePresenter {
        void loadResults(String keyword);

        void cancelLoadTask();

        void openDetail(SearchListObj obj);
    }
}
