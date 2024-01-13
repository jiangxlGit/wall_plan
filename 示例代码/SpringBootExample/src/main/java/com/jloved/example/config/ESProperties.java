package com.jloved.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liuhui
 * @email liuhui@jxwxxkj.com
 * @date 2023-02-10 17:17:43
 */
@Data
@ConfigurationProperties(prefix = "es")
public class ESProperties {

    private String host;

    private Integer port;
}
