package com.shu.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>用户领取优惠券的请求对象</h1>
 *
 * @author yang
 * @date 2019/6/11 20:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GainPassTemplateRequest {
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * passTemplate
     */
    private PassTemplate passTemplate;
}
