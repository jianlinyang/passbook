package com.shu.passbook.service;

import com.shu.passbook.vo.Response;

/**
 * <h1>获取库存信息:只返回用户未领取的</h1>
 *
 * @author yang
 * @date 2019/6/11 20:06
 */
public interface IInventoryService {
    /**
     * <h2>获取库存信息</h2>
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception 异常
     */
    Response getInventoryInfo(Long userId) throws Exception;
}
