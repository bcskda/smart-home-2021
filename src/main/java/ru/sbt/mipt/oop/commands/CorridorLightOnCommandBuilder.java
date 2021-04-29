package ru.sbt.mipt.oop.commands;

import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.SmartHome;

public class CorridorLightOnCommandBuilder extends BaseCommandBuilder {
    private final SmartHome smartHome;

    public CorridorLightOnCommandBuilder(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public Command build(String commandType) {
        if ("corridorLightOn".equals(commandType))
            return makeCommand();
        else
            return passOrNull(commandType);
    }

    private Command makeCommand() {
        return () -> {
            smartHome.execute(component -> {
                if (! (component instanceof Room))
                    return;
                Room room = (Room) component;
                if (!"corridor".equals(room.getName()))
                    return;
                room.forEachLight(light -> ((Light) light).setOn(true));
            });
        };
    }
}
