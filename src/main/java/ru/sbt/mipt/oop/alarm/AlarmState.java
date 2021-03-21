package ru.sbt.mipt.oop.alarm;

import ru.sbt.mipt.oop.events.handlers.SensorEventHandler;

public interface AlarmState extends SensorEventHandler {
    AlarmState Activate(String code);

    AlarmState Deactivate(String code);

    AlarmState Trigger();
}
