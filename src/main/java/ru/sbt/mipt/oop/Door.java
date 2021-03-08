package ru.sbt.mipt.oop;

public class Door {
    private final String id;
    private final Room room;
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
