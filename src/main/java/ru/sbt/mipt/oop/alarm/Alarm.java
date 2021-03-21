package ru.sbt.mipt.oop.alarm;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.SensorEventHandler;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;

public class Alarm implements SensorEventHandler {
    SmartHome smartHome;
    Collection<SensorEventHandler> eventHandlers;
    AlarmState state;

    public Alarm(SmartHome smartHome, Collection<SensorEventHandler> eventHandlers) {
        this.smartHome = smartHome;
        this.eventHandlers = eventHandlers;
        this.state = new AlarmStateStale(this.smartHome, this.eventHandlers, this);
    }

    void Activate(String code) {
        state = state.Activate(code);
    }

    void Deactivate(String code) {
        state = state.Deactivate(code);
    }

    void Trigger() {
        state = state.Trigger();
    }

    @Override
    public Action handleEvent(SensorEvent event) {
        switch (event.getType()) {
            case ALARM_ACTIVATE:
            case ALARM_DEACTIVATE:
                throw new NotImplementedException();  // TODO update state
                // Then pass to other handlers:
                // return state.handleEvent(event);
            default:
                return state.handleEvent(event);
        }
    }
}
