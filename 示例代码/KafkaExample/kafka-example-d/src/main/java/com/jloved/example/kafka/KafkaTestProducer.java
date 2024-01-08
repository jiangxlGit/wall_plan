package com.jloved.example.kafka;

import com.jloved.strive.common.mq.kafka.KafkaMessage;
import com.jloved.strive.common.mq.kafka.KafkaSender;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiangxl
 * @version V1.0
 * @Description TODO
 * @ClassName KafkaTestProducer
 * @Date 2023/5/8 19:06
 */
@Slf4j
@RestController
@RequestMapping("/kafka-d")
public class KafkaTestProducer {

    /**
     * 发送kafka消息
     */
    @GetMapping("/send-1")
    Boolean sendKafka() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHssmmSSS");
        String[] arr = {"A", "B", "C"};
        for (int i = 0; i < 3; i++) {
            KafkaMessage message = KafkaMessage.builder()
                .key(arr[i] + i)
                .value(arr[i] + "-" + sdf.format(new Date()))
                .build();
            KafkaSender<String, String> kafkaSender = KafkaSender.newSender("jloved-kafka-test", String.class, String.class);
            Future future = kafkaSender.send(message);
            log.info("======= 第{}条消息, future: {}", i, future.isDone());
        }
        return Boolean.TRUE;
    }

}
