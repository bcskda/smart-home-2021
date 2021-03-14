package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.events.SensorEvent;

import java.util.Map;

import static ru.sbt.mipt.oop.events.SensorEventType.LIGHT_ON;

public class LightOnEventHandler implements SensorEventHandler {
    Map<String, Light> lightsById;

    public LightOnEventHandler(Map<String, Light> lightsById) {
        this.lightsById = lightsById;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (event.getType() != LIGHT_ON)
            return;
        Light light = lightsById.get(event.getObjectId());
        if (light == null) {
            throw new IllegalArgumentException(
                    "No light with id " + event.getObjectId());
        }
        onLightOn(light);
    }

    private void onLightOn(Light light) {
        light.setOn(true);
    }
}
