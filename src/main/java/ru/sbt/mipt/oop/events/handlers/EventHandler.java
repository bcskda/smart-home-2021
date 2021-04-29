package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.events.Event;

public interface EventHandler {
    Action handleEvent(Event event);
}
