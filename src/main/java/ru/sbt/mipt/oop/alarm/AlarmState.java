package ru.sbt.mipt.oop.alarm;

public interface AlarmState {
    AlarmState activate(String code);

    AlarmState deactivate(String code);

    AlarmState trigger();
}
