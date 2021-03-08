package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.events.SensorEvent;

public class LogEventHandler implements SensorEventHandler {
    @Override
    public void handleEvent(SensorEvent event) {
        System.err.println(event);
    }
}
