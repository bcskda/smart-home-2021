package ru.sbt.mipt.oop.events.handlers;

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
    public void handleEvent(SensorEvent event) {
        if (event.getType() != DOOR_OPEN)
            return;
        Door door = smartHome.getDoorById(event.getObjectId());
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
