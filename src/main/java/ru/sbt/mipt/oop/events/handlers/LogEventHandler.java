package ru.sbt.mipt.oop.events.handlers;

import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.events.Event;

@Component
public class LogEventHandler implements EventHandler {
    @Override
    public Action handleEvent(Event event) {
        System.err.println(event);
        return null;
    }
}
