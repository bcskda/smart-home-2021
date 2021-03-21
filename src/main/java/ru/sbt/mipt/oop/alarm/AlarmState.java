package ru.sbt.mipt.oop.alarm;

public interface AlarmState {
    AlarmState Activate(String code);

    AlarmState Deactivate(String code);

    AlarmState Trigger();
}
