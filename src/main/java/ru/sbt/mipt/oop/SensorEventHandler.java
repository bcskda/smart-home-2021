package ru.sbt.mipt.oop;

public interface SensorEventHandler {
    /**
     * @throws IllegalArgumentException - if event type not expected by handler
     */
    void handleEvent(SensorEvent event) throws IllegalArgumentException;
}
