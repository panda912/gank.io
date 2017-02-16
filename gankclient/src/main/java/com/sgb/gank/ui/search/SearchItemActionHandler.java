package com.sgb.gank.ui.search;

import com.sgb.gank.data.search.module.SearchListObj;

/**
 * Created by panda on 2016/11/16 下午5:00.
 */
public class SearchItemActionHandler {

    private SearchContract.Presenter mPresenter;

    public SearchItemActionHandler(SearchContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void onItemClick(SearchListObj obj) {
        mPresenter.openDetail(obj);
    }
}
