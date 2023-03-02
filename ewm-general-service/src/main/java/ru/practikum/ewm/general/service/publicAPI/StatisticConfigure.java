package ru.practikum.ewm.general.service.publicAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.statistic.client.StatisticHttpClient;

@Configuration
public class StatisticConfigure {

    @Value("${spring.application.name}")
    private  String application;
    @Value("${ewm-statistic-service.uri}")
    private String statsServiceUri;


    @Bean
    public StatisticHttpClient init() {

        return new StatisticHttpClient(statsServiceUri, application, new ObjectMapper());
    }
}
