package com.sgb.gank.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import static com.sgb.gank.data.main.source.MainListRepository.CATEGORY_IOS;

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
        requestData(CATEGORY_IOS, COUNT, mReqPage);
    }


}
