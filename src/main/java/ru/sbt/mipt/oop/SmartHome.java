package ru.sbt.mipt.oop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SmartHome implements Actionable {
    SmartHomeStorage storage = new SmartHomeStorage();

    Map<String, Light> lightsById = new HashMap<>();
    Map<String, Door> doorsById = new HashMap<>();
    Map<Door, Room> roomsByDoor = new HashMap<>();

    public SmartHome() {
        this(new ArrayList<>());
    }

    public SmartHome(Collection<Room> rooms) {
        rooms.forEach(this::addRoom);
    }

    // Update
    public void addRoom(Room room) {
        storage.rooms.add(room);
        for (Light light : room.getLights()) {
            lightsById.put(light.getId(), light);
        }
        for (Door door : room.getDoors()) {
            doorsById.put(door.getId(), door);
            roomsByDoor.put(door, room);
        }
    }

    public Light getLightById(String lightId) {
        return lightsById.get(lightId);
    }

    public Door getDoorById(String doorId) {
        return doorsById.get(doorId);
    }

    public Room getRoomByDoor(Door door) {
        return roomsByDoor.get(door);
    }

    public void forEachRoom(Action action) {
        storage.getRooms().forEach(room -> room.execute(action));
    }

    @Override
    public void execute(Action action) {
        forEachRoom(action);
    }
}
