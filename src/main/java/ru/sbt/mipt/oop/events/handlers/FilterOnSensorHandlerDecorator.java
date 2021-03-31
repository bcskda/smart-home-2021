package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;

public class FilterOnSensorHandlerDecorator implements EventHandler {
    EventHandler wrapped;

    public FilterOnSensorHandlerDecorator(EventHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public Action handleEvent(Event event) {
        if (event instanceof SensorEvent)
            return wrapped.handleEvent(event);
        else
            return null;
    }
}
