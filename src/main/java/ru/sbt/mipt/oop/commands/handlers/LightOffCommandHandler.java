package ru.sbt.mipt.oop.commands.handlers;

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
    public void handleCommand(SensorCommand command) {
        if (command.getType() != LIGHT_OFF)
            return;
        Light light = smartHome.getLightById(command.getObjectId());
        if (light == null) {
            throw new IllegalArgumentException(
                    "No light with id " + command.getObjectId());
        }
        onLightOff(light);
    }

    private void onLightOff(Light light) {
        light.setOn(false);
    }
}
