package com.shu.passbook.service;

import com.alibaba.fastjson.JSON;
import com.shu.passbook.constant.Constants;
import com.shu.passbook.vo.PassTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * <h1>消费 kafka中的 PassTemplate</h1>
 *
 * @author yang
 * @date 2019/6/11 16:37
 */
@Slf4j
@Component
public class ConsumePassTemplate {
    /**
     * pass相关Hbase服务
     */
    private final IHbasePassService passService;

    @Autowired
    public ConsumePassTemplate(IHbasePassService passService) {
        this.passService = passService;
    }

    @KafkaListener(topics = {Constants.TEMPLATE_TOPIC})
    public void receive(@Payload String passTemplate,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Consumer Receive PassTemplate:{}", passTemplate);
        PassTemplate passTemplate2 = JSON.parseObject(passTemplate, PassTemplate.class);
        log.info("DropPassTemplateToHbase:{}", passService.dropPassTemplate(passTemplate2));
    }
}
