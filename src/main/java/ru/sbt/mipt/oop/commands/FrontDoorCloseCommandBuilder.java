package ru.sbt.mipt.oop.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.SmartHome;

@Component
public class FrontDoorCloseCommandBuilder extends BaseCommandBuilder {
    @Autowired private SmartHome smartHome;

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
