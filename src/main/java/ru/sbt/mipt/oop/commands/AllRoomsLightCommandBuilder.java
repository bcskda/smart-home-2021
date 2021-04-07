package ru.sbt.mipt.oop.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;

@Component
public class AllRoomsLightCommandBuilder extends BaseCommandBuilder {
    @Autowired private SmartHome smartHome;

    @Override
    public Command build(String commandType) {
        if ("allRoomsLightOn".equals(commandType))
            return makeCommand(true);
        else if ("allRoomsLightOff".equals(commandType))
            return makeCommand(false);
        else
            return passOrNull(commandType);
    }

    private Command makeCommand(boolean isOn) {
        return () -> {
            smartHome.execute(component -> {
                if (! (component instanceof Light))
                    return;
                ((Light) component).setOn(isOn);
            });
        };
    }
}
