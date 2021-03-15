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
        Light light = smartHome.getLightById(event.getObjectId());
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
