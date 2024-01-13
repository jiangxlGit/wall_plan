package com.jloved.example.config;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ESResponseInterceptor implements RestClientBuilder.HttpClientConfigCallback {
    @Override
    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {

        HttpResponseInterceptor httpResponseInterceptor = new HttpResponseInterceptor() {
            @Override
            public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
                response.addHeader("X-Elastic-Product", "Elasticsearch");
            }
        };
        httpClientBuilder.addInterceptorFirst(httpResponseInterceptor);
        return httpClientBuilder;
    }
}