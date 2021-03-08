package ru.sbt.mipt.oop;

import com.google.gson.annotations.Expose;

public class Light {
    @Expose
    private boolean isOn;
    private final Room room;
    @Expose
    private final String id;

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
