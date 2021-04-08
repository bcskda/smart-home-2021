package ru.sbt.mipt.oop;

import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationSender {
    public void sendSms() {
        System.out.println("Sending SMS");
    }
}
