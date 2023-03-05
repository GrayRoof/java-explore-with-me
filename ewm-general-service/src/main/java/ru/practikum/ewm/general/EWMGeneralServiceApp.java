package ru.practikum.ewm.general;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EWMGeneralServiceApp {
    public static void main(String[] args) {

        SpringApplication.run(EWMGeneralServiceApp.class);
    }
}