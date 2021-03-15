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
        Door door = smartHome.getDoorById(event.getObjectId());
        if (door == null)
            return null;
        return onDoorClose(door);
    }

    private Action onDoorClose(Door door) {
        return component -> {
            if (! (component instanceof Door))
                return;
            Door asDoor = (Door) component;
            if (door.equals(asDoor))
                asDoor.setOpen(false);
        };
    }
}
