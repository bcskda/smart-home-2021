package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.events.SensorEvent;

public interface SensorEventHandler {
    Action handleEvent(SensorEvent event);
}
