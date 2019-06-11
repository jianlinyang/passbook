package com.shu.passbook.vo;

import com.google.common.base.Enums;
import com.shu.passbook.constant.FeedbackType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h2>用户评论</h2>
 *
 * @author yang
 * @date 2019/6/11 14:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    private Long userId;
    private String type;
    private String templateId;
    private String comment;

    public boolean validate() {
        FeedbackType feedbackType = Enums.getIfPresent(FeedbackType.class, this.type.toUpperCase()).orNull();
        return !(null == feedbackType || null == comment);
    }
}
