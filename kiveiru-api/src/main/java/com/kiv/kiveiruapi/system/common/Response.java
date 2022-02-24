package com.kiv.kiveiruapi.system.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private boolean success;
    private int code;
    private String msg;
    private Object data;

    public static Response success(Object data) {
        return new Response(true, 200, "success", data);
    }

    public static Response fail(int code, String msg) {
        return new Response(false, code, msg, null);
    }
}