package ru.sbt.mipt.oop.commands.legacy.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.commands.legacy.SensorCommand;

import static ru.sbt.mipt.oop.commands.legacy.CommandType.LIGHT_OFF;

public class LightOffCommandHandler implements SensorCommandHandler {
    SmartHome smartHome;

    public LightOffCommandHandler(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public Action handleCommand(SensorCommand command) {
        if (command.getType() != LIGHT_OFF)
            return null;
        return onLightOff(command);
    }

    private Action onLightOff(SensorCommand command) {
        return component -> {
            if (! (component instanceof Light))
                return;
            Light light = (Light) component;
            if (command.getObjectId().equals(light.getId()))
                light.setOn(false);
        };
    }
}
