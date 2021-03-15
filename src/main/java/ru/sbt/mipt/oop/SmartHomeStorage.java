package ru.sbt.mipt.oop;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Collection;

public class SmartHomeStorage {
    @Expose
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
