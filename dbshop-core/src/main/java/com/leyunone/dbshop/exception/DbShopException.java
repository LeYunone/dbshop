package com.leyunone.dbshop.exception;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2023-07-17
 */
public class DbShopException extends RuntimeException{

    static final long serialVersionUID = -7034897190745766939L;

    public DbShopException() {
        super();
    }

    public DbShopException(String message) {
        super(message);
    }

    public DbShopException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbShopException(Throwable cause) {
        super(cause);
    }

    protected DbShopException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
