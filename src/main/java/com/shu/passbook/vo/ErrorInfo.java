package com.shu.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>统一错误信息</h1>
 *
 * @author yang
 * @date 2019/6/11 16:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfo<T> {
    /**
     * 错误码
     */
    public static final Integer ERROR = -1;
    /**
     * 特定错误码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 请求url
     */
    private String url;
    /**
     * 请求返回的数据
     */
    private T data;
}
