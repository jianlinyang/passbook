package com.shu.passbook.vo;

import com.shu.passbook.entity.Merchants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>用户领取的优惠券信息</h1>
 *
 * @author yang
 * @date 2019/6/11 20:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassInfo {
    /**
     * 优惠券
     */
    private Pass pass;
    /**
     * 优惠券模板
     */
    private PassTemplate passTemplate;
    /**
     * 优惠券对应商户
     */
    private Merchants merchants;
}
