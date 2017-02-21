package com.sgb.gank.data.main.source.remote;

import com.sgb.gank.data.main.module.MainListResBody;
import com.sgb.gank.data.main.source.MainConstant;
import com.sgb.gank.data.main.source.MainDataSource;
import com.sgb.gank.net.RetrofitService;
import com.sgb.gank.net.exception.BizException;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by panda on 2017/2/21 下午2:57.
 */
public class MainRemoteDataSource implements MainDataSource {
    private static MainRemoteDataSource sInstance;

    private MainRemoteDataSource() {

    }

    public static MainRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new MainRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public Flowable<List<MainListResBody.ResultsObj>> getDatas(@MainConstant.Category String category, int count, int reqPage) {
        return RetrofitService.getInstance().createService(ApiService.class)
                .getDataList(category, count, reqPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<MainListResBody, Publisher<List<MainListResBody.ResultsObj>>>() {
                    @Override
                    public Publisher<List<MainListResBody.ResultsObj>> apply(MainListResBody resBody) throws Exception {
                        if (resBody.error) {
                            return Flowable.error(new BizException(resBody.msg));
                        }
                        return Flowable.just(resBody.results);
                    }
                });
    }

    @Override
    public void saveDatas(String category, List<MainListResBody.ResultsObj> list) {

    }
}
