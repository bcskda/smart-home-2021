package ru.sbt.mipt.oop.alarm;

public class AlarmStateFiring implements AlarmState {
    Alarm alarm;

    public AlarmStateFiring(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public AlarmState activate(String code) {
        throw new IllegalStateException("Cannot activate alarm in state: firing");
    }

    @Override
    public AlarmState deactivate(String code) {
        throw new IllegalStateException("Cannot deactivate alarm in state: firing");
    }

    @Override
    public AlarmState trigger() {
        return this;
    }
}
