package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.events.Event;

public class UnconditionalHandler implements EventHandler {
    Action action;

    public UnconditionalHandler(Action action) {
        this.action = action;
    }

    @Override
    public Action handleEvent(Event event) {
        return action;
    }
}
