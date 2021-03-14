package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.SensorEventType.LIGHT_OFF;

public class LightOffEventHandler implements SensorEventHandler {
    SmartHome smartHome;

    public LightOffEventHandler(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (event.getType() != LIGHT_OFF)
            return;
        Light light = smartHome.getLightById(event.getObjectId());
        if (light == null) {
            throw new IllegalArgumentException(
                    "No light with id " + event.getObjectId());
        }
        onLightOff(light);
    }

    private void onLightOff(Light light) {
        light.setOn(false);
    }
}
