package ru.sbt.mipt.oop.events.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;

@Component
public class AlarmWhenArmedHandler implements EventHandler {
    @Autowired private AlarmWhenFiringHandler alarmWhenFiringHandler;

    @Bean
    public AlarmWhenArmedHandler alarmWhenArmedHandler() {
        return this;
    }

    @Override
    public Action handleEvent(Event event) {
        if (event instanceof SensorEvent)
            return alarmWhenFiringHandler.handleEvent(event);
        else
            return null;
    }
}
