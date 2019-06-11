package com.shu.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <h1>库存请求响应</h1>
 *
 * @author yang
 * @date 2019/6/11 20:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户没领取的优惠券模板信息
     */
    private List<PassTemplateInfo> passTemplateInfos;

}
