package com.shu.passbook.utils;

import com.shu.passbook.vo.Feedback;
import com.shu.passbook.vo.PassTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * <h1>RowKey 生成工具类</h1>
 *
 * @author yang
 * @date 2019/6/11 16:13
 */
@Slf4j
public class RowKeyGenUtil {
    /**
     * <h2>根据PassTemplate 生成RowKey</h2>
     *
     * @param passTemplate {@link PassTemplate}
     * @return {@link String} rowKey
     */
    public static String genPassTemplateRowKey(PassTemplate passTemplate) {
        String passInfo = String.valueOf(passTemplate.getId()) + "_" + passTemplate.getTitle();
        String rowKey = DigestUtils.md5Hex(passInfo);
        log.info("GenPassTemplateRowKey:{},{}", passInfo, rowKey);
        return rowKey;
    }

    /**
     * <h2>根据Feedback 生成RowKey</h2>
     *
     * @param feedback {@link Feedback}
     * @return {@link String} rowKey
     */
    public static String genFeedbackRowKey(Feedback feedback) {
        return new StringBuilder(String.valueOf(feedback.getUserId())).reverse().toString() + (Long.MAX_VALUE - System.currentTimeMillis());
    }
}
