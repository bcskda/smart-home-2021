package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Door;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.events.SensorEvent;

import java.util.Map;

import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_OPEN;

public class GlobalDoorOpenEventHandler implements SensorEventHandler {
    Map<String, Door> doorsById;

    public GlobalDoorOpenEventHandler(Map<String, Door> doorsById) {
        this.doorsById = doorsById;
    }

    @Override
    public void handleEvent(SensorEvent event) throws IllegalArgumentException {
        if (event.getType() != DOOR_OPEN) {
            throw new IllegalArgumentException(
                    "Unexpected SensorEvent of type " + event.getType().toString());
        }
        Door door = doorsById.get(event.getObjectId());
        if (door == null) {
            throw new IllegalArgumentException(
                    "No door with id " + event.getObjectId());
        }
        Room room = door.getRoom();
        onDoorOpen(door, room);
    }

    private void onDoorOpen(Door door, Room room) {
        door.setOpen(true);
        System.out.println("Door " + door.getId() + " in room " + room.getName() + " was closed.");
    }
}
