package ru.sbt.mipt.oop;

import com.google.gson.annotations.Expose;

public class Door {
    @Expose
    private final String id;
    private final Room room;
    @Expose
    private boolean isOpen;

    public Door(String id, Room room, boolean isOpen) {
        this.isOpen = isOpen;
        this.room = room;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
