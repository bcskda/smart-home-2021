package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.alarm.AlarmStateArmed;
import ru.sbt.mipt.oop.alarm.AlarmStateStale;
import ru.sbt.mipt.oop.alarm.AlarmStateFiring;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;

public class FilterByAlarmHandlerDecorator implements EventHandler {
    Alarm alarm;
    EventHandler wrapped;

    public FilterByAlarmHandlerDecorator(Alarm alarm, EventHandler wrapped) {
        this.alarm = alarm;
        this.wrapped = wrapped;
    }

    @Override
    public Action handleEvent(Event event) {
        if (! (event instanceof SensorEvent))
            return null;
        return onSensorEvent((SensorEvent) event);
    }

    private Action onSensorEvent(SensorEvent event) {
        switch (alarm.getState()) {
            case AlarmStateStale.class:
                return wrapped.handleEvent(event);
            case AlarmStateArmed.class:
            case AlarmStateFiring.class:
                return null;
        }
    }
}
