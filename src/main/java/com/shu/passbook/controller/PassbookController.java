package com.shu.passbook.controller;

import com.shu.passbook.log.LogConstants;
import com.shu.passbook.log.LogGenerator;
import com.shu.passbook.service.IFeedbackService;
import com.shu.passbook.service.IGainPassTemplateService;
import com.shu.passbook.service.IInventoryService;
import com.shu.passbook.service.IUserPassService;
import com.shu.passbook.vo.Pass;
import com.shu.passbook.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <h1>Passbook RestController</h1>
 *
 * @author yang
 * @date 2019/6/12 12:37
 */
@RestController
@Slf4j
@RequestMapping("/passbook")
public class PassbookController {
    private final IUserPassService userPassService;
    private final IInventoryService iInventoryService;
    private final IGainPassTemplateService gainPassTemplateService;
    private final IFeedbackService feedbackService;
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public PassbookController(IUserPassService userPassService,
                              IInventoryService iInventoryService,
                              IGainPassTemplateService gainPassTemplateService,
                              IFeedbackService feedbackService,
                              HttpServletRequest httpServletRequest) {
        this.userPassService = userPassService;
        this.iInventoryService = iInventoryService;
        this.gainPassTemplateService = gainPassTemplateService;
        this.feedbackService = feedbackService;
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * <h2>获取用户优惠券信息</h2>
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception e
     */
    @GetMapping("/userpassinfo")
    public Response userPassInfo(Long userId) throws Exception {
        LogGenerator.genLog(httpServletRequest, userId, LogConstants.ActionName.USER_PASS_INFO, null);
        return userPassService.getUserPassInfo(userId);
    }

    /**
     * <h2>获取用户使用了的优惠券信息</h2>
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception e
     */
    @GetMapping("/userusedpassinfo")
    public Response userUsedPassInfo(Long userId) throws Exception {
        LogGenerator.genLog(httpServletRequest, userId, LogConstants.ActionName.USER_USED_PASS_INFO, null);
        return userPassService.getUserUsedPassInfo(userId);
    }

    /**
     * 用户使用优惠券
     *
     * @param pass {@link Pass}
     * @return {@link Response}
     */
    @PostMapping("/userusepass")
    public Response userUsePass(Pass pass) throws Exception {
        LogGenerator.genLog(httpServletRequest, pass.getUserId(), LogConstants.ActionName.USER_USE_PASS, pass);
        return userPassService.userUsePassInfo(pass);
    }

    /**
     * <h2>获取库存信息</h2>
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception e
     */
    @GetMapping("inventoryinof")
    Response inventoryInfo(Long userId) throws Exception {
        LogGenerator.genLog(httpServletRequest, userId, LogConstants.ActionName.INVENTORY_INFO, null);
        return iInventoryService.getInventoryInfo(userId);
    }
}
