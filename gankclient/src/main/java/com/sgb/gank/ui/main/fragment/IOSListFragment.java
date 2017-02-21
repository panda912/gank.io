package com.sgb.gank.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sgb.gank.data.main.module.MainListResBody;
import com.sgb.gank.data.main.source.MainConstant;

import java.util.List;

/**
 * Created by panda on 2016/10/21 下午1:43.
 */
public class IOSListFragment extends BaseMainListFragment {

    private static final int COUNT = 15;

    public IOSListFragment() {
    }

    public static IOSListFragment newInstance() {
        return new IOSListFragment();
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void request() {
        requestData(MainConstant.CATEGORY_IOS, COUNT, mReqPage);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void showDatas(List<MainListResBody.ResultsObj> list) {

    }

    @Override
    public void showNoResult() {

    }

    @Override
    public void showError(String msg) {

    }
}
