package ru.sbt.mipt.oop.commands.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.commands.SensorCommand;

import static ru.sbt.mipt.oop.commands.CommandType.LIGHT_OFF;

public class LightOffCommandHandler implements SensorCommandHandler {
    SmartHome smartHome;

    public LightOffCommandHandler(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public Action handleCommand(SensorCommand command) {
        if (command.getType() != LIGHT_OFF)
            return null;
        Light light = smartHome.getLightById(command.getObjectId());
        if (light == null)
            return null;
        return onLightOff(light);
    }

    private Action onLightOff(Light light) {
        return component -> {
            if (! (component instanceof Light))
                return;
            Light asLight = (Light) component;
            if (light.equals(asLight))
                asLight.setOn(false);
        };
    }
}
