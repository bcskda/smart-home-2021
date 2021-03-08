package ru.sbt.mipt.oop;

import java.util.Map;

import static ru.sbt.mipt.oop.SensorEventType.DOOR_OPEN;

public class GlobalDoorClosedEventHandler implements SensorEventHandler {
    SmartHome smartHome;
    Map<String, Door> doorsById;

    public GlobalDoorClosedEventHandler(SmartHome smartHome, Map<String, Door> doorsById) {
        this.smartHome = smartHome;
        this.doorsById = doorsById;
    }

    @Override
    public void handleEvent(SensorEvent event) throws IllegalArgumentException {
        if (event.getType() == DOOR_OPEN) {
            throw new IllegalArgumentException(
                    "Unexpected SensorEvent of type " + event.getType().toString());
        }
        Door door = doorsById.get(event.getObjectId());
        if (door == null) {
            throw new IllegalArgumentException(
                    "No door with id " + event.getObjectId());
        }
        Room room = door.getRoom();
        onDoorClose(event, door, room);
    }

    private void onDoorClose(SensorEvent event, Door door, Room room) {
        door.setOpen(false);
        System.out.println("Door " + door.getId() + " in room " + room.getName() + " was closed.");
        // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
        // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
        if (room.getName().equals("hall")) {
            for (Room homeRoom : smartHome.getRooms()) {
                for (Light light : homeRoom.getLights()) {
                    light.setOn(false);
                    SensorCommand command = new SensorCommand(CommandType.LIGHT_OFF, light.getId());
                    sendCommand(command);
                }
            }
        }
    }
}
