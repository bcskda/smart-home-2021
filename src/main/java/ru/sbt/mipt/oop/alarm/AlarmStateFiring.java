package ru.sbt.mipt.oop.alarm;

public class AlarmStateFiring implements Alarm.AlarmState {
    Alarm alarm;

    public AlarmStateFiring(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public Alarm.AlarmState activate(String code) {
        throw new IllegalStateException("Cannot activate alarm in state: firing");
    }

    @Override
    public Alarm.AlarmState deactivate(String code) {
        throw new IllegalStateException("Cannot deactivate alarm in state: firing");
    }

    @Override
    public Alarm.AlarmState trigger() {
        throw new IllegalStateException("Cannot trigger alarm in state: firing");
    }
}
