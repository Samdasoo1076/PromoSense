package com.skbroadband.doms.global.utils;

import java.util.function.Function;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.util
 * @File : ThrowingFunction
 * @Program :
 * @Date : 2023-01-16
 * @Comment :
 */
@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Throwable> {
    R apply(T t) throws E;

    static <T, R, E extends Throwable> Function<T, R> unchecked(ThrowingFunction<T, R, E> f) {
        return t -> {
            try {
                return f.apply(t);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }
}
