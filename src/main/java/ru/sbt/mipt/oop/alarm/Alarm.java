package ru.sbt.mipt.oop.alarm;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Alarm {
    AlarmState state;

    @Bean
    public Alarm alarm() {
        return this;
    }

    public Alarm() {
        this.state = new AlarmStateStale(this);
    }

    public boolean isStale() {
        return state instanceof AlarmStateStale;
    }

    public boolean isArmed() {
        return state instanceof AlarmStateArmed;
    }

    public boolean isFiring() {
        return state instanceof AlarmStateFiring;
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
