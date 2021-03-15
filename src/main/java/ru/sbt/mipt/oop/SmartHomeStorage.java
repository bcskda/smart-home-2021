package ru.sbt.mipt.oop;

import java.util.ArrayList;
import java.util.Collection;

public class SmartHomeStorage {
    Collection<Room> rooms;

    public SmartHomeStorage() {
        this(new ArrayList<>());
    }

    public SmartHomeStorage(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public Collection<Room> getRooms() {
        return rooms;
    }
}
