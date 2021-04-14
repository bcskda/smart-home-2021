package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.events.Event;

public class LogEventHandler implements EventHandler {
    @Override
    public Action handleEvent(Event event) {
        System.err.println(event);
        return null;
    }
}
