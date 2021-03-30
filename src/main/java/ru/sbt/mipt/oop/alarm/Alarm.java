package ru.sbt.mipt.oop.alarm;

public class Alarm {
    AlarmState state;

    public Alarm() {
        this.state = new AlarmStateStale(this);
    }

    public Class<? extends AlarmState> getState() {
        return state.getClass();
    }

    public void activate(String code) {
        state = state.activate(code);
    }

    public void deactivate(String code) {
        state = state.deactivate(code);
    }
}
