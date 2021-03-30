package ru.sbt.mipt.oop.alarm;

import ru.sbt.mipt.oop.Action;

import java.util.function.Consumer;

public class Alarm {
    Consumer<Action> onActions;
    AlarmState state;

    public Alarm(Consumer<Action> onActions) {
        this.onActions = onActions;
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

    public void trigger() {
        state = state.trigger();
    }
}
