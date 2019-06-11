package com.shu.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <h2>用户领取的优惠券</h2>
 *
 * @author yang
 * @date 2019/6/11 14:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pass {
    private Long userId;
    private String rowKey;
    private String templateId;
    private String token;
    private Date assignedDate;
    private Date consumerDate;
}
