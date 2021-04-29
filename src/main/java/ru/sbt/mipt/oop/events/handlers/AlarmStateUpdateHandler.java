package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.events.AlarmEvent;
import ru.sbt.mipt.oop.events.Event;

public class AlarmStateUpdateHandler implements EventHandler {
    private final Alarm alarm;

    public AlarmStateUpdateHandler(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public Action handleEvent(Event event) {
        if (event instanceof AlarmEvent)
            updateAlarmState((AlarmEvent) event);
        return null;
    }

    void updateAlarmState(AlarmEvent event) {
        switch (event.getType()) {
            case ALARM_ACTIVATE:
                alarm.activate(event.getCode());
                break;
            case ALARM_DEACTIVATE:
                alarm.deactivate(event.getCode());
                break;
            default:
                break;
        }
    }
}
