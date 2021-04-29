package ru.sbt.mipt.oop.commands;

import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;

public class AllRoomsLightCommandBuilder extends BaseCommandBuilder {
    private final SmartHome smartHome;

    public AllRoomsLightCommandBuilder(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

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
