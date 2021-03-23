package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Action;
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
    public Action handleEvent(SensorEvent event) {
        if (event.getType() != LIGHT_OFF)
            return null;
        return onLightOff(event);
    }

    private Action onLightOff(SensorEvent event) {
        return component -> {
            if (! (component instanceof Light))
                return;
            Light light = (Light) component;
            if (event.getObjectId().equals(light.getId()))
                light.setOn(false);
        };
    }
}
