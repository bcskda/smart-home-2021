package ru.sbt.mipt.oop.remotecontrol;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rc.RemoteControl;
import rc.RemoteControlRegistry;

@Configuration
public class RemoteControlConfiguration {
    private final String singleRemoteControlId = "single";

    @Bean
    public RemoteControlRegistry remoteControlRegistry() {
        RemoteControlRegistry registry = new RemoteControlRegistry();
        registry.registerRemoteControl(singleRemoteControl(), singleRemoteControlId);
        return registry;
    }

    @Bean
    public RemoteControl singleRemoteControl() {
        return new RemoteControlImpl(singleRemoteControlId);
    }
}
