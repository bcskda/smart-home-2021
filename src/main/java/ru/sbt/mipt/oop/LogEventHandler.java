package ru.sbt.mipt.oop;

public class LogEventHandler implements SensorEventHandler {
    @Override
    public void handleEvent(SensorEvent event) {
        System.err.println(event);
    }
}
