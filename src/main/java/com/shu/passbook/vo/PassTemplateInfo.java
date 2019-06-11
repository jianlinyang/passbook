package com.shu.passbook.vo;

import com.shu.passbook.entity.Merchants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <h1>优惠券模板信息</h1>
 *
 * @author yang
 * @date 2019/6/11 19:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassTemplateInfo extends PassTemplate {
    /**
     * 优惠券模板
     */
    private PassTemplate passTemplate;
    /**
     * 优惠券对应商户
     */
    private Merchants merchants;

}
