package com.shu.passbook.service;

import com.shu.passbook.vo.Feedback;
import com.shu.passbook.vo.Response;

/**
 * <h1>用户评论功能</h1>
 *
 * @author yang
 * @date 2019/6/11 19:40
 */
public interface IFeedbackService {
    /**
     * 创建评论
     *
     * @param feedback {@link Feedback}
     * @return {@link Response}
     */
    Response createFeedback(Feedback feedback);

    /**
     * 获取用户评论
     *
     * @param userId id
     * @return {@link Response}
     */
    Response getFeedback(Long userId);
}
