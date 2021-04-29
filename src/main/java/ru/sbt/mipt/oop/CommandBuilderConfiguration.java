package ru.sbt.mipt.oop;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.commands.*;

@Configuration
public class CommandBuilderConfiguration {
    @Qualifier("singleCommandBuilder")
    @Bean
    CommandBuilder alarmCommandBuilder() {
        return new AlarmCommandBuilder();
    }

    @Qualifier("singleCommandBuilder")
    @Bean
    CommandBuilder allRoomsLightCommandBuilder() {
        return new AllRoomsLightCommandBuilder();
    }

    @Qualifier("singleCommandBuilder")
    @Bean
    CommandBuilder corridorLightOnCommandBuilder() {
        return new CorridorLightOnCommandBuilder();
    }

    @Qualifier("singleCommandBuilder")
    @Bean
    CommandBuilder frontDoorCloseCommandBuilder() {
        return new FrontDoorCloseCommandBuilder();
    }
}
