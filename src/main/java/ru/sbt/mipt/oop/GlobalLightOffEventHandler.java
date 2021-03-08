package ru.sbt.mipt.oop;

import java.util.Map;

import static ru.sbt.mipt.oop.SensorEventType.LIGHT_OFF;

public class GlobalLightOffEventHandler implements SensorEventHandler {
    Map<String, Light> lightsById;

    public GlobalLightOffEventHandler(Map<String, Light> lightsById) {
        this.lightsById = lightsById;
    }

    @Override
    public void handleEvent(SensorEvent event) throws IllegalArgumentException {
        if (event.getType() != LIGHT_OFF) {
            throw new IllegalArgumentException(
                    "Unexpected SensorEvent of type " + event.getType().toString());
        }
        Light light = lightsById.get(event.getObjectId());
        if (light == null) {
            throw new IllegalArgumentException(
                    "No light with id " + event.getObjectId());
        }
        Room room = light.getRoom();
        onLightOff(light, room);
    }

    private void onLightOff(Light light, Room room) {
        light.setOn(false);
        System.out.println("Light " + light.getId() + " in room " + room.getName() + " was turned off.");
    }
}
