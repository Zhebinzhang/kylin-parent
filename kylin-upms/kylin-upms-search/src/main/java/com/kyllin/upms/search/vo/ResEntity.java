package com.kyllin.upms.search.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResEntity implements Serializable {

    private Integer code;

    private String msg;

    private Object object;

    private ResEntity() {
    }

    private ResEntity(Integer code, String msg, Object object) {
        this.code = code;
        this.msg = msg;
        this.object = object;
    }

    public static ResEntity ok() {
        return new ResEntity(200, "操作成功", null);
    }

    public static ResEntity ok(String msg, Object obj) {
        return new ResEntity(200, msg, obj);
    }

    public static ResEntity ok(Object object) {
        return new ResEntity(200, "操作成功", object);
    }

    public static ResEntity error() {
        return new ResEntity(500, "操作失败", null);

    }

    public static ResEntity error(String msg, Object obj) {
        return new ResEntity(500, msg, obj);
    }

    public static ResEntity error(Object object) {
        return new ResEntity(500, "操作失败", object);
    }
}