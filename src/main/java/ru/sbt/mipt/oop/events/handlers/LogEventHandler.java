package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.events.SensorEvent;

public class LogEventHandler implements SensorEventHandler {
    @Override
    public Action handleEvent(SensorEvent event) {
        System.err.println(event);
        return null;
    }
}
