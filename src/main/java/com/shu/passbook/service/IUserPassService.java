package com.shu.passbook.service;

import com.shu.passbook.vo.Pass;
import com.shu.passbook.vo.Response;

/**
 * <h1>获取用户个人优惠券信息</h1>
 *
 * @author yang
 * @date 2019/6/11 20:11
 */
public interface IUserPassService {
    /**
     * <h2>获取用户个人优惠券信息,及我的优惠券功能实现</h2>
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception 异常
     */
    Response getUserPassInfo(Long userId) throws Exception;

    /**
     * <h2>获取已使用的优惠券信息</h2>
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception 异常
     */
    Response getUserUsedPassInfo(Long userId) throws Exception;

    /**
     * <h2>获取所有的优惠券信息</h2>
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception 异常
     */
    Response getUserAllPassInfo(Long userId) throws Exception;

    /**
     * <h2>用户使用一张优惠券</h2>
     *
     * @param pass {@link Pass}
     * @return {@link Response}
     * @throws Exception 异常
     */
    Response userUsePassInfo(Pass pass) throws Exception;
}
