package com.shu.passbook.service;

import com.shu.passbook.vo.GainPassTemplateRequest;
import com.shu.passbook.vo.Response;

/**
 * <h1>用户领取优惠券功能实现</h1>
 * @author yang
 * @date 2019/6/11 20:09
 */
public interface IGainPassTemplateService {
    /**
     * <h2>用户领取优惠券</h2>
     * @param gainPassTemplateRequest {@link GainPassTemplateRequest}
     * @return {@link Response}
     * @throws Exception 异常
     */
    Response gainPassTemplate(GainPassTemplateRequest gainPassTemplateRequest) throws Exception;
}
