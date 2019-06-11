package com.shu.passbook.constant;

/**<h1>优惠券状态</h1>
 * @author yang
 * @date 2019/6/11 13:43
 */
public enum PassStatus {
    /**
     * 优惠券状态
     */
    UNUSED(1,"未被使用的"),
    USED(2,"已使用的"),
    ALL(3,"全部领取的");

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态描述
     */
    private String desc;

    PassStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
