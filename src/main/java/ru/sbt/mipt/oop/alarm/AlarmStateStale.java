package ru.sbt.mipt.oop.alarm;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.handlers.SensorEventHandler;

import java.util.Collection;

public class AlarmStateStale implements AlarmState {
    SmartHome smartHome;
    Collection<SensorEventHandler> eventHandlers;

    public AlarmStateStale(SmartHome smartHome, Collection<SensorEventHandler> eventHandlers) {
        this.smartHome = smartHome;
        this.eventHandlers = eventHandlers;
    }
}
