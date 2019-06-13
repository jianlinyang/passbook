package com.shu.passbook.controller;

import com.shu.passbook.log.LogConstants;
import com.shu.passbook.log.LogGenerator;
import com.shu.passbook.service.IFeedbackService;
import com.shu.passbook.service.IGainPassTemplateService;
import com.shu.passbook.service.IInventoryService;
import com.shu.passbook.service.IUserPassService;
import com.shu.passbook.vo.Feedback;
import com.shu.passbook.vo.GainPassTemplateRequest;
import com.shu.passbook.vo.Pass;
import com.shu.passbook.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Response inventoryInfo(Long userId) throws Exception {
        LogGenerator.genLog(httpServletRequest, userId, LogConstants.ActionName.INVENTORY_INFO, null);
        return iInventoryService.getInventoryInfo(userId);
    }

    /**
     * <h2>用户领取优惠券</h2>
     *
     * @param request {@link GainPassTemplateRequest}
     * @return {@link Response}
     * @throws Exception e
     */
    @PostMapping("/gainpasstemplate")
    public Response gainPassTemplate(@RequestBody GainPassTemplateRequest request) throws Exception {
        LogGenerator.genLog(httpServletRequest, request.getUserId(), LogConstants.ActionName.GAIN_PASS_TEMPLATE, request);
        return gainPassTemplateService.gainPassTemplate(request);
    }

    /**
     * <h2>用户创建评论</h2>
     *
     * @param feedback {@link Feedback}
     * @return {@link RequestBody}
     */
    @PostMapping("/createfeedback")
    public Response createFeedback(@RequestBody Feedback feedback) {
        LogGenerator.genLog(httpServletRequest, feedback.getUserId(), LogConstants.ActionName.CREATE_FEEDBACK, feedback);
        return feedbackService.createFeedback(feedback);
    }

    /**
     * <h2>获取用户评论</h2>
     *
     * @param useId 用户id
     * @return {@link Response}
     */
    @GetMapping("/getfeedback")
    public Response getFeedback(Long useId) {
        LogGenerator.genLog(httpServletRequest, useId, LogConstants.ActionName.GET_FEEDBACK, null);
        return feedbackService.getFeedback(useId);
    }

    /**
     * <h2>测试异常返回</h2>
     * @return {@link Response}
     * @throws Exception e
     */
    @GetMapping("exception")
    public Response exception() throws Exception{
        throw new Exception("test exception");
    }
}
