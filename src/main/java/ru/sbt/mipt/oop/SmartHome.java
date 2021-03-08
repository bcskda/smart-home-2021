package ru.sbt.mipt.oop;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Collection;

public class SmartHome {
    @Expose
    Collection<Room> rooms;

    public SmartHome() {
        rooms = new ArrayList<>();
    }

    public SmartHome(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Collection<Room> getRooms() {
        return rooms;
    }
}
