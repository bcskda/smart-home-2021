package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.commands.CommandType;
import ru.sbt.mipt.oop.commands.SensorCommand;
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
    public void handleEvent(SensorEvent event) {
        if (event.getType() != DOOR_CLOSED)
            return;
        Door door = smartHome.getDoorById(event.getObjectId());
        if (door == null) {
            throw new IllegalArgumentException(
                    "No door with id " + event.getObjectId());
        }
        if ("hall".equals(smartHome.getRoomByDoor(door).getName()))
            onHallDoorClose();
    }

    private void onHallDoorClose() {
        for (Room homeRoom : smartHome.getRooms()) {
            for (Light light : homeRoom.getLights()) {
                SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
                controller.sendCommand(command);
            }
        }
    }
}
