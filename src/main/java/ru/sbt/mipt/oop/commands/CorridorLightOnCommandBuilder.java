package ru.sbt.mipt.oop.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.SmartHome;

@Component
public class CorridorLightOnCommandBuilder extends BaseCommandBuilder {
    @Autowired private SmartHome smartHome;

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
