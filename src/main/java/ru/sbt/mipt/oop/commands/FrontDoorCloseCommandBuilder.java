package ru.sbt.mipt.oop.commands;

import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.SmartHome;

public class FrontDoorCloseCommandBuilder extends BaseCommandBuilder {
    private final SmartHome smartHome;

    public FrontDoorCloseCommandBuilder(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public Command build(String commandType) {
        if ("frontDoorClose".equals(commandType))
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
                if (!"hall".equals(room.getName()))
                    return;
                room.forEachDoor(door -> ((Door) door).setOpen(false));
            });
        };
    }
}
