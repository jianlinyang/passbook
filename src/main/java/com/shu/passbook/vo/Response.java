package com.shu.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>Controller 统一响应</h1>
 *
 * @author yang
 * @date 2019/6/11 19:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    /**
     * 错误码,正确返回0
     */
    private Integer errorCode = 0;
    /**
     * 错误信息
     */
    private String errorMsg = "";
    /**
     * 返回值对象
     */
    private Object data;

    /**
     * 正确响应
     *
     * @param data 响应数据
     */
    public Response(Object data) {
        this.data = data;
    }

    /**
     * 空响应
     */
    public static Response success() {
        return new Response();
    }

    /**
     * error响应
     */
    public static Response failure(String errorMsg) {
        return new Response(-1, errorMsg, null);
    }
}

