package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.SensorEventType.LIGHT_ON;

public class LightOnEventHandler implements SensorEventHandler {
    SmartHome smartHome;

    public LightOnEventHandler(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (event.getType() != LIGHT_ON)
            return;
        Light light = smartHome.getLightById(event.getObjectId());
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
