package ru.sbt.mipt.oop.alarm;

import ru.sbt.mipt.oop.Action;

import java.util.function.Consumer;

public class Alarm {
    interface AlarmState {
        AlarmState activate(String code);

        AlarmState deactivate(String code);

        AlarmState trigger();
    }

    Consumer<Action> onActions;
    AlarmState state;

    public Alarm(Consumer<Action> onActions) {
        this.onActions = onActions;
        this.state = new AlarmStateStale(this);
    }

    public Class<? extends Alarm.AlarmState> getState() {
        return state.getClass();
    }

    public void activate(String code) {
        state = state.activate(code);
    }

    public void deactivate(String code) {
        state = state.deactivate(code);
    }

    public void trigger() {
        state = state.trigger();
    }
}
