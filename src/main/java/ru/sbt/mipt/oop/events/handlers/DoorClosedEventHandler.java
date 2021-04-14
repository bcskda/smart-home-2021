package ru.sbt.mipt.oop.events.handlers;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.EventType.DOOR_CLOSED;

@Component
public class DoorClosedEventHandler implements SensorEventHandler {
    @Bean
    public DoorClosedEventHandler doorClosedEventHandler() {
        return this;
    }

    @Override
    public Action handleEvent(Event event) {
        if (event.getType() != DOOR_CLOSED)
            return null;
        return onDoorClose((SensorEvent) event);
    }

    private Action onDoorClose(SensorEvent event) {
        return component -> {
            if (! (component instanceof Door))
                return;
            Door door = (Door) component;
            if (event.getObjectId().equals(door.getId()))
                door.setOpen(false);
        };
    }
}
