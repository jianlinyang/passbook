package com.shu.passbook.service;

import com.shu.passbook.vo.PassTemplate;

/**
 * <h1>Pass Hbase 服务</h1>
 *
 * @author yang
 * @date 2019/6/11 16:34
 */
public interface IHbasePassService {
    /**
     * <h2>将PassTemplate 写入Hbase</h2>
     *
     * @param passTemplate {@link PassTemplate}
     * @return true/false
     */
    boolean dropPassTemplate(PassTemplate passTemplate);
}
