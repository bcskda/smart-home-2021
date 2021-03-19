package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.commands.SensorCommand;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.SensorEventType.LIGHT_ON;

public class LightOnEventHandler implements SensorEventHandler {
    SmartHome smartHome;

    public LightOnEventHandler(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public Action handleEvent(SensorEvent event) {
        if (event.getType() != LIGHT_ON)
            return null;
        return onLightOn(event);
    }

    private Action onLightOn(SensorEvent event) {
        return component -> {
            if (! (component instanceof Light))
                return;
            Light light = (Light) component;
            if (event.getObjectId().equals(light.getId()))
                light.setOn(true);
        };
    }
}
