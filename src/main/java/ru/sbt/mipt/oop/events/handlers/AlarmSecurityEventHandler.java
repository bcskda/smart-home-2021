package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.events.Event;

public class AlarmSecurityEventHandler implements EventHandler {
    private final Alarm alarm;
    private final AlarmStateUpdateHandler stateUpdater;

    private EventHandler onAlarmArmed;
    private EventHandler onAlarmFiring;

    public AlarmSecurityEventHandler(Alarm alarm, AlarmStateUpdateHandler stateUpdater) {
        this.alarm = alarm;
        this.stateUpdater = stateUpdater;
    }

    public AlarmSecurityEventHandler setOnAlarmArmed(EventHandler onAlarmArmed) {
        this.onAlarmArmed = onAlarmArmed;
        return this;
    }

    public AlarmSecurityEventHandler setOnAlarmFiring(EventHandler onAlarmFiring) {
        this.onAlarmFiring = onAlarmFiring;
        return this;
    }

    @Override
    public Action handleEvent(Event event) {
        if (stateUpdater.handleEvent(event) != null) {
            throw new RuntimeException("Unexpected non-null action from state updater");
        }

        if (alarm.isArmed() && onAlarmArmed != null)
            return onAlarmArmed.handleEvent(event);
        else if (alarm.isFiring() && onAlarmFiring != null)
            return onAlarmFiring.handleEvent(event);
        else
            return null;
    }
}
