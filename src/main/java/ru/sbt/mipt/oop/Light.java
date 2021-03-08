package ru.sbt.mipt.oop;

public class Light {
    private boolean isOn;
    private final String id;
     private final Room room;

    public Light(String id, Room room, boolean isOn) {
        this.id = id;
        this.room = room;
        this.isOn = isOn;
    }

    public boolean isOn() {
        return isOn;
    }

    public String getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
}
