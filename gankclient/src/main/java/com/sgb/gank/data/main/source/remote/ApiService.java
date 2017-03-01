package com.sgb.gank.data.main.source.remote;

import com.sgb.gank.data.main.module.MainListResBody;
import com.sgb.gank.net.ApiCache;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by panda on 16/9/2 下午4:26.
 */
public interface ApiService {

    /**
     * 列表数据
     *
     * @param category all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * @param count    每页数据条数
     * @param reqPage  请求页数
     * @return
     */
    @ApiCache(name = "mainlist", duration = 60)
    @GET("data/{category}/{count}/{page}")
    Flowable<MainListResBody> getDataList(@Path("category") String category,
                                          @Path("count") int count,
                                          @Path("page") int reqPage);


    @ApiCache(name = "ioslist", duration = 60)
    @GET("data/iOS/{count}/{page}")
    Flowable<MainListResBody> getIOSDataList(@Path("count") int count,
                                             @Path("page") int reqPage);
}
