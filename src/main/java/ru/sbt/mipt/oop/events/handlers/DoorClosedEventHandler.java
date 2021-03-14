package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.events.SensorEvent;

import java.util.Map;

import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_CLOSED;

public class DoorClosedEventHandler implements SensorEventHandler {
    Map<String, Door> doorsById;

    public DoorClosedEventHandler(Map<String, Door> doorsById) {
        this.doorsById = doorsById;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (event.getType() != DOOR_CLOSED)
            return;
        Door door = doorsById.get(event.getObjectId());
        if (door == null) {
            throw new IllegalArgumentException(
                    "No door with id " + event.getObjectId());
        }
        onDoorClose(door);
    }

    private void onDoorClose(Door door) {
        door.setOpen(false);
    }
}
