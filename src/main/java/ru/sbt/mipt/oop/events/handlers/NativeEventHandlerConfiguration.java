package ru.sbt.mipt.oop.events.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class NativeEventHandlerConfiguration {
    @Autowired private AlarmSecurityEventHandler alarmSecurityEventHandler;
    @Autowired private LogEventHandler logEventHandler;

    @Bean
    public List<EventHandler> getEventHandlers() {
        return Arrays.asList(
                alarmSecurityEventHandler
                , logEventHandler
        );
    }
}
