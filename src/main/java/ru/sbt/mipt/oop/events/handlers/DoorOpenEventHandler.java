package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.EventType.DOOR_OPEN;

public class DoorOpenEventHandler implements EventHandler {
    SmartHome smartHome;

    public DoorOpenEventHandler(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public Action handleEvent(Event event) {
        if (event.getType() != DOOR_OPEN)
            return null;
        return onDoorOpen((SensorEvent) event);
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
