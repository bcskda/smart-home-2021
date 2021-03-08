package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.events.SensorEvent;

public interface SensorEventHandler {
    void handleEvent(SensorEvent event) throws IllegalArgumentException;
}
