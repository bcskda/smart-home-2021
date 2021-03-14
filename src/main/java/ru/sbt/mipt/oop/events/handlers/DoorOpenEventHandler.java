package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.events.SensorEvent;

import java.util.Map;

import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_OPEN;

public class DoorOpenEventHandler implements SensorEventHandler {
    Map<String, Door> doorsById;

    public DoorOpenEventHandler(Map<String, Door> doorsById) {
        this.doorsById = doorsById;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (event.getType() != DOOR_OPEN)
            return;
        Door door = doorsById.get(event.getObjectId());
        if (door == null) {
            throw new IllegalArgumentException(
                    "No door with id " + event.getObjectId());
        }
        onDoorOpen(door);
    }

    private void onDoorOpen(Door door) {
        door.setOpen(true);
    }
}
