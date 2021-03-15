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
        Door door = smartHome.getDoorById(event.getObjectId());
        if (door == null)
            return null;
        return onDoorOpen(door);
    }

    private Action onDoorOpen(Door door) {
        return component -> {
            if (! (component instanceof Door))
                return;
            Door asDoor = (Door) component;
            if (door.equals(asDoor))
                asDoor.setOpen(true);
        };
    }
}
