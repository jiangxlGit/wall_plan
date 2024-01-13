package com.jloved.example.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ESClient 配置
 *
 * @author liuhui
 * @email liuhui@jxwxxkj.com
 * @date 2023-06-21 14:10:33
 */
@Configuration
@EnableConfigurationProperties({ESProperties.class})
public class ESClientAutoConfig {

    @Bean
    public ElasticsearchClient esClient(ESProperties esProperties) {
        // Create the low-level client
        RestClient restClient = RestClient.builder(
                new HttpHost(esProperties.getHost(), esProperties.getPort()))
                .setDefaultHeaders(new Header[]{
                        new BasicHeader("Content-Type", "application/json; charset=UTF-8")})
                .setHttpClientConfigCallback(new ESResponseInterceptor())
                .build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        //create the API client
        return new ElasticsearchClient(transport);
    }
}
