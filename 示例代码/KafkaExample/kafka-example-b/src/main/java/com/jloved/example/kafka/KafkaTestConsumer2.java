package com.jloved.example.kafka;

import com.jloved.strive.common.mq.kafka.IKafkaConsumer;
import com.jloved.strive.common.mq.starter.AbstractKafkaConsumer;
import com.jloved.strive.common.mq.starter.ConsumerType;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

/**
 * test2: 测试同topic不同消费组消费
 */
@Slf4j
@Component(value = "kafkaTestConsumer2")
@ConsumerType(type = "consumerTopicNameTest")
public class KafkaTestConsumer2 extends AbstractKafkaConsumer<String, String> implements IKafkaConsumer<String, String> {
    
    @Override
    public void onMessage(ConsumerRecord<String, String> record) {
        try {
            log.info("------> kafka-2 消费到消息：{}", record.value());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
