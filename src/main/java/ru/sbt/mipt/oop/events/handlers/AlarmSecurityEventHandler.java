package ru.sbt.mipt.oop.events.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.events.Event;

@Component
public class AlarmSecurityEventHandler implements EventHandler {
    @Autowired private Alarm alarm;
    @Autowired private AlarmStateUpdateHandler stateUpdater;
    @Autowired private AlarmWhenArmedHandler onAlarmArmed;
    @Autowired private AlarmWhenFiringHandler onAlarmFiring;

    @Bean
    public AlarmSecurityEventHandler alarmSecurityEventHandler() {
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
