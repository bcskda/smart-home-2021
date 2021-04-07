package ru.sbt.mipt.oop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
@ComponentScan
public class TestConfiguration {
    @Bean
    public SmartHome smartHome() {
        InputStream stream = getClass().getResourceAsStream("test-smart-home.json");
        return new JsonConfigurationReader(stream).readSmartHome();
    }
}
