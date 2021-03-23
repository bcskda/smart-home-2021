package ru.sbt.mipt.oop.alarm;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.AlarmSensorEvent;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.SensorEventHandler;

import java.util.Collection;

public class Alarm implements SensorEventHandler {
    interface AlarmState extends SensorEventHandler {
        AlarmState Activate(String code);

        AlarmState Deactivate(String code);

        AlarmState Trigger();
    }

    SmartHome smartHome;
    Collection<SensorEventHandler> eventHandlers;
    AlarmState state;

    public Alarm(SmartHome smartHome, Collection<SensorEventHandler> eventHandlers) {
        this.smartHome = smartHome;
        this.eventHandlers = eventHandlers;
        this.state = new AlarmStateStale(this.smartHome, this.eventHandlers, this);
    }

    protected void Trigger() {
        state = state.Trigger();
    }

    @Override
    public Action handleEvent(SensorEvent event) {
        switch (event.getType()) {
            case ALARM_ACTIVATE:
                state = state.Activate(((AlarmSensorEvent) event).getCode());
                return null;
            case ALARM_DEACTIVATE:
                state = state.Deactivate(((AlarmSensorEvent) event).getCode());
                return null;
            default:
                return state.handleEvent(event);
        }
    }
}
