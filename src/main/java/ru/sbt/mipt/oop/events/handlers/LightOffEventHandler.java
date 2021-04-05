package ru.sbt.mipt.oop.events.handlers;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.EventType.LIGHT_OFF;

@Component
public class LightOffEventHandler implements EventHandler {
    @Bean
    public LightOffEventHandler lightOffEventHandler() {
        return this;
    }

    @Override
    public Action handleEvent(Event event) {
        if (event.getType() != LIGHT_OFF)
            return null;
        return onLightOff((SensorEvent) event);
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
