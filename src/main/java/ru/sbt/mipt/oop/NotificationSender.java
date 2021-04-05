package ru.sbt.mipt.oop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationSender {
    @Bean
    public NotificationSender notificationSender() {
        return this;
    }

    public void sendSms() {
        System.out.println("Sending SMS");
    }
}
