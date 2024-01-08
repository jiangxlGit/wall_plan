package com.jloved.example.kafka;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiangxl
 * @version V1.0
 * @Description TODO
 * @ClassName KafkaMsg
 * @Date 2023/5/13 11:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMsg implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String key;
    
    private String value;
    
    private String name;
    
}
