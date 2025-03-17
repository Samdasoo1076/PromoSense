package com.skbroadband.doms.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author : 안진갑
 * @Project : SKB_WEB
 * @Package : com.skbroadband.doms.global.dto
 * @File : Response
 * @Program :
 * @Date : 2022-11-24
 * @Comment :
 */
@Getter
public class Response<T> {
    private final int code;
    private final String status;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    @Builder
    private Response(HttpStatus httpsStatus, String message, T data) {
        this.code = httpsStatus.value();
        this.status = httpsStatus.name();
        this.message = message;
        this.data = data;
    }

    public static <E> ResponseEntity<Response<E>> ok() {
        return ResponseEntity.ok().body(new Response<E>(HttpStatus.OK, "success", null));
    }

    public static <E> ResponseEntity<Response<E>> of(HttpStatus httpStatus) {
        return ResponseEntity.ok().body(new Response<E>(httpStatus, "success", null));
    }

    public static <E> ResponseEntity<Response<E>> of(E data) {
        return ResponseEntity.ok().body(new Response<E>(HttpStatus.OK, "success", data));
    }

    public static <E> ResponseEntity<Response<E>> fail() {
        return ResponseEntity.ok().body(new Response<E>(HttpStatus.OK, "fail", null));
    }

    public static <E> ResponseEntity<Response<E>> ofFail(HttpStatus httpStatus) {
        return ResponseEntity.ok().body(new Response<E>(httpStatus, "fail", null));
    }

    public static <E> ResponseEntity<Response<E>> ofFail(String message) {
        return ResponseEntity.ok().body(new Response<E>(HttpStatus.OK, message, null));
    }
}
