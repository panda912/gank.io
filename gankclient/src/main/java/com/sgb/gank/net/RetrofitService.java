package com.sgb.gank.net;

import android.text.TextUtils;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.sgb.gank.BuildConfig;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by panda on 16/8/25 下午5:08.
 */
public class RetrofitService {

    private static final int CONNECT_TIMEOUT = 30;
    private static final int READ_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 30;

    private static volatile RetrofitService sInstance = null;
    private static volatile RetrofitService sSyncInstance = null;

    private Retrofit mRetrofit;

    private RetrofitService(boolean useRxJava) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_HOST)
                .addConverterFactory(EmptyJsonConverterFactory.create(GsonConverterFactory.create()))
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(getOkHttpClient());

        if (useRxJava) {
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()));
        }

        mRetrofit = builder.build();
    }

    public static RetrofitService getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitService.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitService(true);
                }
            }
        }
        return sInstance;
    }

    public static RetrofitService getSyncInstance() {
        if (sSyncInstance == null) {
            synchronized (RetrofitService.class) {
                if (sSyncInstance == null) {
                    sSyncInstance = new RetrofitService(false);
                }
            }
        }
        return sSyncInstance;
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .followRedirects(true)
                .hostnameVerifier((hostname, session) -> TextUtils.equals(BuildConfig.API_HOST, hostname))
                .cookieJar(new CookieJar() { //一般情况下host相同，说明是同一个站点下的，cookie共用，所以使用host作为保存cookie的key

                    HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(HttpUrl.parse(url.host()), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(HttpUrl.parse(url.host()));
                        return cookies != null ? cookies : Collections.emptyList();
                    }
                })
                .addNetworkInterceptor(new StethoInterceptor());

        //request header拦截器
        RequestHeaderInterceptor headerInterceptor = new RequestHeaderInterceptor();
        //log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        builder.addNetworkInterceptor(headerInterceptor)
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor);

        return builder.build();
    }

    public <T> T createService(Class<T> service) {
        return mRetrofit.create(service);
    }

}
