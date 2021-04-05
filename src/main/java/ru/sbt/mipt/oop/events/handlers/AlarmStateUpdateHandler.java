package ru.sbt.mipt.oop.events.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.events.AlarmEvent;
import ru.sbt.mipt.oop.events.Event;

@Component
@Import(Alarm.class)
public class AlarmStateUpdateHandler implements EventHandler {
    @Autowired private Alarm alarm;

    @Bean
    public AlarmStateUpdateHandler alarmStateUpdateHandler() {
        return this;
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
