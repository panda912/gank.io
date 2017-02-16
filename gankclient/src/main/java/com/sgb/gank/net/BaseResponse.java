package com.sgb.gank.net;

/**
 * Created by panda on 16/9/7 上午10:20.
 */
public class BaseResponse<T> {
    public boolean error;
    public String msg;

    public T results;

    @Override
    public String toString() {
        return "BaseResponse{" +
                "error=" + error +
                ", msg='" + msg + '\'' +
                ", results=" + results +
                '}';
    }
}
