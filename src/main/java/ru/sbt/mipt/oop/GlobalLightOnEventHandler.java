package ru.sbt.mipt.oop;

import java.util.Map;

import static ru.sbt.mipt.oop.SensorEventType.LIGHT_OFF;
import static ru.sbt.mipt.oop.SensorEventType.LIGHT_ON;

public class GlobalLightOnEventHandler implements SensorEventHandler {
    Map<String, Light> lightsById;

    public GlobalLightOnEventHandler(Map<String, Light> lightsById) {
        this.lightsById = lightsById;
    }

    @Override
    public void handleEvent(SensorEvent event) throws IllegalArgumentException {
        if (event.getType() != LIGHT_ON) {
            throw new IllegalArgumentException(
                    "Unexpected SensorEvent of type " + event.getType().toString());
        }
        Light light = lightsById.get(event.getObjectId());
        if (light == null) {
            throw new IllegalArgumentException(
                    "No light with id " + event.getObjectId());
        }
        Room room = light.getRoom();
        onLightOn(light, room);
    }

    private void onLightOn(Light light, Room room) {
        light.setOn(true);
        System.out.println("Light " + light.getId() + " in room " + room.getName() + " was turned on.");
    }
}
