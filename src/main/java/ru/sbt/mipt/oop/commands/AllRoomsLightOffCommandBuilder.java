package ru.sbt.mipt.oop.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;

@Component
public class AllRoomsLightOffCommandBuilder extends BaseCommandBuilder {
    @Autowired  SmartHome smartHome;

    @Override
    public Command build(String commandType) {
        if ("allRoomsLightOff".equals(commandType))
            return makeCommand();
        else
            return passOrNull(commandType);
    }

    Command makeCommand() {
        return () -> {
            smartHome.execute(component -> {
                if (! (component instanceof Light))
                    return;
                ((Light) component).setOn(false);
            });
        };
    }
}
