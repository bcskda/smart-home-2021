package ru.sbt.mipt.oop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.commands.CommandBuilder;
import ru.sbt.mipt.oop.commands.CommandBuilderChain;
import ru.sbt.mipt.oop.remotecontrol.RemoteControlImpl;

@Configuration
public class RemoteControlConfiguration {
    @Autowired
    CommandBuilderChain commandBuilderChain;

    @Bean
    public RemoteControlImpl securityRemoteControl() {
        CommandBuilder commandBuilder = commandBuilderChain.commandBuilder();
        RemoteControlImpl rc = new RemoteControlImpl("security");
        rc.addCommand("1", commandBuilder.build("frontDoorClose"));
        rc.addCommand("A", commandBuilder.build("activateAlarm"));
        rc.addCommand("B", commandBuilder.build("triggerAlarm"));
        return rc;
    }

    @Bean
    public RemoteControlImpl lightRemoveControl() {
        CommandBuilder commandBuilder = commandBuilderChain.commandBuilder();
        RemoteControlImpl rc = new RemoteControlImpl("light");
        rc.addCommand("1", commandBuilder.build("allRoomsLightOn"));
        rc.addCommand("2", commandBuilder.build("allRoomsLightOff"));
        rc.addCommand("A", commandBuilder.build("corridorLightOn"));
        return rc;
    }
}
