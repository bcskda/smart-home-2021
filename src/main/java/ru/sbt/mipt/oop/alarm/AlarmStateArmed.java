package ru.sbt.mipt.oop.alarm;

public class AlarmStateArmed implements Alarm.AlarmState {
    Alarm alarm;
    String code;

    public AlarmStateArmed(Alarm alarm, String code) {
        this.alarm = alarm;
        this.code = code;
    }

    @Override
    public Alarm.AlarmState activate(String code) {
        throw new IllegalStateException("Cannot activate alarm in state: armed");
    }

    @Override
    public Alarm.AlarmState deactivate(String code) {
        if (this.code.equals(code)) {
            System.out.println("Deactivating alarm from state: armed");
            return new AlarmStateStale(alarm);
        } else {
            return this.trigger();
        }
    }

    @Override
    public Alarm.AlarmState trigger() {
        System.out.println("Triggering alarm from state: armed");
        return new AlarmStateFiring(alarm);
    }
}
