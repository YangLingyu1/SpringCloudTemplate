package com.lingyu.common;

import lombok.Data;

@Data
public class Result<T> {

    private int code;       // 响应码：200成功 500失败
    private String msg;     // 提示信息
    private T data;         // 返回数据

    // 成功
    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.setCode(200);
        r.setMsg("操作成功");
        r.setData(data);
        return r;
    }

    // 失败
    public static <T> Result<T> fail() {
        return fail("操作失败");
    }

    public static <T> Result<T> fail(String msg) {
        Result<T> r = new Result<>();
        r.setCode(500);
        r.setMsg(msg);
        r.setData(null);
        return r;
    }
}