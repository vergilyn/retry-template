package com.vergilyn.retry.api.common;

import java.io.Serializable;

public interface ApiResponse extends Serializable {

    /**
     * true, 一般指：1) 接口请求成功 2) 接口文档中表明的成功。
     */
    boolean isSuccess();

    default String getErrorCode(){
        return null;
    };

    default String getErrorMessage(){
        return null;
    };
}
