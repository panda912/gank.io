package com.sgb.gank.net;

import android.os.Build;

import com.sgb.gank.BuildConfig;
import com.sgb.gank.GankApplication;
import com.sgb.gank.util.AppUtils;
import com.sgb.gank.util.NetworkUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by panda on 16/9/2 下午2:57.
 */
class RequestHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(request.newBuilder()
                .method(request.method(), request.body())
                .addHeader("Mobile", Build.BRAND + " " + Build.MODEL + " " + Build.VERSION.RELEASE)
                .addHeader("DeviceId", AppUtils.getDeviceID(GankApplication.getInstance()))
                .addHeader("Operator", NetworkUtils.getSimName(GankApplication.getInstance()))
                .addHeader("Network", NetworkUtils.getTypeName(GankApplication.getInstance()))
                .addHeader("MAC", AppUtils.getMacAddress(GankApplication.getInstance()))
                .addHeader("IP", NetworkUtils.getPdsnIp())
                .addHeader("AppVersion", BuildConfig.VERSION_NAME)
                .addHeader("FLAVOR", BuildConfig.FLAVOR)
                .build());
    }
}
