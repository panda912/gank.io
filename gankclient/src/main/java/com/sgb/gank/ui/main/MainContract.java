package com.sgb.gank.ui.main;

import com.sgb.gank.data.main.module.MainListResBody;
import com.sgb.gank.data.main.source.MainConstant;
import com.sgb.gank.ui.BasePresenter;
import com.sgb.gank.ui.BaseView;

import java.util.List;

/**
 * Created by panda on 16/8/26 下午4:11.
 */
public class MainContract {

    public interface View extends BaseView<Presenter> {
        void showLoading();

        void showDatas(List<MainListResBody.ResultsObj> list, boolean hasMore);

        void showNoResult();

        void showError(String msg);
    }

    public interface Presenter extends BasePresenter {
        void loadDatas(@MainConstant.Category String category, int count, int reqPage);
    }
}
