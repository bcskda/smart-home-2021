package ru.sbt.mipt.oop;

import com.google.gson.annotations.Expose;

import java.util.Collection;

public class Room {
    @Expose
    private Collection<Light> lights;
    @Expose
    private Collection<Door> doors;
    @Expose
    private String name;

    public Room(Collection<Light> lights, Collection<Door> doors, String name) {
        this.lights = lights;
        this.doors = doors;
        this.name = name;
    }

    public Collection<Light> getLights() {
        return lights;
    }

    public Collection<Door> getDoors() {
        return doors;
    }

    public String getName() {
        return name;
    }
}
