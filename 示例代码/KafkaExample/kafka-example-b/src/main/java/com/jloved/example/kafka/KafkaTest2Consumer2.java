package com.jloved.example.kafka;

import com.jloved.strive.common.mq.kafka.IKafkaConsumer;
import com.jloved.strive.common.mq.starter.AbstractKafkaConsumer;
import com.jloved.strive.common.mq.starter.ConsumerType;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

/**
 * test4: 测试手动commit时发生异常的情况
 */
@Slf4j
@Component(value = "kafkaTest2Consumer2")
@ConsumerType(type = "consumerTopicNameTest2")
public class KafkaTest2Consumer2 extends AbstractKafkaConsumer<String, String> implements IKafkaConsumer<String, String> {
    
    @Override
    public void onMessage(ConsumerRecord<String, String> record) {
        try {
            log.info("------> kafka-2-2 消费到消息：{}", record.value());
            int a = 1;
            int b = 0;
            System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    
}
