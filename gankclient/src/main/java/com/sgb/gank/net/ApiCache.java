package com.sgb.gank.net;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by panda on 2016/11/17 下午1:53.
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface ApiCache {
    String name();

    long duration();
}
