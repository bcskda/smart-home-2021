package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_OPEN;

public class DoorOpenEventHandler implements SensorEventHandler {
    SmartHome smartHome;

    public DoorOpenEventHandler(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public Action handleEvent(SensorEvent event) {
        if (event.getType() != DOOR_OPEN)
            return null;
        return onDoorOpen(event);
    }

    private Action onDoorOpen(SensorEvent event) {
        return component -> {
            if (! (component instanceof Door))
                return;
            Door door = (Door) component;
            if (event.getObjectId().equals(door.getId()))
                door.setOpen(true);
        };
    }
}
