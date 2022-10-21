package vn.edu.fpt.capstone.api.configs.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import vn.edu.fpt.capstone.api.configs.AppConfig;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final AppConfig appConfig;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(appConfig.getConnectTimeout()))
                .setReadTimeout(Duration.ofSeconds(appConfig.getReadTimeout()))
                .build();
    }

}
