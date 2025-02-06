package com.leyunone.dbshop.system;

import com.leyunone.dbshop.bean.DataResponse;
import com.leyunone.dbshop.exception.DbShopException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理器
**/
@RestControllerAdvice
public class DbshopRestExceptionAdvice {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(DbShopException.class)
    public DataResponse serviceException(DbShopException e){
        return DataResponse.buildFailure(e.getMessage());
    }

}
