package com.sgb.gank.data.main.source.local;

import com.sgb.gank.data.main.module.MainListResBody;
import com.sgb.gank.data.main.source.MainConstant;
import com.sgb.gank.data.main.source.MainDataSource;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by panda on 2017/2/21 下午2:58.
 */
public class MainLocalDataSource implements MainDataSource {
    private static MainLocalDataSource sInstance;

    private MainLocalDataSource() {

    }

    public static MainLocalDataSource getInstance() {
        if(sInstance == null) {
            sInstance = new MainLocalDataSource();
        }
        return sInstance;
    }

    @Override
    public Flowable<List<MainListResBody.ResultsObj>> getDatas(@MainConstant.Category String category, int count, int reqPage) {
        return null;
    }

    @Override
    public void saveDatas(String category, List<MainListResBody.ResultsObj> list) {

    }
}
