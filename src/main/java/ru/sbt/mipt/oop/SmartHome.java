package ru.sbt.mipt.oop;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SmartHome {
    @Expose
    Collection<Room> rooms;

    Map<String, Light> lightsById = new HashMap<>();
    Map<String, Door> doorsById = new HashMap<>();
    Map<Door, Room> roomsByDoor = new HashMap<>();

    public SmartHome() {
        this(new ArrayList<>());
    }

    public SmartHome(Collection<Room> rooms) {
        this.rooms = rooms;

        for (Room room : rooms) {
            for (Light light : room.getLights()) {
                lightsById.put(light.getId(), light);
            }
            for (Door door : room.getDoors()) {
                doorsById.put(door.getId(), door);
                roomsByDoor.put(door, room);
            }
        }
    }

    // Update handlers after adding rooms
    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Collection<Room> getRooms() {
        return rooms;
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
}
