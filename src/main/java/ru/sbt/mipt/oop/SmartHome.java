package ru.sbt.mipt.oop;

import java.util.ArrayList;
import java.util.Collection;

public class SmartHome implements Actionable {
    final private Collection<Room> rooms;

    public SmartHome() {
        this(new ArrayList<>());
    }

    public SmartHome(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    @Override
    public void execute(Action action) {
        action.execute(this);
        forEachRoom(action);
    }

    private void forEachRoom(Action action) {
        rooms.forEach(room -> room.execute(action));
    }
}
