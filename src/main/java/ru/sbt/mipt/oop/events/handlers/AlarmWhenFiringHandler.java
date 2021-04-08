package ru.sbt.mipt.oop.events.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.NotificationSender;
import ru.sbt.mipt.oop.actions.ToggleLights;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.events.Event;

@Component
public class AlarmWhenFiringHandler implements EventHandler {
    @Autowired private Alarm alarm;
    @Autowired public NotificationSender notificationSender;

    @Override
    public Action handleEvent(Event event) {
        alarm.trigger();
        notificationSender.sendSms();
        return new ToggleLights();
    }
}
