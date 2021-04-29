package ru.sbt.mipt.oop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.commands.*;

@Configuration
public class CommandBuilderConfiguration {
    @Autowired
    private SmartHome smartHome;

    @Qualifier("singleCommandBuilder")
    @Bean
    BaseCommandBuilder alarmCommandBuilder() {
        return new AlarmCommandBuilder(smartHome, smartHome.getAlarm());
    }

    @Qualifier("singleCommandBuilder")
    @Bean
    BaseCommandBuilder allRoomsLightCommandBuilder() {
        return new AllRoomsLightCommandBuilder(smartHome);
    }

    @Qualifier("singleCommandBuilder")
    @Bean
    BaseCommandBuilder corridorLightOnCommandBuilder() {
        return new CorridorLightOnCommandBuilder(smartHome);
    }

    @Qualifier("singleCommandBuilder")
    @Bean
    BaseCommandBuilder frontDoorCloseCommandBuilder() {
        return new FrontDoorCloseCommandBuilder(smartHome);
    }
}
