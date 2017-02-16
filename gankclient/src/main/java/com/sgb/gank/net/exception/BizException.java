package com.sgb.gank.net.exception;

/**
 * Created by panda on 16/9/7 上午11:23.
 */
public class BizException extends Exception {

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }
}
