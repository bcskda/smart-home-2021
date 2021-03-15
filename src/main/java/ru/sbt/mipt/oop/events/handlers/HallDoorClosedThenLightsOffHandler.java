package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_CLOSED;

public class HallDoorClosedThenLightsOffHandler implements SensorEventHandler {
    CommandSender controller;
    SmartHome smartHome;

    public HallDoorClosedThenLightsOffHandler(CommandSender controller, SmartHome smartHome) {
        this.controller = controller;
        this.smartHome = smartHome;
    }

    @Override
    public Action handleEvent(SensorEvent event) {
        if (event.getType() != DOOR_CLOSED)
            return null;
        Door door = smartHome.getDoorById(event.getObjectId());
        if (door == null)
            return null;
        Room room = smartHome.getRoomByDoor(door);
        if ("hall".equals(room.getName()))
            return onHallDoorClose(room);
        else
            return null;
    }

    private Action onHallDoorClose(Room room) {
        return component -> {
            if (! (component instanceof Room))
                return;
            Room asRoom = (Room) component;
            if (room.equals(asRoom))
                asRoom.forEachLight(light -> ((Light) light).setOn(false));
        };
    }
}
