package com.shu.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <h1>投放的优惠券对象定义</h1>
 *
 * @author yang
 * @date 2019/6/11 14:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassTemplate {
    private Integer id;
    private String title;
    private String summary;
    private String desc;
    private Long limit;
    private Boolean hasToken;
    private Integer background;
    private Date start;
    private Date end;
}
