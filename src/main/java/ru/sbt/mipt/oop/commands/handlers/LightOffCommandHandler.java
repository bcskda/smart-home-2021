package ru.sbt.mipt.oop.commands.handlers;

import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.commands.CommandType;
import ru.sbt.mipt.oop.commands.SensorCommand;

import java.util.Map;

public class LightOffCommandHandler implements SensorCommandHandler {
    Map<String, Light> lightsById;

    public LightOffCommandHandler(Map<String, Light> lightsById) {
        this.lightsById = lightsById;
    }

    @Override
    public void handleCommand(SensorCommand command) throws IllegalArgumentException {
        if (command.getType() != CommandType.LIGHT_OFF) {
            throw new IllegalArgumentException(
                    "Unexpected SensorCommand of type " + command.getType().toString());
        }
        Light light = lightsById.get(command.getObjectId());
        if (light == null) {
            throw new IllegalArgumentException(
                    "No light with id " + command.getObjectId());
        }
        Room room = light.getRoom();
        onLightOff(light, room);
    }

    private void onLightOff(Light light, Room room) {
        light.setOn(false);
        System.out.println("Light " + light.getId() + " in room " + room.getName() + " was turned off.");
    }
}
