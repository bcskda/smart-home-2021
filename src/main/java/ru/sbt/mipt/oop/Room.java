package ru.sbt.mipt.oop;

import java.util.Collection;

public class Room implements HomeComponent, Actionable {
    private final Collection<Light> lights;
    private final Collection<Door> doors;
    private final String name;

    public Room(Collection<Light> lights, Collection<Door> doors, String name) {
        this.lights = lights;
        this.doors = doors;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void forEachLight(Action action) {
        lights.forEach(light -> light.execute(action));
    }

    public void forEachDoor(Action action) {
        doors.forEach(door -> door.execute(action));
    }

    @Override
    public void execute(Action action) {
        action.execute(this);
        forEachLight(action);
        forEachDoor(action);
    }
}
