package com.vergilyn.retry.api.common;


import java.io.Serializable;
import java.util.List;
import java.util.function.Supplier;

public interface ApiRequest<T extends ApiResponse> extends Serializable {

    Class<T> responseClass();

    boolean check() throws ApiException;

    String toPostJsonBody();

    /**
     * 通用方法
     */
    default void addNotNull(List<String> target, String name, Supplier<Boolean> isNull){
        if (isNull.get()){
            target.add(name);
        }
    }
}
