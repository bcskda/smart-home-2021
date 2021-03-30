package ru.sbt.mipt.oop.alarm;

public class AlarmStateStale implements Alarm.AlarmState {
    Alarm alarm;

    public AlarmStateStale(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public Alarm.AlarmState activate(String code) {
        System.out.println("Activating alarm from state: stale");
        return new AlarmStateArmed(alarm, code);
    }

    @Override
    public Alarm.AlarmState deactivate(String code) {
        throw new IllegalStateException("Cannot deactivate alarm in state: stale");
    }

    @Override
    public Alarm.AlarmState trigger() {
        System.out.println("Triggering alarm from state: stale");
        return new AlarmStateFiring(alarm);
    }
}
