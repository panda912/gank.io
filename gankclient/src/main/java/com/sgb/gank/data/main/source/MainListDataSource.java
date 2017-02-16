package com.sgb.gank.data.main.source;

import com.sgb.gank.data.main.module.MainListResBody;

import java.util.List;

/**
 * Created by panda on 2016/11/17 上午10:46.
 */
public interface MainListDataSource {

    interface LoadDatasCallback {
        void onSuccess(List<MainListResBody.ResultsObj> list);

        void onFailure(String errStr);
    }

    void getDatas(String category, LoadDatasCallback callback);

    void saveDatas(String category, List<MainListResBody.ResultsObj> list);

}
