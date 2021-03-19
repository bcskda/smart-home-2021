package ru.sbt.mipt.oop;

import java.util.ArrayList;
import java.util.Collection;

public class SmartHome implements HomeComponent, Actionable {
    SmartHomeStorage storage = new SmartHomeStorage();

    public SmartHome() {
        this(new ArrayList<>());
    }

    public SmartHome(Collection<Room> rooms) {
        rooms.forEach(this::addRoom);
    }

    // Update
    public void addRoom(Room room) {
        storage.rooms.add(room);
    }

    public void forEachRoom(Action action) {
        storage.getRooms().forEach(room -> room.execute(action));
    }

    @Override
    public void execute(Action action) {
        action.execute(this);
        forEachRoom(action);
    }
}
