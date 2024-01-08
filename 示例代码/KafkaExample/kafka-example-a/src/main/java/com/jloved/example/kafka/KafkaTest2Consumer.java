package com.jloved.example.kafka;

import com.jloved.strive.common.mq.kafka.IKafkaConsumer;
import com.jloved.strive.common.mq.starter.AbstractKafkaConsumer;
import com.jloved.strive.common.mq.starter.ConsumerType;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

/**
 * test1: 测试不同topic的消费
 */
@Slf4j
@Component(value = "kafkaTest2Consumer")
@ConsumerType(type = "consumerTopicNameTest2")
public class KafkaTest2Consumer extends AbstractKafkaConsumer<String, KafkaMsg> implements IKafkaConsumer<String, KafkaMsg> {
    
    @Override
    public void onMessage(ConsumerRecord<String, KafkaMsg> record) {
        try {
            log.info("------> kafka-1-2 消费到消息：{}", record.value());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
