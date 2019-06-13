package com.shu.passbook.controller;

import com.shu.passbook.log.LogConstants;
import com.shu.passbook.log.LogGenerator;
import com.shu.passbook.service.IUserService;
import com.shu.passbook.vo.Response;
import com.shu.passbook.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>创建用户服务</h1>
 *
 * @author yang
 * @date 2019/6/12 13:10
 */
@RestController
@Slf4j
@RequestMapping("/passbook")
public class CreateUserController {
    private final IUserService userService;

    private final HttpServletRequest httpServletRequest;

    public CreateUserController(IUserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.httpServletRequest = request;
    }

    /**
     * <h2>创建用户</h2>
     *
     * @param user {@link User}
     * @return {@link Response}
     * @throws Exception e
     */
    @PostMapping("createuser")
    public Response createUser(@RequestBody User user) throws Exception {
        LogGenerator.genLog(httpServletRequest, -1L, LogConstants.ActionName.CREATE_USER, user);
        return userService.createUser(user);
    }

}
