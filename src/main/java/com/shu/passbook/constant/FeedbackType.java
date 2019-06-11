package com.shu.passbook.constant;

/**
 * <h1>评论类型枚举</h1>
 *
 * @author yang
 * @date 2019/6/11 13:46
 */
public enum FeedbackType {
    /**
     * 评论类型
     */
    PASS(1, "针对优惠券评论"),
    APP(2, "针对卡包App评论");
    /**
     * 评论类型码
     */
    private Integer code;
    /**
     * 评论类型描述
     */
    private String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    FeedbackType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
