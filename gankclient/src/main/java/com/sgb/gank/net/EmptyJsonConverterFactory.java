package com.sgb.gank.net;

import java.io.EOFException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 空json字符串处理
 * <p>
 * Created by panda on 16/9/7 下午12:00.
 */
public class EmptyJsonConverterFactory extends Converter.Factory {

    public static EmptyJsonConverterFactory create(GsonConverterFactory gsonConverterFactory) {
        return new EmptyJsonConverterFactory(gsonConverterFactory);
    }

    private GsonConverterFactory mGsonConverterFactory;

    private EmptyJsonConverterFactory(GsonConverterFactory gsonConverterFactory) {
        this.mGsonConverterFactory = gsonConverterFactory;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Converter<ResponseBody, ?> delegateConverter = mGsonConverterFactory.responseBodyConverter(type, annotations, retrofit);
        return new Converter<ResponseBody, Object>() {
            @Override
            public Object convert(ResponseBody value) throws IOException {
                try {
                    return delegateConverter.convert(value);
                } catch (EOFException e) {
                    return null;
                }
            }
        };
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return mGsonConverterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }
}
