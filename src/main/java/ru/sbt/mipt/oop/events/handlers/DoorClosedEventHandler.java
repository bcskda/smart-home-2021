package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_CLOSED;

public class DoorClosedEventHandler implements SensorEventHandler {
    SmartHome smartHome;

    public DoorClosedEventHandler(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public Action handleEvent(SensorEvent event) {
        if (event.getType() != DOOR_CLOSED)
            return null;
        return onDoorClose(event);
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
