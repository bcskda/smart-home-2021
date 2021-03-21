package ru.sbt.mipt.oop.alarm;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.handlers.SensorEventHandler;

import java.util.Collection;

public class AlarmStateFiring implements AlarmState {
    SmartHome smartHome;
    Collection<SensorEventHandler> eventHandlers;
    Alarm alarm;

    public AlarmStateFiring(SmartHome smartHome, Collection<SensorEventHandler> eventHandlers,
                            Alarm alarm) {
        this.smartHome = smartHome;
        this.eventHandlers = eventHandlers;
        this.alarm = alarm;
    }
}
