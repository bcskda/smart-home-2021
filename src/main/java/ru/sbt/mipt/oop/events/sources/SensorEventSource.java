package ru.sbt.mipt.oop.events.sources;

import ru.sbt.mipt.oop.events.SensorEvent;

public interface SensorEventSource {
    SensorEvent next();
}
