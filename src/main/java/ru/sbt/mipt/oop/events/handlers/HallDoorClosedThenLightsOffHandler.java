package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.Room;
import ru.sbt.mipt.oop.SmartHomeController;
import ru.sbt.mipt.oop.commands.CommandType;
import ru.sbt.mipt.oop.commands.SensorCommand;
import ru.sbt.mipt.oop.events.SensorEvent;

import java.util.Set;

import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_CLOSED;

public class HallDoorClosedThenLightsOffHandler implements SensorEventHandler {
    SmartHomeController controller;
    Set<String> hallDoorsIds;

    public HallDoorClosedThenLightsOffHandler(SmartHomeController controller, Set<String> hallDoorsIds) {
        this.controller = controller;
        this.hallDoorsIds = hallDoorsIds;
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (event.getType() != DOOR_CLOSED)
            return;
        if (hallDoorsIds.contains(event.getObjectId()))
            onHallDoorClose();
    }

    private void onHallDoorClose() {
        for (Room homeRoom : controller.getHome().getRooms()) {
            for (Light light : homeRoom.getLights()) {
                SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
                controller.sendCommand(command);
            }
        }
    }
}
