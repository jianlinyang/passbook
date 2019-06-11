package com.shu.passbook.service;

import com.shu.passbook.vo.Response;
import com.shu.passbook.vo.User;

/**
 * <h1>用户服务接口</h1>
 *
 * @author yang
 * @date 2019/6/11 19:24
 */
public interface IUserService {
    /**
     * <h2>创建用户</h2>
     *
     * @param user {@link User}
     * @return {@link Response}
     */
    Response createUser(User user) throws Exception;
}
